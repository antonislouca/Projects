#include "threadpool.h"

// typedef struct thread_pool {
//     Queue tasks_queue;
//     int num_free_threads;
//     pthread_mutex_t mutex;
//     pthread_cond_t wait_request;
// 	   pthread_t *threads;
// } threadpool;

// FIXME might need a 2nd mutex, not sure
// 1st : Adding a task in the queue
// 2nd : Removing a task from queue

void manager(int socket)
{
	server *s;

	mailserverinit(&s, socket);

	int phase = 0; //0:Authenticate username,1:Authenticate password,2:Transaction,3:Update,4:terminate

	if (sendResponse(s->new_socket, "+OK POP3 server ready\r\n") == EXIT_FAILURE)
	{
		close(s->new_socket);
		mailserverDestroy(&s);
		return; // TODO (1) must close connection and return from function
	}

	while (TRUE)
	{

		char received_message[MAXLENGTH] = " ";
		char *token[10];
		char cmd[BUFSIZ] = {'\0'};
		char username[MAXLENGTH];
		fd_set rfds;			  //file descriptor
		struct timeval timevalue; //this will be set to the time value to wait
		int retval;				  //return value of select

		/* Watch stdin (fd 0) to see when it has input. */
		FD_ZERO(&rfds);
		FD_SET(s->new_socket, &rfds);
		/* Wait up to five seconds. */
		timevalue.tv_sec = 60 * 10;
		timevalue.tv_usec = 0;
		retval = select(s->new_socket + 1, &rfds, NULL, NULL, &timevalue);
		/* Don’t rely on the value of tv now! */
		//printf("select unblock??\n");
		if (retval == -1)
		{
			perror("select()");
			return;
		}
		else if (retval)
		{
			printf("waiting to read\n");
			if (read(s->new_socket, received_message, sizeof received_message) < 0)
			{
				perror("read");
				break; //TODO (1)
			}

			int lastindex = tokenize(received_message, token, " \r\n");
			//	printf("tokens: %s\n", token[0]);

			toUpper(cmd, token[0]);
			printf("Phase: %d, Command %s\n", phase, cmd);

			if ((strcmp(cmd, "USER") == 0) && (phase == 0))
			{
				if (authenticateusername(s, token[1]) == EXIT_SUCCESS)
				{
					phase = 1;
					strcpy(username, token[1]);
				}
			}
			else if ((strcmp(cmd, "PASS") == 0) && (phase == 1))
			{
				if (authenticatepassword(s, username, token[1]) == EXIT_SUCCESS)
				{
					phase = 2;
				}
			}
			else if ((strcmp(cmd, "STAT") == 0) && (phase == 2))
			{
				statistics(s);
			}
			else if ((strcmp(cmd, "LIST") == 0) && (phase == 2))
			{
				list(s, token[1]);
			}
			else if ((strcmp(cmd, "RETR") == 0) && (phase == 2))
			{
				retrive(s, token[1]);
			}
			else if ((strcmp(cmd, "UIDL") == 0) && (phase == 2))
			{
				uidl(s, token[1]);
			}
			else if ((strcmp(cmd, "DELE") == 0) && (phase == 2))
			{
				delete (s, atoi(token[1]));
			}
			else if (strcmp(cmd, "QUIT") == 0)
			{
				char buf[MAXLENGTH] = {'\0'};
				int msg = update(s);
				if (msg != 0)
				{
					sprintf(buf, "-ERR some deleted messages not removed\r\n");
				}
				else if (s->messages == 0)
				{
					sprintf(buf, "+OK %s POP3 server signing off (mailbox empty)\r\n", s->username);
				}
				else
				{
					sprintf(buf, "+OK %s POP3 server signing off (%ld messages left)\r\n", s->username, s->messages);
				}
				if (sendResponse(s->new_socket, buf) == EXIT_FAILURE)
					break;
				close(s->new_socket);
				break;
			}
			else if (strcmp(cmd, "CAPA") == 0)
			{
				if (sendResponse(s->new_socket, "+OK Capability list follows\r\n") == EXIT_FAILURE)
					break;
				if (sendResponse(s->new_socket, "USER\r\nDELE\r\nSTAT\r\nLIST\r\nPASS\r\nUIDL\r\nRETR\r\nCAPA\r\nQUIT\r\nEXPIRE 600\r\n") == EXIT_FAILURE)
					break;
				if (sendResponse(s->new_socket, ".\r\n") == EXIT_FAILURE)
					break;
				if (lastindex > 0 && strcmp(token[1], "QUIT") == 0)
				{
					break;
				}
			}
			else
			{
				if (sendResponse(s->new_socket, "-ERR unkown command\r\n") == EXIT_FAILURE)
					break;
			}
		}
		else
		{
			printf("TIMEOUT\n");
			close(s->new_socket);
			mailserverDestroy(&s);
			return;
		}
	}
	mailserverDestroy(&s);
}
static int threadpool_get_task(threadpool *pool)
{
	// tasks = new_socket numbers => a client
	int task;

	// Obtain a task
	if (pthread_mutex_lock(pool->mutex_queue))
	{
		perror("pthread_mutex_lock: ");
		return -1;
	}

	while (qEmpty(pool->tasks_queue) == TRUE)
	{
		// Wait until a new task arrives.
		if (pthread_cond_wait(pool->wait_request, pool->mutex_queue))
		{
			perror("pthread_cond_wait: ");
			if (pthread_mutex_unlock(pool->mutex_queue))
			{
				perror("pthread_mutex_unlock: ");
			}

			return -1;
		}
	}

	if ((task = qDequeue(pool->tasks_queue)) < 0)
	{
		printf("Failed to obtain a task from the jobs queue.\n");
		return -1;
	}

	if (pthread_mutex_unlock(pool->mutex_queue))
	{
		perror("pthread_mutex_unlock: ");
		return -1;
	}

	return task;
}

static void *worker_routine(void *data)
{
	threadpool *pool = (threadpool *)data;

	while (TRUE)
	{
		int new_socket = threadpool_get_task(pool);
		if (new_socket == -1)
		{
			printf("Error while getting a task. Thread is exiting\n");
			break;
		}

		////////////////////////////////////////////////////////////////////////
		// Reduce the number of free threads by one
		////////////////////////////////////////////////////////////////////////
		if (pthread_mutex_lock(pool->mutex_threads))
		{
			perror("pthread_mutex_lock: ");
			break;
		}
		pool->num_free_threads--;

		if (pthread_mutex_unlock(pool->mutex_threads))
		{
			perror("pthread_mutex_unlock: ");
			break;
		}
		////////////////////////////////////////////////////////////////////////
		// Service client
		////////////////////////////////////////////////////////////////////////

		manager(new_socket);

		////////////////////////////////////////////////////////////////////////
		// Increase the number of free threads by one
		////////////////////////////////////////////////////////////////////////
		if (pthread_mutex_lock(pool->mutex_threads))
		{
			perror("pthread_mutex_lock: ");
			break;
		}
		pool->num_free_threads++;

		if (pthread_mutex_unlock(pool->mutex_threads))
		{
			perror("pthread_mutex_unlock: ");
			break;
		}
	}

	return NULL;
}

threadpool *threadpool_init(int num_threads)
{
	threadpool *pool;

	// Create threadpool struct
	if ((pool = malloc(sizeof(threadpool))) == NULL)
	{
		perror("malloc: ");
		return NULL;
	}

	// Initialize queue mutex
	pool->mutex_queue = malloc(sizeof(*(pool->mutex_queue)));
	if (pool->mutex_queue == NULL)
	{
		free(pool);
		return NULL;
	}
	if (pthread_mutex_init(pool->mutex_queue, NULL))
	{
		perror("pthread_mutex_init: ");
		free(pool);
		return NULL;
	}

	// Initialize free threads mutex
	pool->mutex_threads = malloc(sizeof(*(pool->mutex_threads)));
	if (pool->mutex_threads == NULL)
	{
		free(pool);
		return NULL;
	}
	if (pthread_mutex_init(pool->mutex_threads, NULL))
	{
		perror("pthread_mutex_init: ");
		free(pool);
		return NULL;
	}

	// Initialize cond var
	pool->wait_request = malloc(sizeof(*(pool->wait_request)));
	if (pool->wait_request == NULL)
	{
		free(pool);
		return NULL;
	}
	if (pthread_cond_init(pool->wait_request, NULL))
	{
		perror("pthread_mutex_init: ");
		free(pool);
		return NULL;
	}

	// Initialize the tasks queue
	initQueue(&(pool->tasks_queue));

	// Create threads
	if ((pool->threads = malloc(sizeof(pthread_t) * num_threads)) == NULL)
	{
		perror("malloc: ");
		free(pool);
		return NULL;
	}

	// Start worker threads
	pool->num_free_threads = num_threads;
	for (int i = 0; i < num_threads; i++)
	{
		// pool->num_threads = i; // num_thread will be the thread_id
		if (pthread_create(&(pool->threads[i]), NULL, worker_routine, pool))
		{
			perror("pthread_create:");
			return NULL;
		}
	}

	return pool;
}

int threadpool_add_task(threadpool *pool, int new_socket)
{

	// Check if there are any free workers
	if (pool->num_free_threads == 0)
		return -2;

	if (pthread_mutex_lock(pool->mutex_queue))
	{
		perror("pthread_mutex_lock: ");
		return -1;
	}

	// Add task to the queue
	if (qEnqueue(pool->tasks_queue, new_socket) == FALSE)
	{
		printf("Failed to add a new task to the tasks queue.\n");
		if (pthread_mutex_unlock(pool->mutex_queue))
			perror("pthread_mutex_unlock: ");

		return -1;
	}

	if (qEmpty(pool->tasks_queue) == FALSE)
	{
		// Notify all worker threads that there are new jobs
		if (pthread_cond_broadcast(pool->wait_request))
		{
			perror("pthread_cond_broadcase: ");
			if (pthread_mutex_unlock(pool->mutex_queue))
				perror("pthread_mutex_unlock: ");

			return -1;
		}
	}

	if (pthread_mutex_unlock(pool->mutex_queue))
	{
		perror("pthread_mutex_lock: ");
		return -1;
	}

	return 0;
}

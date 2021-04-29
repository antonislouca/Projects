#include "mailserver.h"
#include "threadpool.h"

// void *manager(void *args) {
//     setting *set = (setting *)args;
//     server *s;
//     int socket = -1;
//     // pthread_barrier_wait(&set->barrier);

//     while (1) {
//         pthread_mutex_lock(&set->mutex);  //cs enter

//         while (qEmpty(set->connection) == TRUE)
//             pthread_cond_wait(&set->wait_request, &set->mutex);  //waits on condition

//         socket = qDequeue(set->connection);  //cs exit

//         pthread_mutex_unlock(&set->mutex);

//         mailserverinit(&s, socket);

//         int phase = -1;  //0:initiate,1:Authenticate,2:Transaction,3:Update,4:terminate
//         phase = 1;
//         if (write(s->new_socket, "+OK POP3 server read\r\n", sizeof "+OK POP3 server read\r\n") < 0) {
//             perror("write");
//             continue;  // TODO check
//         }

//         while (1) {
//             char received_message[MAXLENGTH] = " ";
//             char *token[10];
//             char cmd[512] = {'\0'};
//             if (read(s->new_socket, received_message, sizeof received_message) < 0) {
//                 perror("read");
//                 exit(1);
//             }
//             int lastindex = tokenize(received_message, token, " ");
//             printf("tokens: %s\n", token[0]);

//             toUpper(cmd, token[0]);
//             printf("Phase: %d, Command %s\n", phase, cmd);

//             if ((strcmp(cmd, "USER") == 0) && (phase == 1)) {
//                 if (authenticate(s, token[1]) == EXIT_SUCCESS)
//                     phase = 2;
//             } else if ((strcmp(cmd, "STAT") == 0) && (phase == 2)) {
//                 statistics(s);
//             } else if ((strcmp(cmd, "LIST") == 0) && (phase == 2)) {
//                 list(s, token[1]);
//             } else if ((strcmp(cmd, "RETR") == 0) && (phase == 2)) {
//                 retrive(s, token[1]);
//             } else if ((strcmp(cmd, "DELE") == 0) && (phase == 2)) {
//                 delete (s, atoi(token[1]));
//             } else if (strcmp(cmd, "QUIT") == 0) {
//                 char buf[MAXLENGTH] = {'\0'};
//                 int msg = update(s);
//                 if (msg != 0) {
//                     sprintf(buf, "-ERR some deleted messages not removed\r\n");
//                 } else if (s->messages == 0) {
//                     sprintf(buf, "+OK %s POP3 server signing off (mailbox empty)\r\n", s->username);
//                 } else {
//                     sprintf(buf, "+OK %s POP3 server signing off (%ld messages left)\r\n", s->username, s->messages);
//                 }
//                 sendResponse(s->new_socket, buf);
//                 close(s->new_socket);
//                 break;  //  break;
//                 //implement quit???
//                 //UPDATE state
//             }
//         }
//         mailserverDestroy(&s);
//     }
// }

int main()
{
    int sock, newsock, serverlen;
    int num_threads = 2;
    int port = 4000;
    setting *set;
    settingsinit(&set, num_threads);
    configure_server(&num_threads, &port);

      printf("threads: %d, port: %d\n", num_threads, port);
    struct sockaddr_in server, client;
    struct sockaddr *serverptr;
    struct sockaddr *clientptr;
    socklen_t clientlen;

    // Create the threadpool of num_threads workers
    threadpool *pool;
    if ((pool = threadpool_init(num_threads)) == NULL)
    {
        printf("Error! Failed to create a thread pool struct.\n");
        exit(EXIT_FAILURE);
    }

    if ((sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
    {
        perror("socket");
        exit(EXIT_FAILURE);
    }
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = htonl(INADDR_ANY);
    server.sin_port = htons(port);
    serverptr = (struct sockaddr *)&server;
    serverlen = sizeof server;

    if (bind(sock, serverptr, serverlen) < 0)
    {
        perror("bind");
        exit(EXIT_FAILURE);
    }
    if (listen(sock, 5) < 0)
    {
        perror("listen");
        exit(EXIT_FAILURE);
    }
    while (TRUE)
    {
        clientptr = (struct sockaddr *)&client;
        clientlen = sizeof client;
        if ((newsock = accept(sock, clientptr, &clientlen)) < 0)
        {
            perror("accept");
            exit(EXIT_FAILURE);
        }
        printf("Connection accepted\n");
        int ret = threadpool_add_task(pool, newsock);
        // Error checking
        if (ret == -1)
        {
            printf("Error while adding task");
            exit(EXIT_FAILURE);
        }
        // Statistics collection
        if (ret == -2)
        {
            printf("Ignored accepted connection, since free workers == 0\n");
            close(newsock);
        }
    }

    return EXIT_SUCCESS;
}

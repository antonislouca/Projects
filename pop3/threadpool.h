#include "mailserver.h"

typedef struct thread_pool
{
    Queue *tasks_queue;
    int num_free_threads;
    pthread_mutex_t *mutex_queue;
    pthread_mutex_t *mutex_threads;
    pthread_cond_t *wait_request;
    pthread_t *threads;
} threadpool;

threadpool *threadpool_init(int num_threads);

int threadpool_add_task(threadpool *thread, int new_socket);
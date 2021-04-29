#include "mailserver.h"
void mailserverinit(server **s, int socket)
{
    *s = (server *)malloc(1 * sizeof(server));
    (*s)->deleted = (int *)calloc(ADDSZ, sizeof(int));
    (*s)->INBOX = (char *)calloc(MAXLENGTH, sizeof(char));
    strcpy((*s)->INBOX, "./DEMO/");
    //  printf("inbox %s \n", (*s)->INBOX);
    (*s)->num_deleted = 0;
    (*s)->deletedArrayMax = ADDSZ;
    (*s)->new_socket = socket;
    //socket init happens where?

    (*s)->username = (char *)calloc(MAXLENGTH, sizeof(char));
    (*s)->messages = 0;
}

void mailserverDestroy(server **s)
{
    free((*s)->username);
    free((*s)->deleted);
    free((*s)->INBOX);
    free(*s);
}

void settingsinit(setting **set, int threads)
{
    *set = (setting *)malloc(1 * sizeof(setting));
    initQueue(&(*set)->connection);
    pthread_cond_init(&(*set)->wait_request, NULL);
    pthread_mutex_init(&(*set)->mutex, NULL);
    pthread_barrier_init(&(*set)->barrier, NULL, threads+1);
}
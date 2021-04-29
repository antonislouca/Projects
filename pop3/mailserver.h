#ifndef MAILSERVERH
#define MAILSERVERH

#define _GNU_SOURCE

#include <sys/types.h>  // For sockets
#include <sys/socket.h> // For sockets
#include <netinet/in.h> // For Internet sockets
#include <netdb.h>      // For gethostbyaddr
#include <stdio.h>      // For I/O
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <errno.h>
#include <dirent.h>
#include <ctype.h>
#include <time.h>
#include <pthread.h>
// #include <openssl/evp.h>
// #include <openssl/rsa.h>
// #include <openssl/err.h>
// #include <openssl/pem.h>
// #include <openssl/ssl.h>
// #include <openssl/sha.h>

//definitions
#define TRUE 1
#define FALSE 0
#define ADDSZ 512
#define MAXLENGTH 512
#define EMPTY -1
#define FAIL -1

#define perror2(s, e) fprintf(stderr, "%s: %s\n", s, strerror(e))

//Structs
typedef struct mailserver
{
    char *INBOX;
    int *deleted;
    int num_deleted;
    int new_socket; //will this be a pointer or not???
    int deletedArrayMax;
    char *username;
    size_t messages;
} server;

typedef struct node
{
    int data;
    struct node *next;
} Node;

typedef struct queue
{
    Node *head;
    Node *tail;
} Queue;
typedef struct settings
{
    Queue *connection;
    pthread_cond_t wait_request;
    pthread_mutex_t mutex;
    pthread_barrier_t barrier;
} setting;
//function declaration section
int list(server *s, char *args);
int uidl(server *s, char *args);
int delete (server *s, int toBedeleted);
int retrive(server *s, char *number);
int statistics(server *s);
int authenticateusername(server *s, char *username);
int authenticatepassword(server *s, char *username, char *pass);
int dsize(server *s, size_t *num_messages, size_t *total_octets, int calcTotal);
size_t fsize(char *filename);
int tokenize(char *command, char **tokenised, const char *delim);
int isDeleted(server *s, char *file_name);
void trimTrailing(char *str);
void mailserverinit(server **s, int socket);
void mailserverDestroy(server **s);
int configure_server(int *threads, int *port);
int sendResponse(int socket, char *msg);
void toUpper(char *upper, char *string);
int update(server *s);
int threadPoolcreate(int numberofthreads, pthread_t **threadPool, setting *set);
// void *manager();
void settingsinit(setting **set, int threads);
/** @brief Initializes a queue by properly setting members head & tail.
 *
 * @param queue valid struct queue
 * @post the struct queue points to, will be set up to represent an empty queue
 * @return void
*/
int initQueue(Queue **queue);

/** 
 * @brief Adds val to the tail of the queue.
 * @param qPtr points to a valid struct queue
 * @param val is the value to enqueue into the queue pointed to by qPtr
 * @return 1 If the operation is successful
 *         0 If not successfull, & no change will be made to the queue
 */
int qEnqueue(Queue *qPtr, int val);

/** @brief Dequeues one element from the head of the queue.
 *
 * @param qPtr points to a valid Queue.
 * @post  If the queue pointed to by qPtr is non-empty, then the value at the
 *            head of the queue is deleted from the queue and returned.
 *         Otherwise, -1 is returned to signify that the queue was already empty
 *            when the dequeue was attempted.
 *  @return value at the head of the queue If the queue is non-empty
 *          -1 if the queue was already empty
 */
int qDequeue(Queue *qPtr);

/** @brief Checks to see if the queue is empty.
 *
 * @param qPtr points to a valid Queue.
 * @return true if the queue pointed by qPtr is empty
 */
int qEmpty(Queue *qPtr);

/** @brief Returns the data of qFront from a struct qPtr.
 *
 * @param qPtr points to a valid Queue.
 * @return value at head  if the queue pointed by qPtr is non-empty
 *         -1              if the queue is empty
 */
int qHead(Queue *qPtr);

#endif
#include "mailserver.h"

#include <unistd.h>
#include <time.h>
#include <errno.h>
#include <sys/signal.h>

ssize_t safe_write(int fd, const void* buf, size_t bufsz)
{
    sigset_t sig_block, sig_restore, sig_pending;

    sigemptyset(&sig_block);
    sigaddset(&sig_block, SIGPIPE);

    /* Block SIGPIPE for this thread.
     *
     * This works since kernel sends SIGPIPE to the thread that called write(),
     * not to the whole process.
     */
    if (pthread_sigmask(SIG_BLOCK, &sig_block, &sig_restore) != 0) {
        return -1;
    }

    /* Check if SIGPIPE is already pending.
     */
    int sigpipe_pending = -1;
    if (sigpending(&sig_pending) != -1) {
        sigpipe_pending = sigismember(&sig_pending, SIGPIPE);
    }

    if (sigpipe_pending == -1) {
        pthread_sigmask(SIG_SETMASK, &sig_restore, NULL);
        return -1;
    }

    ssize_t ret;
    while ((ret = write(fd, buf, bufsz)) == -1) {
        if (errno != EINTR)
            break;
    }

    /* Fetch generated SIGPIPE if write() failed with EPIPE.
     *
     * However, if SIGPIPE was already pending before calling write(), it was
     * also generated and blocked by caller, and caller may expect that it can
     * fetch it later. Since signals are not queued, we don't fetch it in this
     * case.
     */
    if (ret == -1 && errno == EPIPE && sigpipe_pending == 0) {
        struct timespec ts;
        ts.tv_sec = 0;
        ts.tv_nsec = 0;

        int sig;
        while ((sig = sigtimedwait(&sig_block, 0, &ts)) == -1) {
            if (errno != EINTR)
                break;
        }
    }

    pthread_sigmask(SIG_SETMASK, &sig_restore, NULL);
    return ret;
}

int configure_server(int *threads, int *port)
{
    // Read variables in the config.txt file and store the values
    // in the right variables of the program.

    FILE *fp;
    char *line = NULL;
    size_t len = 0;
    size_t read;

    fp = fopen("config.txt", "r");
    if (fp == NULL)
    {
        perror("config.txt");
        // TODO do some proper error handling for config.txt
        // exit(EXIT_FAILURE);
        *threads = 4;
        *port = 2000;
        return EXIT_FAILURE;
    }

    // TODO test the edge cases when reading config.txt
    while ((read = getline(&line, &len, fp)) != -1)
    {
        if (line[0] == '#' || line[0] == '\n' || line[0] == '\r')
            continue;
        char *varname = strtok(line, " =\r\n");
        char *varvalue = strtok(NULL, " =\r\n");
        if (!strcmp(varname, "THREADS"))
            *threads = atoi(varvalue);
        if (!strcmp(varname, "PORT"))
            *port = atoi(varvalue);
    }

    fclose(fp);

    return EXIT_SUCCESS;
}
int tokenize(char *command, char **tokenised, const char *delim)
{
    int i = 0;
    char *token;
    token = strtok(command, delim);
    while (token != NULL)
    {
        tokenised[i] = token;
        token = strtok(NULL, delim);
        i++;
    }
    tokenised[i] = NULL;
    return i - 1;
}
//int tokenize(char *command, char **tokenised, const char *delim)
//{
//    int i = 0;
//    char *token;
//    token = strtok(command, delim);
//    while (token != NULL)
//    {
//        if (token == NULL)
//            continue;
//        int length = strlen(token);
//        if (token[length - 2] == '\r')
//        {
//            token[length - 2] = '\0';
//        }
//        else if (token[length - 1] == '\n')
//            token[length - 1] = '\0';
//        tokenised[i] = token;
//        token = strtok(NULL, delim);
//        i++;
//    }
//    tokenised[i] = NULL;
//    return i - 1;
//}

size_t fsize(char *filename)
{
    struct stat st;

    if (stat(filename, &st) == 0)
        return st.st_size;

    // TODO do not send that to the Client!!!
    // fprintf(stderr, "Connot determine size of %s: %s\n",
    //         filename, strerror(errno));

    return -1;
}

// TODO if its only purpose is to check if a file is deleted,
// then rename "contains" to "isDeleted"
int isDeleted(server *s, char *file_name)
{ //FIXME

    // Assuming that the first 0 indicates the end:

    // int i = 0;
    // int current_deleted = deleted[i];
    // int file_name_int = atoi(file_name);

    // while (deleted[i] != 0)
    //     if (current_deleted == file_name_int)
    //         return TRUE;
    //     else
    //         current_deleted = deleted[++i];

    // Assuming that the end is the array size:

    // int deletedSize = BUFSIZ; // TODO determine size of deleted array
    // int current_deleted;
    // int file_name_int = atoi(file_name);

    // for (int i = 0; i < deletedSize; i++)
    //     if (deleted[i] == file_name_int)
    //         return TRUE;

    // Assuming that the index of the file_name determines its deletion

    // int file_name_int = atoi(file_name);
    // return deleted[file_name_int - 1];

    int file_name_int = atoi(file_name);
    for (int i = 0; i < s->num_deleted; i++)
        if ((s->deleted)[i] == file_name_int)
            return TRUE;

    return FALSE;
}

int sendResponse(int socket, char *msg)
{
    if (safe_write(socket, msg, strlen(msg)) < 0)
    {
        perror("write");
        return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
}

void toUpper(char *upper, char *string)
{
    if (!string) return;
    int n = strlen(string);
    for (int i = 0; i < n; i++)
        upper[i] = toupper(string[i]);
}


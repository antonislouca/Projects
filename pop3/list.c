#include "mailserver.h"

int dsize(server *s, size_t *num_messages, size_t *total_octets, int calcTotal)
{
    DIR *dir;
    struct dirent *entry;
    char file_path[BUFSIZ];
    char msg[BUFSIZ];
    int socket = s->new_socket;

    if (calcTotal)
    {
        // Current mode: Calculate num_messages and total octets
        *num_messages = 0;
        *total_octets = 0;
    }

    strcpy(file_path, s->INBOX);

    if ((dir = opendir(s->INBOX)) == NULL)
    {
        perror(s->INBOX);
        return EXIT_FAILURE;
    }

    while ((entry = readdir(dir)) != NULL)
    {
        if (entry->d_type != DT_REG)
            continue; // not a regular file
        if (isDeleted(s, entry->d_name))
            continue; // in deleted

        bzero(file_path + strlen(s->INBOX), BUFSIZ - strlen(s->INBOX) - 1);
        strcat(file_path, entry->d_name);

        if (calcTotal)
        {
            (*total_octets) += fsize(file_path);
            (*num_messages)++;
        }
        else
        {
            sprintf(msg, "%s %ld\r\n", entry->d_name, fsize(file_path));
            if (sendResponse(socket, msg) != EXIT_SUCCESS)
                return EXIT_FAILURE;
        }

        // printf("file_path: %s\n", file_path);
        // printf("size: %ld\n\n", fsize(file_path));
    }
    if (!calcTotal)
        if (sendResponse(socket, ".\r\n") != EXIT_SUCCESS)
            return EXIT_FAILURE;

    closedir(dir);

    return EXIT_SUCCESS;
}

int list(server *s, char *args)
{
    char buffer[BUFSIZ];
    long num_messages = -1;
    long total_octets = -1;
    int socket = s->new_socket;
    char msg[BUFSIZ];

    strcpy(buffer, s->INBOX);

    // printf("Dir: |%s|\n", buffer);

    if (args && args[0])
    { // include the argument only if it exists
        strcat(buffer, args);

        // printf("Size: %ld\n", fsize(buffer));
        long file_size = (isDeleted(s, args))? -1 : fsize(buffer);
        if (file_size >= 0)
        {
            sprintf(msg, "+OK %s %ld\r\n", args, file_size);
            if (sendResponse(socket, msg) != EXIT_SUCCESS)
                return EXIT_FAILURE;
        }
        else
        {
            dsize(s, &num_messages, &total_octets, TRUE);
            sprintf(msg, "-ERR no such message, "
                         "only %ld messages in mailbox\r\n",
                    num_messages);
            if (sendResponse(socket, msg) != EXIT_SUCCESS)
                return EXIT_FAILURE;
        }
    }
    else
    {
        // printf("%d messages\n", dsize(INBOX));
        dsize(s, &num_messages, &total_octets, TRUE);
        sprintf(msg, "+OK %ld messages (%ld octets)\r\n",
                num_messages, total_octets);
        if (sendResponse(socket, msg) != EXIT_SUCCESS)
            return EXIT_FAILURE;
        dsize(s, &num_messages, &total_octets, FALSE);
    }
    return EXIT_SUCCESS;
}

#ifdef DEBUGLIST
int main(int argc, char *argv[])
{
    char *token[10];
    char cmd[100] = "LIST ";
    tokenize(cmd, token, " ");
    printf("token[0]:|%s|\n", token[0]);
    printf("token[1]:|%s|\n", token[1]);
    // printf("token[1]:|%s|\n", *(token + 1));
    // list(NULL, *(token + 1));
    server s;
    s.num_deleted = 0;
    s.INBOX = "./DEMO/konstantinos/";
    list(&s, token[1]);
    return EXIT_SUCCESS;
}
#endif
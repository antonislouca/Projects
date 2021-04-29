#include "mailserver.h"

/*int tokenize(char *command, char **tokenised, const char *delim)
{
    int i = 0;
    char *token;
    token = strtok(command, delim);
    while (token != NULL)
    {
        if (token == NULL)
            continue;
        int length = strlen(token);
        if (token[length - 2] == '\r')
        {
            token[length - 2] = '\0';
        }
        else if (token[length - 1] == '\n')
            token[length - 1] = '\0';
        tokenised[i] = token;
        token = strtok(NULL, delim);
        i++;
    }
    tokenised[i] = NULL;
    return i - 1;
}*/
void trimTrailing(char *str)
{
    int index, i;

    /* Set default index */
    index = -1;

    /* Find last index of non-white space character */
    i = 0;
    while (str[i] != '\0')
    {
        if (str[i] != ' ' && str[i] != '\t' && str[i] != '\n')
        {
            index = i;
        }

        i++;
    }

    /* Mark next character to last non-white space character as NULL */
    str[index + 1] = '\0';
}

int retrive(server *s, char *number)
{
    int socket = s->new_socket;
    if (isDeleted(s, number) == TRUE)
    {
        if (sendResponse(socket, "-ERR no such message\r\n") != EXIT_SUCCESS)
            return EXIT_FAILURE;
        return EXIT_SUCCESS;
    }
    char str[512];
    //char *token[10];
    char recived_message[MAXLENGTH] = " ";
    char path[MAXLENGTH] = "";
    /*bzero(recived_message, sizeof recived_message);
    if (read(socket, recived_message, sizeof recived_message) < 0)
    {
        perror("read");
        exit(1);
    }
    int lastindex = tokenize(recived_message, token, " ");*/
    //check if needed for number of tokens
    //check for command if needed
    bzero(path, sizeof path);
    sprintf(path, "%s%s", s->INBOX, number);
    FILE *fd;
    if (!(fd = fopen(path, "r")))
    {
        bzero(str, sizeof str);
        sprintf(str, "-ERR no such message\r\n");
        if (sendResponse(socket, str) != EXIT_SUCCESS)
            return EXIT_FAILURE;
        return EXIT_FAILURE;
    }

    char line[500];
    char login_success = 0;
    char response[2 * MAXLENGTH];
    long int octs = -1;
    if ((octs = fsize(path)) <= 0)
        return EXIT_FAILURE;
    bzero(response, sizeof response);
    sprintf(response, "+OK %ld octets\r\n", octs);
    if (sendResponse(socket, response) != EXIT_SUCCESS)
        return EXIT_FAILURE;

    while (fgets(line, sizeof(line), fd))
    {
        trimTrailing(line);
        bzero(response, sizeof response);
        if (line[0] == '.')
        {
            sprintf(response, ".%s\r\n", line);
        }
        else
        {
            sprintf(response, "%s\r\n", line);
        }
        if (sendResponse(socket, response) != EXIT_SUCCESS)
        {
            return EXIT_FAILURE;
        }
    }
    fclose(fd);
    bzero(response, sizeof response);
    sprintf(response, ".\r\n");
    if (sendResponse(socket, response) != EXIT_SUCCESS)
    {
        return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
}
#ifdef DEBUGRETREVE
int main()
{
    int sock, newsock, serverlen;
    int port = 2000;
    struct sockaddr_in server, client;
    struct sockaddr *serverptr;
    struct sockaddr *clientptr;
    socklen_t clientlen;
    if ((sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
    { /* Create socket */
        perror("socket");
        exit(1);
    }
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = htonl(INADDR_ANY);
    server.sin_port = htons(port); /* The given port */
    serverptr = (struct sockaddr *)&server;
    serverlen = sizeof server;
    char buf[256];

    if (bind(sock, serverptr, serverlen) < 0)
    { /* Bind socket to an address */
        perror("bind");
        exit(1);
    }
    if (listen(sock, 5) < 0)
    { /* Listen for connections */
        perror("listen");
        exit(1);
    }
    while (1)
    {
        clientptr = (struct sockaddr *)&client;
        clientlen = sizeof client;
        if ((newsock = accept(sock, clientptr, &clientlen)) < 0)
        {
            perror("accept");
            exit(1);
        }
        retrive(newsock, "marios");
    }

    return 0;
}
#endif
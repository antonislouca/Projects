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

int authenticateusername(server *s, char *username)
{
    int socket = s->new_socket;
    DIR *dir;
    char str[512];
    char *token[10];
    char recived_message[MAXLENGTH] = " ";
    char login[512];
    char pass[512];
    sprintf(str, "./DEMO/%s", username);
    if ((dir = opendir(str)))
    {
        closedir(dir);
        bzero(str, sizeof str);
        bzero(login, sizeof login);
        sprintf(str, "+OK %s is a valid mailbox\r\n", username);
        sprintf(login, "%s", username);
        if (sendResponse(socket, str) == EXIT_FAILURE)
            return EXIT_FAILURE;
        return EXIT_SUCCESS;
    }
    else
    {
        bzero(str, sizeof str);
        sprintf(str, "-ERR never heard of mailbox %s\r\n", username);
        if (sendResponse(socket, str) == EXIT_FAILURE)
            return EXIT_FAILURE;
        return -1;
    }
}
int authenticatepassword(server *s, char *username, char *pass)
{
    int socket = s->new_socket;
    char str[512];
    char *token[10];
    FILE *fd;
    if (!(fd = fopen("./DEMO/login.txt", "r")))
    {
        perror("open");
        return EXIT_FAILURE;
    }
    char line[500];
    char login_success = 0;
    while (fgets(line, sizeof(line), fd))
    {
        int lastindex = tokenize(line, token, ":\n\r");
        if (strcmp(token[0], username) == 0 && strcmp(token[1], pass) == 0)
        {
            login_success = 1;
            break;
        }
    }
    fclose(fd);
    if (login_success)
    {
        bzero(str, sizeof str);
        sprintf(str, "+OK maildrop locked and ready\r\n");
        if (sendResponse(socket, str) == EXIT_FAILURE)
            return EXIT_FAILURE;
        strcat(s->INBOX, username);
        strcat(s->INBOX, "/");
        size_t temp = 0;
        strcpy(s->username, username);
        dsize(s, &(s->messages), &temp, TRUE);

        return EXIT_SUCCESS;
    }
    else
    {
        bzero(str, sizeof str);
        sprintf(str, "-ERR invalid password\r\n");
        if (sendResponse(socket, str) == EXIT_FAILURE)
            return EXIT_FAILURE;
        return -1;
    }
}
//int authenticate(server *s, char *username)
//{
//    int socket = s->new_socket;
//    DIR *dir;
//    char str[512];
//    char *token[10];
//    char recived_message[MAXLENGTH] = " ";
//    char login[512];
//    char pass[512];
//    sprintf(str, "./DEMO/%s", username);
//    if ((dir = opendir(str)))
//    {
//        closedir(dir);
//        bzero(str, sizeof str);
//        bzero(login, sizeof login);
//        sprintf(str, "+OK %s is a valid mailbox\r\n", username);
//        sprintf(login, "%s", username);
//        if (write(socket, str, sizeof str) < 0)
//        {
//            perror("write");
//            return EXIT_FAILURE;
//        }
//        bzero(recived_message, sizeof recived_message);
//        if (read(socket, recived_message, sizeof recived_message) < 0)
//        {
//            perror("read");
//            return EXIT_FAILURE;
//        }
//        int lastindex = tokenize(recived_message, token, " ");
//
//        if (strcmp(token[0], "QUIT") == 0 || strcmp(token[0], "quit") == 0)
//        {
//            close(socket);
//            return EXIT_SUCCESS;
//        }
//        else if (strcmp(token[0], "PASS") == 0 || strcmp(token[0], "pass") == 0)
//        {
//            sprintf(pass, "%s", token[1]);
//            FILE *fd;
//            if (!(fd = fopen("./DEMO/login.txt", "r")))
//            {
//                perror("open");
//                return EXIT_FAILURE;
//            }
//            char line[500];
//            char login_success = 0;
//            while (fgets(line, sizeof(line), fd))
//            {
//                int lastindex = tokenize(line, token, ":");
//                if (strcmp(token[0], login) == 0 && strcmp(token[1], pass) == 0)
//                {
//                    login_success = 1;
//                    break;
//                }
//            }
//            fclose(fd);
//            if (login_success)
//            {
//                bzero(str, sizeof str);
//                sprintf(str, "+OK maildrop locked and ready\r\n");
//                if (write(socket, str, sizeof str) < 0)
//                {
//                    perror("write");
//                    return EXIT_FAILURE;
//                }
//                strcat(s->INBOX, username);
//                strcat(s->INBOX, "/");
//                size_t temp = 0;
//                strcpy(s->username, username);
//                dsize(s, &(s->messages), &temp, TRUE);
//
//                return EXIT_SUCCESS;
//            }
//            else
//            {
//                bzero(str, sizeof str);
//                sprintf(str, "-ERR invalid password\r\n");
//                if (write(socket, str, sizeof str) < 0)
//                {
//                    perror("write");
//                    return EXIT_FAILURE;
//                }
//                return -1;
//            }
//        }
//    }
//    else
//    {
//        bzero(str, sizeof str);
//        sprintf(str, "-ERR never heard of mailbox %s\r\n", username);
//        if (write(socket, str, sizeof str) < 0)
//        {
//            perror("write");
//            return EXIT_FAILURE;
//        }
//        return -1;
//    }
//    return EXIT_SUCCESS;
//}

#ifdef DEBUGLOGIN
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
        authenticate(newsock, "marios");
    }

    return EXIT_FAILURE;
}
#endif
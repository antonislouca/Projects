#include "mailserver.h"

int statistics(server *s)
{

    //variable init section
    DIR *dir;
    struct dirent *entry;
    struct stat statbuffer;
    long messageNum = 0;
    size_t mailboxSize = 0;
    char pathBuf[512] = {'0'}; //initialize to zero
    int inboxsize = strlen(s->INBOX);
    strcpy(pathBuf, s->INBOX);
    char msg[512] = {'0'};
    //printf("Directory %s \n", s->INBOX);
    if ((dir = opendir(s->INBOX)) == NULL) //open directory, maybe should be given as arg
    {
        perror("Failed to open directory.");
        return EXIT_FAILURE;
    }

    while ((entry = readdir(dir)) != NULL) //loop until all file in dir are read
    {                                      //TODO should not be counted if file is deleted

        if (strcmp(".", entry->d_name) == 0 || strcmp("..", entry->d_name) == 0)
        {
            continue; //ignore files that are . or ..
        }
        if (isDeleted(s, entry->d_name) == TRUE)
            continue; //skipp files marked as deleted

        messageNum++; // for each dir entry increase the message number
        strcat(pathBuf, entry->d_name);
        stat(pathBuf, &statbuffer); //get stats for the file in the directory
        mailboxSize += statbuffer.st_size;

// findSize(entry->d_name, &mailboxSize);
#ifdef DEBUGSTAT
        printf("FILE name: %s & size %ld \n", entry->d_name, statbuffer.st_size);
#endif

        //reset buffer
        //memset(pathBuf + sizeof(inbox) - 1, '0', BUFSIZ - sizeof(inbox));
        bzero(pathBuf + inboxsize, 512 - inboxsize);
    }

    // printf("+OK %ld %ld\r\n", messageNum, mailboxSize);
    // dsize(s, &messageNum, &mailboxSize, TRUE);
    sprintf(msg, "+OK %ld %ld\r\n", messageNum, mailboxSize);
    if (sendResponse(s->new_socket, msg) == 1)
    {
        return EXIT_FAILURE;
    }
    closedir(dir);
    return EXIT_SUCCESS;
}

#ifdef DEBUGSTAT

int main(int argc, char *argv[])
{
    server *s;
    mailserverinit(&s);
    strcat(s->INBOX, "antonis/");
    printf("Directory %s \n", s->INBOX);
    statistics(s);
    mailserverDestroy(&s);
    return EXIT_SUCCESS;
}

#endif

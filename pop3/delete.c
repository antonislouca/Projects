#include "mailserver.h"
// int prepareDeleted(char *inbox, char **deleted, int *length)
// {
//     int numberofMessages = 0;
//     DIR *dir;
//     struct dirent *entry;
//     if ((dir = opendir(inbox)) == NULL) //open directory, maybe should be given as arg
//     {
//         perror("Failed to open directory.");
//         return EXIT_FAILURE;
//     }

//     while ((entry = readdir(dir)) != NULL) //loop until all file in dir are read
//     {                                      //TODO should not be counted if file is deleted

//         if (strcmp(".", entry->d_name) == 0 || strcmp("..", entry->d_name) == 0)
//         {
//             continue; //ignore files that are . or ..
//         }
//         numberofMessages++;
//     }

//     // int size = 0;
//     *deleted = calloc(numberofMessages, sizeof(char)); //an arrays to keep the deleted IDs in
//     //using id -1 we can see if a message is marked or not for deletion
//     *(length) = numberofMessages;
//     return EXIT_SUCCESS;
// }
// int delete (char *lookup, int toBeDel, int size)
// {

//     char msg[1024] = {'0'};
//     if (size < (toBeDel) || (toBeDel) <= 0) //if index given is out of bounds
//     {
//         sprintf(msg, "-ERR no such message\r\n");
//         write(STDIN_FILENO, msg, strlen(msg));
//         return EXIT_FAILURE;
//     }
//     else
//     {
//         lookup[toBeDel - 1] = '1';
//         sprintf(msg, "-OK message %d deleted\r\n", toBeDel);
//         write(STDIN_FILENO, msg, strlen(msg));
//         return EXIT_SUCCESS;
//     }
// }

int delete (server *s, int toBedeleted)
{
    char msg[1024] = {'0'};
    int index = s->num_deleted;
    //check if message already deleted
    for (int i = 0; i < index; i++)
    {
        if (s->deleted[i] == toBedeleted)
        {
            sprintf(msg, "-ERR message %d already deleted\r\n", toBedeleted);
            if (sendResponse(s->new_socket, msg) == 1)
            {
                return EXIT_FAILURE;
            }
            return EXIT_FAILURE;
        }
    }

    //check if message exitss in inbox
    DIR *dir;
    struct dirent *entry;
    int flag = FALSE;
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
        int tempid = atoi(entry->d_name);
        // printf("Temp id %d\n", tempid);
        if (toBedeleted == tempid)
        {
            flag = TRUE;
            break;
        }
    }

    if (flag == TRUE)
    {

        if ((s->deletedArrayMax) == index)
        { //need to alocate more memory
            int newsize = (s->deletedArrayMax) + ADDSZ;
            int *new = (int *)calloc(newsize, sizeof(int));

            if (new == NULL)
            {
                perror("Failed to realocate memory");
                exit(-1);
            }
            memcpy(new, s->deleted, (index));
            free(s->deleted);               //free prev array
            s->deleted = new;               //change pointers to new array
            (s->deletedArrayMax) = newsize; //update size
            // for (int i = 0; i < s->num_deleted; i++)
            // {
            //     printf("deleted: %d \n", s->deleted[i]);
            // }
            // printf("Realocated memory, new size is %d\n", s->deletedArrayMax);
        }

        s->deleted[index] = toBedeleted; //add message to be deleted to list
        (s->num_deleted)++;              //increase index
        sprintf(msg, "+OK message %d deleted\r\n", toBedeleted);
        if (sendResponse(s->new_socket, msg) == 1)
        {
            return EXIT_FAILURE;
        }
        // write(s->new_socket, msg, strlen(msg));
        return EXIT_SUCCESS;
    }
    else //then message does not exist
    {
        sprintf(msg, "-ERR no such message\r\n");
        if (sendResponse(s->new_socket, msg) == 1)
        {
            return EXIT_FAILURE;
        }
        return EXIT_FAILURE;
    }
}

#ifdef DEBUGDEL

int main(int argc, char *argv[])
{

    server *s;
    mailserverinit(&s);
    strcat(s->INBOX, "antonis/");
    printf("Directory %s \n", s->INBOX);
    statistics(s);
    delete (s, 5);
    delete (s, 139);
    delete (s, -1);
    for (int i = 0; i < s->num_deleted; i++)
    {
        printf("deleted: %d \n", s->deleted[i]);
    }
    statistics(s);
    mailserverDestroy(&s);
    // char *deleted = NULL;
    // int size = 0;

    // // prepareDeleted(dummyInbox, &deleted, &size);
    // //  printf("Size: %d\n", size);
    // int *deleted = (int *)malloc(sizeof(int) * size);
    // int tobedel = 139;
    // int index = 0;
    // delete (deleted, &index, tobedel, dummyInbox);
}

#endif

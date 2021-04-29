#include "mailserver.h"

int update(server *s)
{
    int failed = 0;
    for (int i = 0; i < s->num_deleted; i++)
    {
        //check if file is deleted
        char buf[512] = {'0'};
        sprintf(buf, "%s%d", s->INBOX, s->deleted[i]);
        //printf("DEBUG %s\n", buf);
        if (remove(buf) != 0)
        {
            failed++;
        }
        else
        {
            (s->messages)--;
        }
    }
    return failed;
}

#ifdef DEBUGUPDATE

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
    update(s);
    mailserverDestroy(&s);
}

#endif
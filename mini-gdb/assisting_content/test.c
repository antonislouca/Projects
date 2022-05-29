#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv)
{
    size_t count = atoi(argv[1]);
    for (size_t i = 0; i < count; i++)
    {
        fprintf(stdout, "Iteration: %d\n", i + 1);
    }
}
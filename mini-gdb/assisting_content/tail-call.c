#include <stdlib.h>
#include <stdio.h>
int bar(void)
{
    printf("This is bar\n");
    printf("This is bar2\n");
    return 1;
}
int foo(void)
{
    printf("This is foo\n");
    return bar();
}
int main(int argc, char *argv[])
{
    printf("This is main\n");
    return foo();
}
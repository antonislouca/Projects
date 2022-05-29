#include <stdio.h>
// void a()
// {
//     int foo = 1;
//     printf("foo: %d\n", foo);
// }

// void b()
// {
//     int foo = 2;
//     printf("foo: %d\n", foo);
//     a();
// }

// void c()
// {
//     int foo = 3;
//     printf("foo: %d\n", foo);
//     b();
// }

// void d()
// {
//     int foo = 4;
//     printf("foo: %d\n", foo);
//     c();
// }

// void e()
// {
//     int foo = 5;
//     printf("foo: %d\n", foo);
//     d();
// }

void f()
{
    int foo = 6;
    printf("foo: %d\n", foo);
    // e();
}

int main()
{
    for (size_t i = 0; i < 10; i++)
    {
        f();
    }
}

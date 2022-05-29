int foo(char i)
{
    switch (i)
    {
    case 'a':
        return 2;
        break;
    case 'b':
        return 13;
        break;
    case 'c':
        return 24;
        break;
    case 'd':
        return 35;
        break;
    case 'e':
        return 46;
        break;
    default:
        return -1;
    }
}
int main(int argc, char const *argv[])
{
    foo('a');
    return 0;
}

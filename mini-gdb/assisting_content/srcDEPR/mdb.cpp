#include "mdb_headers.hpp"

int main(int argc, char *argv[])
{
    // execution paradigm:
    // cat /proc/PID/maps
    // ./mdb.out /mnt/c/Users/santoryu/Desktop/Epl451hw2/src/hw2/src/tail-call.out
    if (argc <= 1)
        die("min_strace <program>: %d", argc);

    std::cerr << "Filename:" << argv[1] << std::endl;
    debugger *mdb = new debugger(argv[1]);
    // the struct that contains the elf file representation

    begin_debuging(mdb, argv);

    //!  test that elf is loaded correctly

    //! free section
    delete (mdb);
}

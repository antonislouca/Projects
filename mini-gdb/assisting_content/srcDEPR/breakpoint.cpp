#include "breakpoint.hpp"

breakpoint::breakpoint(long address, bool active, pid_t pid)
{
    this->address = address;
    this->active = active;
    this->pid = pid;
    old_code = 0;
}

breakpoint::~breakpoint()
{
}
bool breakpoint::is_active()
{
    return active;
}

long breakpoint::get_address()
{
    return address;
}

long breakpoint::get_oldcode()
{
    return old_code;
}

bool breakpoint::enable_bp()
{
    if (DEBUG)
        std::cerr << std::hex << "0x" << address << std::dec << " pid: " << pid << std::endl;
    /* Backup current code.  */

    old_code = ptrace(PTRACE_PEEKDATA, pid, (void *)address, 0);
    if (old_code == -1)
    {
        die_bp("(peekdata) %s", strerror(errno));
        return false; //never executed

    } // address given does not exist return

    //* fprintf(stderr, "0x%p: 0x%lx\n", (void *)address, old_code);

    /* Insert the breakpoint. modify only one byte with int3 not all */
    long trap = (old_code & 0xFFFFFFFFFFFFFF00) | 0xCC;
    if (ptrace(PTRACE_POKEDATA, pid, (void *)address, (void *)trap) == -1)
        die_bp("(pokedata) %s", strerror(errno));

    active = true;

    return true;
    // /* Resume process.  */
    // if (ptrace(PTRACE_CONT, pid, 0, 0) == -1)
    //     die_bp("(cont) %s", strerror(errno));
}
bool breakpoint::disable_bp()
{
    if (DEBUG)
        fprintf(stderr, "Old code: %lx\n", old_code);

    // reset code to regular state
    if (ptrace(PTRACE_POKEDATA, pid, (void *)address, (void *)old_code) == -1)
        die_bp("(pokedata) %s", strerror(errno));

    active = false; // set breakpoint as disabled
    return true;
}
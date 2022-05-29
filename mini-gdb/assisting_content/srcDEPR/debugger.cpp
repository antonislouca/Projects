#include "mdb_headers.hpp"
debugger::debugger(const char *filename)
{
    this->elf = new elf_file;
    this->symbol_mappings = new std::map<std::string, long>();
    this->active_breakpoints = 0;
    // this->pedding_Waits = 0;
    this->tracee_pid = 0;
    this->breakpoints = new std::map<std::intptr_t, breakpoint>();
    this->bp_q = new std::list<long>();
    this->running = false;
    this->base_address = 0;

    this->is_reapply = false;
    this->disas = new disassembler(filename);
    load_elf(filename, elf);             // load and initialize elf
    debug_symbols(elf, symbol_mappings); // map symbols and addresses
}

debugger::~debugger()
{
    delete this->elf;
    delete this->symbol_mappings;
    delete this->breakpoints;
    delete this->bp_q;
    delete this->disas;
}

// getters and setters:
// int debugger::get_pending_waits()
// {
//     return this->pedding_Waits;
// }
bool debugger::is_running()
{
    return this->running;
}
void debugger::set_running(bool value)
{
    this->running = value;
}

int debugger::get_bp_q_size()
{
    return this->bp_q->size();
}

// void debugger::set_pending_waits(int number)
// {
//     this->pedding_Waits = number;
// }

std::map<std::string, long> *debugger::get_symbol_mappings()
{
    return this->symbol_mappings;
}

elf_file *debugger::get_elf()
{
    return this->elf;
}
int debugger::get_active_breakpoints_number()
{
    return this->active_breakpoints;
}

void debugger::set_active_breakpoints_number(int number)
{
    this->active_breakpoints = number;
}

pid_t debugger::get_tracee_pid()
{
    return this->tracee_pid;
}

long debugger::get_base_Addr()
{
    return this->base_address;
}

void debugger::set_base_Addr(long address)
{
    this->base_address = address;
}
void debugger::start_tracing(char *argv[])
{
    /*
   fork() for executing the program that is analyzed.
   create the tracee process
   */

    pid_t pid = fork();
    switch (pid)
    {
    case -1: /* error */
        die("%s", strerror(errno));
    case 0: /* Code that is run by the child. */
        /* Start tracing.  */
        personality(ADDR_NO_RANDOMIZE); //@ disable ASLR

        ptrace(PTRACE_TRACEME, 0, 0, 0);
        /* execvp() is a system call, the child will block and
           the parent must do waitpid().
           The waitpid() of the parent is in the label
           waitpid_for_execvp.
         */
        execvp(argv[1], argv + 1);
        die("%s", strerror(errno));
    }
    this->tracee_pid = pid;
    is_traced = true;
    ptrace(PTRACE_SETOPTIONS, pid, 0, PTRACE_O_EXITKILL);
    waitpid(pid, 0, 0); // wait for sigtrap singal from child process
}

bool debugger::continue_exec()
{
    if (ptrace(PTRACE_CONT, tracee_pid, 0, 0) == -1)
        die("(cont) %s", strerror(errno));

    int status;
    if (waitpid(tracee_pid, &status, 0) == -1)
    {
        perror("waitpid failed\n");
        exit(EXIT_FAILURE);
    }

    if (WIFEXITED(status))
    {
        std::cerr << "Debugee process exited." << std::endl;
        running = false;   // set that process has now stopped running
        is_traced = false; // set that process stopped being traced
        return false;
    }
    return true;
}

void debugger::print_active_bp()
{
    std::cerr << "Active breakpoints: "
              << this->active_breakpoints << std::endl;

    int count = 1;
    for (auto itr = breakpoints->begin(); itr != breakpoints->end(); itr++)
    {
        fprintf(stderr, "Breakpoint %d: @ 0x%lx\n", count, (*itr).first);

        count++;
    }
}

void debugger::print_symbols()
{
    for (auto itr = symbol_mappings->begin(); itr != symbol_mappings->end(); itr++)
    {
        fprintf(stderr, "Symbol: %s @ 0x%ld\n", (*itr).first.c_str(), (*itr).second);
    }
}

void debugger::reapply_bp()
{
    this->is_reapply = true; // set reapply to true
    for (auto itr = breakpoints->begin(); itr != breakpoints->end(); itr++)
    {
        this->set_breakpoint((*itr).first, tracee_pid);
    }

    this->is_reapply = false; // restore reapply flag to false when we are done
}

void debugger::set_bp_symbol(std::string symbol)
{
    if (DEBUG)
        std::cerr << "symbol: " << symbol << std::endl;

    // check if key exists in map
    if (this->symbol_mappings->count(symbol) > 0)
    {
        // get address using symbol mappings
        long address = this->symbol_mappings->at(symbol);

        // calculate new address by addding base address
        address = address + this->base_address;

        std::stringstream stream;
        stream << address << std::dec;
        // std::cout << address << "stream:" << stream.str() << std::endl;

        // convert string to hex
        address = stol(stream.str(), 0, 16);
        // add break point using the same method as the address one
        this->set_breakpoint(address, tracee_pid);
    }
    else
    {
        std::cerr << "Cannot add breakpoint. Invalid symbol." << std::endl;
    }
}
/*
We want to take an address, set the break point for that address
and store in a map the original instruction for that break point.
*/
void debugger::set_breakpoint(long breakpoint_address, pid_t pid)
{

    if (breakpoints->count(breakpoint_address) > 0 && !this->is_reapply)
    {
        std::cerr << "Breakpoint already exists" << std::endl;
        return;
    }

    // todo check if address in text segment

    breakpoint bp = {breakpoint_address, true, pid};
    if (bp.enable_bp())
    {
        // add break point to map
        breakpoints->insert(std::make_pair(breakpoint_address, bp));
    }
    else
    {
        std::cerr << "Breakpoint could not be set.\nAddress 0x"
                  << breakpoint_address
                  << " does not match to code segment\n";
        exit(-1);
        return;
    }

    // active_breakpoints++; // increase active break points
    fprintf(stderr, "Breakpoint added successfully @ 0x%lx\nActive breakpoints: %ld\n",
            breakpoint_address, breakpoints->size());
}
void debugger::delete_bp(int indext_bp)
{
    int count = 1;
    for (auto itr = breakpoints->begin(); itr != breakpoints->end(); itr++)
    {
        if (count == indext_bp)
        { // if the count is equal to the index, delete that element
            (*itr).second.disable_bp();
            breakpoints->erase((*itr).first); // this is ok since only one element is
                                              // removed per call
            break;
        }
        count++;
    }
}

void debugger::serve_breakpoint()
{
    struct user_regs_struct regs;

    if (DEBUG)
        fprintf(stderr, "Process id %d", tracee_pid);

    if (ptrace(PTRACE_GETREGS, tracee_pid, 0, &regs) == -1)
        die("(getregs) %s", strerror(errno));

    // regs.rip;
    unsigned long long rip = regs.rip - this->base_address;
    try
    {

        breakpoint bp = this->breakpoints->at(rip);
        fprintf(stderr, "Serving BP @ 0x%lx\n",
                bp.get_address());
        // to restore original code

        // call process_inspect
        this->process_inspect();

        bp.disable_bp(); // disable break point, RESTORE OLD CODE

        regs.rip = bp.get_address();
        if (ptrace(PTRACE_SETREGS, tracee_pid, 0, &regs) == -1)
            die("(setregs) %s", strerror(errno));

        // bp.enable_bp(); //TODO enable break point again to hit it again
        // todo we can restore single step and then reapply breakpoint
    }
    catch (const std::out_of_range &e)
    {
        fprintf(stderr, "Rip out of bounds @ 0x%llx\n",
                rip);
        exit(EXIT_FAILURE);
    }
}
void debugger::process_inspect()
{
    struct user_regs_struct regs;

    if (ptrace(PTRACE_GETREGS, tracee_pid, 0, &regs) == -1)
        die("%s", strerror(errno));

    long current_ins = ptrace(PTRACE_PEEKDATA, tracee_pid, regs.rip, 0);
    if (current_ins == -1)
        die("(peekdata) %s", strerror(errno));

    fprintf(stderr, "=> 0x%llx: 0x%lx\n", regs.rip, current_ins);
}
void debugger::process_step()
{
    while (1)
    {
        if (ptrace(PTRACE_SINGLESTEP, tracee_pid, 0, 0) == -1)
            die("(singlestep) %s", strerror(errno));

        // waitpid(pid, 0, 0);
    }
}
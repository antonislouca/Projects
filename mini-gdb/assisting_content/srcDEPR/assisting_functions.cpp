#include "mdb_headers.hpp"
void Usage()
{
    print_char(80, '=');
    std::cerr
        << "Supported commamnds :\n"
        << "b: [symbol | *address]\n"
        << "l: List active breakpoints\n"
        << "d n: Delete n-th breakpoint\n"
        << "r: Run program\n"
        << "c: Continue execution\n"
        << "q: Quit"
        << std::endl;
    print_char(80, '=');
}
void print_char(int times, const char ch)
{
    for (int i = 0; i < times; i++)
        putc(ch, stderr);

    putc('\n', stderr);
}

long Read_base_Address(pid_t pid)
{
    char filename[256];
    sprintf(filename, "/proc/%d/maps", pid);
    std::ifstream proc_maps(filename); // open /procs/maps in read only mode
    std::string line;
    std::getline(proc_maps, line);
    std::string address = line.substr(0, line.find('-'));
    return std::stol(address);
}
void run(debugger *mdb)
{
    mdb->set_running(true);
    // mdb->serve_breakpoint();
    // mdb->continue_exec();

    if (mdb->continue_exec())
    { // to start the process still runs
      // we can serve the breakpoint
        mdb->serve_breakpoint();
    }
}
void begin_debuging(debugger *mdb, char *argv[])
{
    const char *prompt = ">>> ";
    char *input_buf = new char[maxbuf];
    Usage();

    mdb->start_tracing(argv); // set up ptrace and create tracee process

    /*
     sets the base address after reading it from proc/pid/maps
    */

    mdb->set_base_Addr(Read_base_Address(mdb->get_tracee_pid()));

    fprintf(stderr,
            "Debugging process initialized: debugee PID= %d Base address: 0x%ld\n",
            mdb->get_tracee_pid(), mdb->get_base_Addr());

    std::string input;

    mdb->print_symbols();
    while (1)
    {
        // fprintf(stderr, "Active breakpoints: %d\n", mdb->get_active_breakpoints_number());
        // writing the prompt
        if (write(STDERR_FILENO, prompt, 4) < 0)
        {
            perror("Error while writing");
            exit(-1);
        }

        // read from user blocking syscall
        if (read(STDIN_FILENO, input_buf, maxbuf) < 0)
        {
            bzero(input_buf, maxbuf);
            continue;
        }

        // begin command switch
        // check first letter in input string to find the command

        input.assign(input_buf);

        if (DEBUG)
            std::cerr
                << "Input: "
                << input
                << "Input length: "
                << input.length()
                << std::endl;

        if (strncmp(input_buf, "b ", 2) == 0)
        {
            // command b *address: set breakpoint using address notation
            if (strncmp(input_buf, "b *0x", 5) == 0)
            {
                // TODO check if breakpoint address is valid
                long address = std::stol(
                    input.substr(5, input.length() - 1), 0, 16);

                if (DEBUG)
                    fprintf(stderr, "Address: %lx\n", address);

                mdb->set_breakpoint(address, mdb->get_tracee_pid());
            }
            else // use the symbol notation to set breakpoints
            {
                mdb->set_bp_symbol(input.substr(2, input.length() - 3));
            }
        }
        // command l
        else if (strncmp(input_buf, "l", 1) == 0)
        {
            mdb->print_active_bp();
        }
        // command d
        else if (strncmp(input_buf, "d ", 2) == 0)
        {
            int index_bp = std::atoi(input.substr(2, input.length() - 1).c_str());
            mdb->delete_bp(index_bp);
        }
        // command r
        else if (strncmp(input_buf, "r", 1) == 0)
        {
         
            if (ptrace(PTRACE_CONT, mdb->get_tracee_pid(), 0, 0) == -1)
                die("(cont) %s", strerror(errno));

            waitpid(mdb->get_tracee_pid(), 0, 0);

            // run(mdb);
            // if (!mdb->is_running() && mdb->is_traced)
            // {
            //     run(mdb); // call the run function
            // }
            // else if (mdb->is_running() && mdb->is_traced)
            // {
            //     // child process is already running

            //     fprintf(stderr,
            //             "Child process already running. Do you want to restart? (Y/n)\n");
            //     char answer;
            //     std::cin >> answer; // get input
            //     if (answer == 'Y' || answer == 'y')
            //     { // todo check that this works

            //         // we need to reset the process if user wants to restart else skip

            //         // kill child process
            //         kill(mdb->get_tracee_pid(), SIGKILL);

            //         // set up an new tracee process and replace pid
            //         mdb->start_tracing(argv);

            //         // reapply all breakpoints to new tracee process
            //         mdb->reapply_bp();

            //         run(mdb);
            //     }
            // }
            // else
            // {
            //     // tracing is not yet started

            //     // set up an new tracee process and replace pid
            //     mdb->start_tracing(argv);

            //     // reapply all breakpoints to new tracee process
            //     mdb->reapply_bp();
            //     run(mdb);
            // }
        }
        // command c
        else if (strncmp(input_buf, "c", 1) == 0)
        {
            if (mdb->is_running() && mdb->is_traced)
            {
                // mdb->serve_breakpoint();
                // mdb->continue_exec();
                if (mdb->continue_exec()) // if process did not exit yet
                {
                    mdb->serve_breakpoint();
                }
            }
            else
            {
                std::cerr << "Program is not running." << std::endl;
            }
        }
        // command si
        else if (strncmp(input_buf, "si", 2) == 0)
        {
            // TODO
            //  mdb->process_step(tracee_pid);
        }
        // command disas
        else if (strncmp(input_buf, "disas", 5) == 0)
        {
            // TODO DISAS COMMAND
        }
        // use q to quit
        else if (strncmp(input_buf, "q", 1) == 0)
        {
            std::cerr << "Debugging process terminated." << std::endl;

            // free if we need to free anything
            // add a function for this
            delete (input_buf);
            return;
        }
        else
        {
            std::cerr << "Unsupported command\n";
            Usage();
        }

        // nullify input buffer for reuse
        bzero(input_buf, maxbuf);
        input.clear();
    }
}
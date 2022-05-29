#pragma once
/* C standard library */
#include <errno.h>
#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include <stddef.h>

/* POSIX */
#include <unistd.h>
#include <sys/user.h>
#include <sys/wait.h>
#include <libelf.h>
#include <gelf.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <bits/stdc++.h>

/* Linux */
#include <syscall.h>
#include <sys/ptrace.h>

/*C++ headers*/
#include <iostream>
#include <string>
#include <map>
#include <list>

#include "elf_loader.hpp"
#include "breakpoint.hpp"
#include "disassembler.hpp"

#define TOOL "mdb"
#define maxbuf 1024
#define DEBUG 1

//@ macros

#define die(...)                                \
    do                                          \
    {                                           \
        fprintf(stderr, TOOL ": " __VA_ARGS__); \
        fputc('\n', stderr);                    \
        exit(EXIT_FAILURE);                     \
    } while (0)

//@structs and classes

class debugger
{
private:
    elf_file *elf;                                    // mapped elf representation
    std::map<std::string, long> *symbol_mappings;     // symbol mappings
    std::map<std::intptr_t, breakpoint> *breakpoints; // active breakpoints
    long base_address;
    int active_breakpoints;

    disassembler *disas;
    // int pedding_Waits;
    pid_t tracee_pid;
    bool running = false;
    bool is_reapply;
    std::list<long> *bp_q;
    void process_inspect();

public:
    bool is_traced = false;
    /*getter function that returns trace pid*/
    pid_t get_tracee_pid();
    /*getter function for the mapped elf*/
    elf_file *get_elf();

    /*getter function for the number of active breakpoints*/
    int get_active_breakpoints_number();

    /*setter function for active breakpoitns*/
    void set_active_breakpoints_number(int number);

    // /*getter function for the number of pending waits*/
    // int get_pending_waits();

    /*getter function for the number of pending breakpoints*/
    int get_bp_q_size();

    // /*setter function for  pending waits*/
    // void set_pending_waits(int number);

    /*getter function for the base address*/
    long get_base_Addr();

    /*setter function for the base address*/
    void set_base_Addr(long address);

    /*
    returns the status of the process if is running or not
    */
    bool is_running();

    /*sets the running variable to true or false*/
    void set_running(bool value);

    /*getter function for symbol mappings*/
    std::map<std::string, long> *get_symbol_mappings();

    // constructor
    debugger(const char *filename);
    //  destructor
    ~debugger();

    /*
    Begins the tracing process, by creating tracer and tracee processes
    */
    void start_tracing(char *argv[]);
    /*
    Function that is called using the debugger object and sets the break point
    for the given address.
    */
    void set_breakpoint(long breakpoint_address, pid_t pid);

    /*
    function that serves each break point, it restores original code
    and resets the program counter to execute that command
     */
    void serve_breakpoint();

    /*function that performs the step instruction command*/
    void process_step();

    /*
    function that tells the process to continue and then wait for signal
    Returns true if child process is still running
    Reuturn false if child process exited, thus continuing execution was not
    succesful
    */
    bool continue_exec();

    /*function that prints a list of the active breakpoints*/
    void print_active_bp();

    /*
    function that deletes breakpoint @ the index given and uses ptrace to
    restore the code to that particular address
    */
    void delete_bp(int indext_bp);

    /*function that sets a breakpoint using symbol str*/
    void set_bp_symbol(std::string input);

    /*function that prints symbols*/
    void print_symbols();

    /*function for reapplying already set breakpoints if exist*/
    void reapply_bp();
};

//@ assisting function signatures
// function that begins the debugging process
void begin_debuging(debugger *mdb, char *argv[]);
/*prints the usage of mdb tool*/
void Usage();

/*prints input character times times*/
void print_char(int times, const char ch);

/*
reads /proc/pid/maps to get the base address for the
child process
*/
long Read_base_Address(pid_t pid);

/*command that executes the runnning input*/
void run(debugger *mdb);

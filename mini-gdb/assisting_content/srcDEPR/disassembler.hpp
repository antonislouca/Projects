#pragma once
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include "capstone/capstone.h"
#include "elf_loader.hpp"
#include <string>

//@classes
class disassembler
{
private:
    csh handle;

public:
    disassembler(const char *filename);
    ~disassembler();
    void print_ins(cs_insn *ins);
    void disas_command(elf_file *elf);
};

//@function prototypes

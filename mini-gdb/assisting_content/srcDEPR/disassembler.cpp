#include "disassembler.hpp"

disassembler::disassembler(const char *filename)
{
    /*initialized engine*/
    if (cs_open(CS_ARCH_X86, CS_MODE_64, &handle) != CS_ERR_OK)
    {
        fprintf(stderr, "Could not initialize capstone. Exiting...\n");
        exit(-1);
    }
    /* AT&T */
    cs_option(handle, CS_OPT_SYNTAX, CS_OPT_SYNTAX_ATT);
}

disassembler::~disassembler()
{
    cs_close(&handle);
}

void disassembler::print_ins(cs_insn *ins)
{

    fprintf(stderr, "0x%016lx:\t%s\t\t%s\n", ins->address, ins->mnemonic, ins->op_str);
}

// void disassembler::disas_command(unsigned char *command, elf_file *elf)
// {
// }
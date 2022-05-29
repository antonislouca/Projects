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

/* Linux */
#include <syscall.h>
#include <sys/ptrace.h>

/*C++ headers*/
#include <iostream>
#include <string>
#include <map>

//@macros
#define DIE_elf(...)                  \
    do                                \
    {                                 \
        fprintf(stderr, __VA_ARGS__); \
        fputc('\n', stderr);          \
        exit(EXIT_FAILURE);           \
    } while (0)

//@ function prototypes

typedef struct struct_elf
{
    Elf *elf;              // the elf given
    Elf_Scn *symtab;       // used for  symbol table
    Elf_Scn *dynsym;       // used for dynamic symbol table
    size_t shstrtab_index; // used for section string table
    bool is_stripped;
    Elf_Data *text;
    long unsigned text_start = 0;
    long unsigned text_end = 0;
} elf_file;
/*
function that loads the elf in the elf_file struct using libelf
*/
void load_elf(const char *filename, elf_file *elf);
void debug_symbols(elf_file *elf, std::map<std::string, long> *symbol_mappings);
/*
Retrieves section header based on the section index of the symbol
*/
void get_shdr_from_symbol(GElf_Shdr *sym_shdr, Elf *elf, Elf64_Section section_index);

/*
Performs the resolution from symbol type numeric to symbol type character
Resolution is done as follows:
A:  Absolute symbol, global
a:  Absolute symbol, local
B:  Uninitialized data (bss), global
b:  Uninitialized data (bss), local
C:  Common symbol
D:  Initialized data , global
d:  Initialized data , local
R:  Symbol is read only, global
r:  Symbol is read only, local
T:  Text symbol, global
t:  Text symbol, local
U:  Undefined symbol
u:  unique global symbol
V:  symbol is weak object global
v:  symbol is a weak object local
W:  Symbol is a weak symbol global
w:  symbol is wak symbol local
?:  Symbol is unknown
*/
char get_symbol_type(GElf_Sym sym, GElf_Shdr shdr);
/*
This function will return the letter as is or convert it to a lower case one
if the symbol is a local stab
*/
char is_local(char c, unsigned char info);
/*
Retrieves section header based on the section index of the symbol
*/
void get_shdr_from_symbol(GElf_Shdr *sym_shdr, Elf *elf, Elf64_Section section_index);

// bool in_text(long address);
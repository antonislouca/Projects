#include "mdb_headers.hpp"

void load_elf(const char *filename, elf_file *elf_obj)
{
    elf_obj->dynsym = NULL;
    elf_obj->symtab = NULL;
    elf_obj->is_stripped = 0;
    elf_obj->shstrtab_index = 0;
    // check version
    if (elf_version(EV_CURRENT) == EV_NONE)
        DIE_elf("(elf_version) %s", elf_errmsg(-1));

    int fd = open(filename, O_RDONLY);
    elf_obj->elf = elf_begin(fd, ELF_C_READ, NULL);
    if (elf_obj->elf == NULL)
        DIE_elf("(Not an elf file) %s", elf_errmsg(-1));

    Elf_Scn *scn = NULL; // section
    GElf_Shdr shdr;      // section header

    // get section strtab index
    if (elf_getshdrstrndx(elf_obj->elf, &elf_obj->shstrtab_index) != 0)
        DIE_elf("(elf_getshdrstrndx) %s", elf_errmsg(-1));
    while ((scn = elf_nextscn(elf_obj->elf, scn)) != NULL)
    {
        if (gelf_getshdr(scn, &shdr) != &shdr)
            DIE_elf("(gelf_getshdr) %s", elf_errmsg(-1));

        // get symtab section
        if (!strcmp(elf_strptr(elf_obj->elf, elf_obj->shstrtab_index, shdr.sh_name),
                    ".symtab"))
        {
            elf_obj->symtab = scn;
        }
        if (!strcmp(elf_strptr(elf_obj->elf, elf_obj->shstrtab_index, shdr.sh_name), ".text"))
        {
            elf_obj->text = elf_getdata(scn, elf_obj->text);
            if (!elf_obj->text)
                DIE_elf("(getdata) %s", elf_errmsg(-1));
            elf_obj->text_start = shdr.sh_addr;
            elf_obj->text_end = shdr.sh_size + elf_obj->text_start;
        }

        // get dynsym section
        if (!strcmp(elf_strptr(elf_obj->elf, elf_obj->shstrtab_index, shdr.sh_name),
                    ".dynsym"))
        {
            elf_obj->dynsym = scn;
        }
    }

    // set boolean if symtab does not exist
    if (elf_obj->symtab == NULL)
    {
        elf_obj->is_stripped = 1;
    }

    std::cerr
        << "Binary file locked and loaded" << std::endl;
}

void debug_symbols(elf_file *elf, std::map<std::string, long> *symbol_mappings)
{
    GElf_Shdr symtab_shdr;

    /* Get the descriptor.  */
    if (gelf_getshdr(elf->symtab, &symtab_shdr) != &symtab_shdr)
        DIE_elf("(error getting symbtab section header) %s", elf_errmsg(-1));

    // get symtab section data --> the indices to strtab
    Elf_Data *data = elf_getdata(elf->symtab, NULL);

    // find how many entries are stored in section using entry size
    int num_of_entries = symtab_shdr.sh_size / symtab_shdr.sh_entsize;

    for (int i = 0; i < num_of_entries; i++)
    {
        GElf_Sym sym;
        // get i-th symbol from data adn store it in sym
        gelf_getsym(data, i, &sym);
        // print symbol if is not a section or a file related symbol

        // MAP SYMBOLS THAT BELONG TO FUNCTIONS ONLY
        if (ELF64_ST_TYPE(sym.st_info) == STT_FUNC)
        {
            // GElf_Shdr sym_shdr;
            // get_shdr_from_symbol(&sym_shdr, elf->elf, sym.st_shndx);

            // // find symbol type
            // char type = get_symbol_type(sym, sym_shdr);
            if (sym.st_value >= elf->text_start && sym.st_value < elf->text_end)
            {
                symbol_mappings->insert(std::make_pair(
                    elf_strptr(elf->elf, symtab_shdr.sh_link, sym.st_name), sym.st_value));
            }
            // add symbol to map only if its part of the .text segment
            // if (type == 'T' || type == 't') // todo CHANGE THIS USING R DISAS WAY
            // {
            //     // std::cerr << "symbol name: "
            //     //           << elf_strptr(elf->elf, symtab_shdr.sh_link, sym.st_name)
            //     //           << "  symbol value: 0x"
            //     //           << sym.st_value << std::endl;

            //     symbol_mappings->insert(std::make_pair(
            //         elf_strptr(elf->elf, symtab_shdr.sh_link, sym.st_name), sym.st_value));
            //     // returns name of symbol
            //     // symbol address
            // }
        }
    }
}

char get_symbol_type(GElf_Sym sym, GElf_Shdr shdr)
{

    if (ELF64_ST_BIND(sym.st_info) == STB_GNU_UNIQUE) // symbol is unique global symbol
    {

        return is_local('u', sym.st_info);
    }
    else if (ELF64_ST_BIND(sym.st_info) == STB_WEAK) // weak symbol but not tagged as weak
    {

        if (sym.st_shndx == SHN_UNDEF)
        { // weak symbol and indefined

            return is_local('w', sym.st_info);
        }
        else
        { // weak symbol but defined
            return is_local('W', sym.st_info);
        }
    }
    else if (ELF64_ST_BIND(sym.st_info) == STB_WEAK && // symbol is a weak object
             ELF64_ST_TYPE(sym.st_info) == STT_OBJECT)
    {

        if (sym.st_shndx == SHN_UNDEF)
        {

            // weak object but undefined

            return is_local('v', sym.st_info);
        }
        else // weak object but defined
        {

            return is_local('V', sym.st_info);
        }
    }
    else if (sym.st_shndx == SHN_UNDEF) // undifined symbol
    {

        return is_local('U', sym.st_info);
    }
    else if (sym.st_shndx == SHN_ABS)
    {
        // the symbol value is absolute and wont change,

        return is_local('A', sym.st_info);
    }
    else if (sym.st_shndx == SHN_COMMON)
    { // the symbol is common

        return is_local('C', sym.st_info);
    }
    else if (shdr.sh_type == SHT_NOBITS &&
             shdr.sh_flags == (SHF_ALLOC | SHF_WRITE))
    { // symbol belongs to uniitialised data section .bss

        return is_local('B', sym.st_info);
    }
    else if (shdr.sh_type == SHT_PROGBITS && shdr.sh_flags == SHF_ALLOC)
    { // symbol in read only data section

        return is_local('R', sym.st_info);
    }
    else if (shdr.sh_type == SHT_PROGBITS &&
             shdr.sh_flags == (SHF_ALLOC | SHF_WRITE))
    { // symbol belongs to inited data section .data and is
        // global, section is writable

        return is_local('D', sym.st_info);
    }
    else if (shdr.sh_type == SHT_PROGBITS &&
             shdr.sh_flags == (SHF_ALLOC | SHF_EXECINSTR))
    { // text symbol global

        return is_local('T', sym.st_info);
    }
    else if (shdr.sh_type == SHT_DYNAMIC)
    { // has dynamic linking indofmration

        return is_local('D', sym.st_info);
    }

    else
    { //! default returns T which is text
      // c = ('t' - 32);
        return is_local('T', sym.st_info);
    }
}

char is_local(char c, unsigned char info)
{
    /*
    below code will convert if needed any upper case letter to a lower case letter
    this will be done when the symbol is a local stab but not the stab "?"
    lower case letters describe local symbols and upper case ones describe
    global symbols
    */
    if (ELF64_ST_BIND(info) == STB_LOCAL && c != '?')
        return c += 32;

    return c;
}

void get_shdr_from_symbol(GElf_Shdr *sym_shdr, Elf *elf, Elf64_Section section_index)
{
    Elf_Scn *scn = elf_getscn(elf, section_index);

    if (gelf_getshdr(scn, sym_shdr) != sym_shdr)
        DIE_elf("(getshdr shdr from symbol) %s", elf_errmsg(-1));
}
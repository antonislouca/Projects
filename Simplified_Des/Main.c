/*
 *
 * Copyright (C) 2021 Antonis Louca
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * Υou should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *  @file module.h
 *  The file contains the main function of simple des ecryption program
 *  Created on: Jan 31, 2021
 *  Author: Antonis Louca
 */
#include "Des.h"


//private function declaration section
/*
 *@brief the main function of the program 
 *@param argc number of parameters
 *@argv users command line input parameters
 */
 int  main (int argc, char **argv){

    element *el;

    if(argc==3){ // mode 2, 2 arguments
        initel (&el,argv[1],argv[2]);
     //   printf("arg1 %s, arg2 %s",el->plaintext,el->key);
    }else if (argc==1){  //mode 1 run with no arguments
        initel (&el,"01010001","0101001100");
   //     printf("no arguments given");
    }else{
        printf("Wrong input!\n""Correct Usage:\n"
	"Mode 1: Running with no options, the program runs with  default parameters\n"
           "(plaintext:01010001,key:0101001100)\n"
    "Mode 2: 2 parameters:<8-bit plaintext> <10-bit key>\n");
    exit(-1);
    }
    encryptP(el);
    printf("%s\n", el->ciphertext);
    freelem(el);
exit(0);
}


void initel (element **el,char *plaintext,char *key){
    (*el)=(element *) malloc(sizeof(element));
    (*el)->plaintext= (char *)malloc(sizeof(char)* strlen(plaintext)+1);
    // (*el)->ciphertext= (char *)malloc(sizeof(char)* strlen(plaintext)+1);
     (*el)->ciphertext=(char*) calloc(strlen(plaintext)+1,sizeof(char));
    (*el)->key= (char *)malloc(sizeof(char)* strlen(key)+1);
    (*el)->Ksize=  strlen(key);
    (*el)->Tsize=  strlen(plaintext);
    (*el)->round=  1;
    strcpy((*el)->key,key);
    strcpy((*el)->plaintext,plaintext);
}

void freelem(element *el){
    free(el->ciphertext);
    free(el->plaintext);
    free(el->key);
    free(el);
}

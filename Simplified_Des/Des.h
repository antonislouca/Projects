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
 *  Header file for DES encryption program
 *  Created on: Jan 31, 2021
 *  Author: Antonis Louca
 */

#ifndef DES_H
#define DES_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define public 
#define private static
#define DEBUG 0

typedef struct desstr{
    char * plaintext;
    char * ciphertext;  
    char * key;
    int Ksize,Tsize,round;
} element;


//function section

/*
 *@brief performs the ecryption of the given element's plaintext to cipher text
 *@param Desel a des element 
 */
public  void encryptP(element * Desel);
/*
 *@brief frees all the alocated space of the given element
 *@param el a des element 
 */
void freelem(element *el);
/*
 *@brief initializes the given struct pointer
 *@param el a des element to be initialized
 *@param plaintext the plaintext section to be converted
 *@param key the given text to encrypt the plaintext with
 */
void initel (element **el,char *plaintext,char *key);
#endif
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
 *  Encrypt file contains functions for the encryption of the plain text
 *  Created on: Jan 31, 2021
 *  Author: Antonis Louca
 */


#include "Des.h"

//private function declaration section

/*
 *@brief Performs the initial permutation of plain text. IP of DES algorithm.
 *@param el a des element 
 */
private void initialPermutation(element *el);

/*
 *@brief Performs all core steps of the function section of DES.
 Contains 4 steps:
 Step 1: E expansion
 Step 2: xor with key
 Step 3: s-BOX 
 Step 4: P4 permutation
 @param el a des element 
 @param sbResult the result of the funtion section
*/
private void function(element *el,char *sbResult);

/*
*@brief Expands the given text to more bits 
*@param ex the expanded text
*@param text the text to be expanded
*/
private void expand (char * ex,char * text);

/*
 *@brief key scheduling process  
 *@param K8 key for round i
 *@param key 10 bit key from previous round
 *@param round current round
 */
private void keyScheduling(char * K8,char * key,int round);

/*
 *@brief Rotates the given key by given number of rotations
 *@param key current key
 *@param given number of rotations
 */
private void rotate(char *key,int rotations);

/*
 *@brief Rotates given key by one rotation from left to right
 *@param key given key
 */
private void rotateByOne(char *key);

/*
 *@brief performs an xor function between the  two given parameters
 *@param exp 1 parameter
 *@param key xored by the key 
 */
private void xor(char * exp, char *key);

/*
 *@brief transforms the given character to int
 *@param x given character
 *@return integer corresponding to given character
 */
private int myAtoi(char x);

/*
 *@brief Contains the sbox functions, includes final permutation p4 of function F
 *@param result the arrays that is the output of the f FUNCTION
 *@param input input string of the sbox 
 */
private void Sbox(char *result,char *input);

/*
 *@brief converts a decimal given number to a binary one
 *@param dec the given decima number
 *@param bin the converted binary output
 */
private void dec2Bin(int dec,char *bin);

/*
 *@brief converts a binary given number to a decimal one
 *@param bin the input binary number
 *@return the output as a decimal 
 */
private int bin2dec(char *bin);

/*
 *@brief Performs the final permutation of plain text. and creates the ciphertext
 *@param el a des element 
 */
private void finalPermutaion(element * el);


//
//Implementation
//
public void encryptP(element *Desel){
  //  strcpy(Desel->ciphertext,"check");
    initialPermutation(Desel); //initial perm
    char *FResult=(char*) calloc(4+1,sizeof(char));
    char *lp=(char*) calloc(4+1,sizeof(char));
    char *rp=(char*) calloc(4+1,sizeof(char));

    for(int i=0;i<2;i++){

    if(DEBUG){  
    printf("\n\nSTATS:\nRound: %d \n",Desel->round);
    printf("Plaintext %s",Desel->plaintext);
    printf("\nKey %s\n",Desel->key);
    }
    function(Desel,FResult);

    if(DEBUG){  
    printf("\nFunction result %s ",FResult);
    }

    strncpy(lp, Desel->plaintext, 4);

    if(DEBUG){  
    printf("\nPlaintext left part: %s",lp);
    }

    xor(lp,FResult);
   
   if(DEBUG){  
    printf("\nFinal xor result: %s\n",lp);
   }


    strncpy(rp, Desel->plaintext+4, 4);
    
    //swap
    if((Desel->round)==1){
    strncpy(Desel->plaintext+4,lp , 4);
    strncpy(Desel->plaintext,rp , 4);
    }else{
    strncpy(Desel->plaintext+4,rp , 4);
    strncpy(Desel->plaintext,lp , 4);
    }
   // printf("plaintext %s ",Desel->plaintext);
    (Desel->round)++; 
     // do 2 encryption rounds 
    //increase round counter for next round

  
    }
    

    //when finish do final perm
    finalPermutaion(Desel);
    free(FResult);
    free(lp);
    free(rp);
}

private void finalPermutaion(element *el){
   int IPinv[8]= {3,0,2,4,6,1,7,5};
  //  char temp[el->Tsize];
    char *temp=(char *)malloc(sizeof(char)* el->Tsize+1);
    strcpy(temp,el->plaintext);

    for(int i=0;i<el->Tsize;i++){
        el->ciphertext[i]=temp[IPinv[i]];
}
free(temp);
}


//F function of cipher
private void function(element *el,char *sbResult){
//Step 1: expansion &keyscheduling
//Step 2: xor with Ki
//Step 3: s-Box//Step 4: premute P4


char *expanded=(char*) calloc(el->Tsize+1,sizeof(char));
//Step 1: expansion &keyscheduling
expand(expanded,el->plaintext); //expansion

if(DEBUG){  
printf("After expanssion: %s\n",expanded);
}


char *key=(char*) calloc(el->Tsize+1,sizeof(char));

// get key for round i
keyScheduling(key,el->key,el->round);

if(DEBUG){  
printf("Current rouund scheduling key result: %s\n",key);
}

//Step 2: xor with ki
xor(expanded,key);

if(DEBUG){  
printf("After xor: %s\n",expanded);
}


//Step 3-4: S-box functions
Sbox(sbResult,expanded);

if(DEBUG){  
printf("After p4 permutation %s ",sbResult);
}

free(expanded);
free(key);

}

private void Sbox(char *result,char *input){
  int s0[4][4]={{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,3,2}};
  int s1[4][4]={{0,1,2,3},{2,0,1,3},{3,0,1,0},{2,1,0,3}}; 
  int p4[4]={1,3,2,0};
char *lp=(char*) calloc(4+1,sizeof(char));
 strncpy(lp, input, 4);
 //printf("lp:%s\n",lp);
 char *rp=(char*) calloc(4+1,sizeof(char));
  strncpy(rp, input+4, 4);
  //printf("rp:%s\n",rp);

  char *col=(char*) calloc(2+1,sizeof(char));
  char *row=(char*) calloc(2+1,sizeof(char));
 

//sbox 0
  col[0]=lp[1];
  col[1]=lp[2];
  row[0]=lp[0];
  row[1]=lp[3];
  
  //printf("\nlcols:%s row: %s ",col,row);
 // int rows,cols;
  
  //get s0 value
 int lres= s0[bin2dec(row)][bin2dec(col)];
  //sbox 1
  col[0]=rp[1];
  col[1]=rp[2];
  row[0]=rp[0];
  row[1]=rp[3];

 int rres= s1[bin2dec(row)][bin2dec(col)];
 
  // printf("\nlres\n %d ",lres);
  // printf("\nrres %d ",rres);
  dec2Bin(lres,row); //put result of left in row
 
  dec2Bin(rres,col);//put result of right in col
  
 result[0]=row[0];
 result[1]=row[1];
 result[2]=col[0];
 result[3]=col[1];

strcpy(rp,result);

//permutation 4
 for(int i=0;i<4;i++){
   result[i]=rp[p4[i]];
 }
  free(lp);
  free(rp);
  free(col);
  free(row);

}

private int bin2dec(char *bin){
	if(strlen(bin)!=2)
	  return -1;

 if (strcmp(bin,"00")==0){
	  return 0;
 }
	else if (strcmp(bin,"01")==0){
	return 1;
  }
	else if (strcmp(bin,"10")==0){
	  return 2;
  }
	else if (strcmp(bin,"11")==0){
	return 3;
  }
  else {
	  return -1;
  }
}

private void dec2Bin(int dec,char *bin){
  if(dec == 0){
	strcpy(bin,"00");
  }
  else	if(dec == 1){
	strcpy(bin,"01");
  }
	else if(dec == 2){
	strcpy(bin,"10");
  }
	else if(dec == 3){
	strcpy(bin,"11");
  }
}

private void xor(char * exp, char *key){
  for(int i=0;i<strlen(exp);i++){
    if((myAtoi(exp[i])!=myAtoi(key[i]))){
    exp[i]='1';
  }else{
     exp[i]='0';
  }

  }
}

private int myAtoi(char x){
  char *temp=(char*) calloc(1+1,sizeof(char));
 temp[0]=x;
int res=atoi(temp);
free(temp);
 //printf("char: %s\n",temp);
  return res;

}

/// function that handle key scheduling 
// needs rotate, rotate by one 
private void keyScheduling(char * K8,char * key,int round){

int p10 [10]={2,4,1,6,3,9,0,8,7,5};
int p8[8]={5,2,6,3,7,4,9,8};

if(round==1){
char *temp=(char*) calloc(10+1,sizeof(char));
strcpy(temp,key);

 for(int i=0;i<10;i++){
   key[i]=temp[p10[i]];
 }
free(temp);
}
// printf("Pkey:%s\n",key);
 char *Lk=(char*) calloc(5+1,sizeof(char));
 strncpy(Lk, key, 5);
// printf("LK:%s\n",Lk);
 char *Rk=(char*) calloc(5+1,sizeof(char));
  strncpy(Rk, key+5, 5);
  // printf("RK:%s\n",Rk);
   rotate(Lk,round);
   rotate(Rk,round);
   strcpy(key,Lk);
   strcpy(key+5,Rk);
  //printf("Pkey:%s\n",key);


 for(int i=0;i<8;i++){
   K8[i]=key[p8[i]];
 //  printf("%c",key[p8[i]]);
 }


free(Lk);
free(Rk);
// printf("key for round %d:%s\n",round,K8);
}

private void rotate(char *key,int rotations){

  for(int i=0;i<rotations;i++){
    rotateByOne(key);
  }

 // printf("rotated:%s\n",key);
}
private void rotateByOne(char *key){
   int temp = key[0], i; 
    for (i = 0; i < strlen(key)-1; i++) {
        key[i] = key[i + 1]; 
    }
    key[i] = temp; 


   // printf("rotated:%s\n",key);
}

private void expand (char * ex,char * text){
int indexs[8]={7,4,5,6,5,6,7,4};
//printf("new cipher %s\n",text);
  for(int i=0;i<8;i++){
    ex[i]=text[indexs[i]];
  //  printf("c1 %s\n",ex);
  }
//printf("expanded %s\n",ex);

}

//initial permutation
private void initialPermutation(element *el){
   // printf("got here");
    int IP[8]= {1,5,2,0,3,7,4,6};
  //  char temp[el->Tsize];
    char *temp=(char *)malloc(sizeof(char)* el->Tsize+1);
    strcpy(temp,el->plaintext);

    for(int i=0;i<el->Tsize;i++){
        el->plaintext[i]=temp[IP[i]];
     //   printf("%c",temp[IP[i]]);
    }
//    printf("new cipher %ld\n",strlen(temp));
free(temp);
}
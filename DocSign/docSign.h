#ifndef docSign_H
#define docSign_H

#include <stdio.h>
#include <stdlib.h>
#include <openssl/evp.h>
#include <openssl/rsa.h>
#include <openssl/err.h>
#include <openssl/pem.h>
#include <openssl/ssl.h>
#include <openssl/sha.h>
#include <string.h>
#include <unistd.h>

//Function definition
#define public 
#define private static
#define KEY_LENGTH 2048
#define PUPLIC_EXPONENT 3

//Function declarations
void sign(char *docname);
void generateRSAkeys();
void readDoc(char *name,  char **doc);
void getDigest( char *doc,int size,unsigned char *digest);
void writeBack(char *docname,unsigned char *signature, char *doc,int docsize,int sigsize);
void validate(char *docname);
//void readSignature( char *doc,int size,unsigned char *signature,  char *plainDoc,int sigsize,int pDocSize);
void readSignedDoc(char *name, char **plaintext,unsigned char *signature,int *docsize,int sigsize);
#endif
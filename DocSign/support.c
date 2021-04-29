#include "docSign.h"

/*
key generation function, called only if keys do not exist 
in directory.
*/
void generateRSAkeys(){
    int	returnValue = 0;
	RSA	*keypair = NULL;
	BIGNUM	*bignumber = NULL;
	
	bignumber = BN_new();
	returnValue = BN_set_word(bignumber,RSA_F4);
	if(returnValue != 1){ //check return value valid else free all and exit
	RSA_free(keypair);
	BN_free(bignumber);
	printf("Failure in key generation\n");
	exit(-1);
	}

	//rsa key generation
	keypair = RSA_new();
	returnValue = RSA_generate_key_ex(keypair, KEY_LENGTH, bignumber, NULL);
	if(returnValue != 1){ //check return value valid else free all and exit
	RSA_free(keypair);
	BN_free(bignumber);
	printf("Failure in key generation\n");
	exit(-1);
	}

	//save two keys public and private in different files
	BIO	*publicbio = BIO_new_file("public.pem", "w+");
	BIO *privatebio = BIO_new_file("private.pem", "w+");

	returnValue = PEM_write_bio_RSAPublicKey(publicbio, keypair);
	if(returnValue != 1){ //check return value valid else free all and exit
	BIO_free_all(publicbio);
	BIO_free_all(privatebio);
	RSA_free(keypair);
	BN_free(bignumber);
	printf("Failure in key generation\n");
	exit(-1);
	}


	returnValue = PEM_write_bio_RSAPrivateKey(privatebio, keypair, NULL, NULL, 0, NULL, NULL);

	if(returnValue != 1){ //check return value valid else free all and exit
	BIO_free_all(publicbio);
	BIO_free_all(privatebio);
	RSA_free(keypair);
	BN_free(bignumber);
	printf("Failure in key generation\n");
	exit(-1);
	}
	

//free all
	BIO_free_all(publicbio);
	BIO_free_all(privatebio);
	RSA_free(keypair);
	BN_free(bignumber);
}


/*
Reads regular document to be signed.
*/
void readDoc(char *name,  char **doc){
FILE *fp = fopen(name, "rb");

if(fp==NULL){
    printf("Invalid file name given\n");
    exit(-1);
}


fseek(fp, 0, SEEK_END); //seek end
long fsize = ftell(fp);
fseek(fp, 0, SEEK_SET);  //go back to starting point
//printf("readDoc function\n");
char *string = calloc(fsize+1 ,sizeof( char)); //maybe not +1
if(string==NULL){ 
    printf("Failed to allocate memory");
    exit(-1);
}

//printf("readDoc function\n");
fread(string, 1, fsize, fp);
fclose(fp);
//printf("strlen: %d\n",(int)strlen(string));
//printf("fsize: %d\n",(int)fsize);
//*(size)=fsize;
// printf("size: %d\n",*(size));

*(doc)=string;
//printf("size: %d\n",*(size));
}


/*computes the digest of the given text and size*/
void getDigest( char *doc,int size,unsigned char *digest){
    SHA256_CTX c;

     if ((SHA256_Init(&c))==0){
         printf("Failed to initialize SHA struct.Exiting...\n");
         exit(-1);
     }
    

    if ((SHA256_Update(&c,doc,size))==0){
        printf("Failed to update SHA.Exiting...\n");
        exit(-1);
    }

     SHA256_Final(digest,&c);
}


/*
writes regular document along with its signature to a new signed 
file
*/
void writeBack(char *docname,unsigned char *signature, char *doc,int docsize,int sigsize){
int namelen=strlen(docname);
char *newname=( char *)calloc(namelen+10,sizeof( char));
if(newname==NULL){
    printf("Failed to allocate memory");
    exit(-1);
}

for(int i=0,j=0;i<namelen;i++){ //search for invlid starting character / and remove
	if(docname[i]=='/')
		continue;
	newname[j]=docname[i];
	j++;
}

 //strncpy(newname,docname,namelen);
 strncat(newname,".signed",8); //add signed ending to file
  FILE *fp=fopen(newname,"w");

  if(fp==NULL){
    printf("Failed to create destination file.\n");
    exit(-1);
  }

 // printf("Docsize:%d, sigsize:%d",docsize,sigsize);

	//write containts
    fwrite(doc,sizeof( char),docsize,fp);
    fwrite(signature,sizeof( char),sigsize,fp);

//free section
  fclose(fp);
  printf("Document: %s was just signed. Signed document: %s.\n",docname,newname);
  free(newname);
 
}



/*
Reads signed file given for verification and the signature contained in it
*/
void readSignedDoc(char *name, char **plaintext,unsigned char *signature,int *docsize,int sigsize){
FILE *fp = fopen(name, "rb");
fseek(fp, 0, SEEK_END);
long fsize = ftell(fp);
rewind(fp);  //go back to starting point

*(docsize)=((int)fsize)-sigsize;
char *string = calloc(*(docsize) ,sizeof( char)); //check for this if needed the +1

if(string==NULL){
    printf("Failed to allocate memory");
    exit(-1);
}

fread(string, 1, *(docsize), fp);
fread(signature,1,sigsize,fp);
*(plaintext)=string;
}
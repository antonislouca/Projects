#include "docSign.h"

/*
preforms the signe operation by reading the document, computing its
digest with sha256 and writing a new document with extension ".signed"
which includes the plaintext and the signed digest.
Keys are generated only if the directory does not contain already generated 
keys.
*/
void sign(char *docname){
      unsigned char *digest=(unsigned char *)calloc
                                (SHA256_DIGEST_LENGTH,sizeof(unsigned char));

         //mem aloc failure  
          if(digest==NULL){
            printf("Failed to allocate memory");
            exit(-1);
            }



   FILE *fp;
    //check if keys exist or not
        if(!access("private.pem",F_OK)){ //FILE EXISTS keys already generated
           fp =fopen("private.pem","rb");
        }else{
           // printf("Get to generate keys\n");
            generateRSAkeys();   //key generation 
            //check if read binary
            fp=fopen("private.pem","rb");
        }

   
     char *doc=NULL;
        readDoc(docname,&doc); //calls read doc to get the document and the 
                                    //size of it
        int size=strlen(doc);
        //printf("docsize %d\nReading key.",size);
        RSA *privatekey = RSA_new();
        PEM_read_RSAPrivateKey(fp,&privatekey,NULL,NULL); //read private key

        fclose(fp);
     unsigned char *signature=(unsigned char *)
                        calloc(RSA_size(privatekey),sizeof(unsigned char));
        
         //mem aloc failure
            if(signature==NULL){
                printf("Failed to allocate memory");
                exit(-1);
             }
            


     unsigned int siglen=0; //siglength

     getDigest(doc,size,digest); //computes digest of read doc

     int check =RSA_sign(NID_sha256,digest,SHA256_DIGEST_LENGTH,signature,&siglen,privatekey); //perform sign operation

        //testing 
        //fwrite(digest,32,1,stdout);
        // fwrite(doc,1,size,stdout);
        // fwrite(signature,1,siglen,stdout);
        // printf("siglength: %d\n",siglen);

    if(check==1){ //success
            //DOCNAME,SIGNATURE,PLAINTEXTDOC,SIZEofDOC,signature length
        writeBack(docname,signature,doc,size,(int)siglen); 

    }else{
            printf("Something went wrong.\nDocument could not be signed.\n");
            exit(-1);
    }   

    //free section
    free(privatekey);
    free(signature);
    free(digest);
    free(doc);
}



/*
Performs signature verification for the encrypted document.
*/
void validate(char *docname){

    FILE *fp;
    //check if keys exist or not
        if(!access("public.pem",F_OK)){ //FILE EXISTS keys already generated
           fp =fopen("public.pem","rb");
        }else{ //keys not generated cannot validate an already encrypted message
             printf("There are no keys in current directory."
           "\nRun sign option to generate a valid signed document, and keypair.\n");
            exit(-1);
        }

      //read public key
       RSA *publickey = RSA_new();
       PEM_read_RSAPublicKey(fp,&publickey,NULL,NULL); //read public key
       fclose(fp); //close file pointer

     //digest
     unsigned char *digest=(unsigned char *)calloc
                                (SHA256_DIGEST_LENGTH,sizeof(unsigned char));

     //mem aloc failure
    if(digest==NULL){
    printf("Failed to allocate memory");
    exit(-1);
    }

     int siglen=256; 
     int plainDocSize=0;

     unsigned char *signature=(unsigned char *)
                        calloc(RSA_size(publickey),sizeof(unsigned char));     
        //mem aloc failure
        if(signature==NULL){
             printf("Failed to allocate memory");
            exit(-1);
          }


       char *plainDoc=NULL;
       readSignedDoc(docname,&plainDoc,signature,&plainDocSize,siglen); //read document
        getDigest(plainDoc,plainDocSize,digest);  //compute digest
        //debugging 
       // fwrite(plainDoc,1,plainDocSize,stdout);
       // fwrite(signature,1,siglen,stdout);
       // printf("DocSize: %d , siglength: %d\n",plainDocSize,siglen);
      //  printf("rsapublickey size: %d",RSA_size(publickey));

    int check=RSA_verify(NID_sha256,digest,SHA256_DIGEST_LENGTH,signature,RSA_size(publickey),publickey);

    if(check==1){
        printf("Digital signature is valid\n"); //valid digital signature
    }else{
        printf("Digital signature is invalid\n"); //invalid digital signature
    }

    //free section
    free(publickey);
    free(signature);
    free(digest);
    free(plainDoc);
}
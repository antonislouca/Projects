#include "docSign.h"


int main(int argc, char *argv[]){
  //  printf("%d",argc);
    if(argc!=3){
        printf("Usage: docsign -sv\n"
        "-s sign document\n"
        "-v validate document\n");
        exit(-1);
    }


    if(strcmp(argv[1],"-s")==0){
   
    //printf("DOCUMENT:%s, SIZE:%d\n",doc,size);
    sign(argv[2]);
    }
    else if(strcmp(argv[1],"-v")==0){
    // readDoc(argv[2],&size,&doc);
    //printf("DOCUMENT:%s, SIZE:%d\n",doc,size);
    validate(argv[2]);
    }else{
       printf("Usage: docsign -sv\n"
        "-s sign document\n"
        "-v validate document\n"); 
    }
    // printf("%s",argv[1]);


    // RSA_print_fp(stdout,privatekey,0);
    // RSA_print_fp(stdout,publickey,0);

 
   
 return 0;
}
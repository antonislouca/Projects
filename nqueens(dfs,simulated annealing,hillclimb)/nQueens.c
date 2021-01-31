/*
*
* @file nQueens.c
* @brief the file contains the main method of the nqueens program.
*
* Reads the input arguments from the user and if the arguments are wrong
* prints a message to the user with the prototypemof arguments.
* Also if the arguments are correct invokes the right function to solve the
* nqueens problem.
*
* @Author      : Antonis Louca
* @bugs No known ones
 */

#include <stdio.h>
#include <stdlib.h>
#include "algorithms.h"
#include <string.h>
#include <getopt.h>
#include <ctype.h>
#include "utils.h"
#include <stdbool.h>
/**
 * @brief the main function of the program. Calls 3 algorithms that can be
 * performed by the program.
 *
 * Reads the input arguments from the user and calls the right algorithm,if
 * arguments are wrong prints the input prototype and exits.
 * The simulation annealing and hill climb algorithm are called five times
 * each.
 * @param argc the number of arguments given by the user
 * @param argv the array that contains the arguments given
 * @return should not return anything
 *
 * */
int main(int argc,char **argv) {
	int specs=0,seedval=-1,secs=0,option=0;
	bool seedexist=false;

	if(argc>7||argc<5){
	correctUsage();
	exit(-1);
	}


	for(int i=1;i<argc;i++){
	//	printf("%s",argv[i]);
	 if(strcmp("dfs",argv[i])==0){
		 option=1;
		 i++;
		 specs=checkargument(argv[i]);
		 if(specs==-1){
			 correctUsage();exit(-1);
		 }
	 }else if(strcmp("ann",argv[i])==0){
		 option=3;
		 i++;
		 specs=checkargument(argv[i]);
		 if(specs==-1){
		 correctUsage();exit(-1);
		 }

	 }else if(strcmp("hill",argv[i])==0){
		 option=2;
		 i++;
		 specs=checkargument(argv[i]);
		 if(specs==-1){
			 correctUsage();exit(-1);
		 }

	 }else if(strcmp("maxtime",argv[i])==0){
		 i++;
		 secs=checkargument(argv[i]);
		 if(secs==-1){
			 correctUsage();exit(-1);
		}

	 }else if(strcmp("seed",argv[i])==0){
		 i++;
		 seedval=checkargument(argv[i]);
		 if(seedval==-1){
			 correctUsage();exit(-1);
		 }
		 seedexist=true;

	 }
	 else{
		correctUsage();
		 exit(-1);
	 }
	}

	printf("Searching for solution:\n");
  switch(option){
  case 1: dfsAlgo1(specs,secs);break; //call algo1
  case 2:

	  	 (seedexist==false)?	srand(time(0))	:srand(seedval);
	  	for(int i=0;i<5;i++){
	  	   hillclimb(specs, secs); //call algo 2
	  	  if(i!=4)printf("\nReinitializing...\n\n");

	  	}
	  	break;

  case 3:
	  	  (seedexist==false) ? srand(time(0)) : srand(seedval);

  	  	  for(int i=0;i<5;i++){
	  	  simannealing(specs,secs); //call algo3

	  	  if(i!=4)printf("\nReinitializing...\n\n");
  	  	  }
  	  	  break;
  default:break;
  }


printf("\nThank you for using our program!!!\n");
	return EXIT_SUCCESS;
}


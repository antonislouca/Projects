/*
 * @file utils.c
 * @brief Contains usefull functions of different utilities.
 *
 *  @Author: Antonis Louca
 *
 */
#include "utils.h"
/**
 * @brief Prints the correct usage of the nqueens program when argument inputs
 * are wrong.
 * */

void correctUsage(){
	printf("Wrong arguments input.\n");
	printf("Usage: <algo> N <maxtime> L <seed> S.\n");
	printf("Where algo can be either: dfs or hill or ann.\n");
	printf("maxtime the time the user can wait.\n");
	printf("seed can be optional but not a negative number. Seed is not needed in dfs algorithm\n");
}

/*
*@brief Checks if the argument is valid and converts the string to numeric
*@param *ptr character string to be checked
*/
int checkargument(char *ptr) {
	    if (ptr == NULL) return -1;
	    int num = 0;
	    while(*ptr!='\0'){
	        if (!isdigit(*ptr)){
	        	return -1;
	        }
	        else {num  = num * 10 + (*ptr - '0');}
	        ptr++;
	    	}
	    return num;

}

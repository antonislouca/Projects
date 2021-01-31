/*
 * @file algorythms.h
 * @brief A header file that contains function prototypes for the algorithms source
 * file.
 * Created on: Oct 20, 2019
 * @Author: Antonis Louca
 */

#ifndef ALGORITHMS_H_
#define ALGORITHMS_H_


//Libraries included
#include "stack.h"
#include "board.h"
#include<stdbool.h>
#include <time.h>
#include<math.h>


/*Function that solves the nQueens problem using the dfs algorythm*/
bool dfsAlgo1(int n,int times);
/*Function that solves the nQueens problem using the hill climbing algorythm*/
bool hillclimb(int n,int times);
/*Function that solves the nQueens problem using the simulation annealling
 *  algorythm*/
bool simannealing(int n, int times);

#endif /* ALGORITHMS_H_ */

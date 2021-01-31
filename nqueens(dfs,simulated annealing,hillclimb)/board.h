/*
 * @file board.h
 * @brief Header file for the board source file.Contains the function prototypes
 * of the board  file, along with the struct myboard.
 *  Created on: Oct 22, 2019
 *  Author: Antonis Louca
 */

#ifndef BOARD_H_
#define BOARD_H_
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>

/*Struct myboard*/
typedef struct Myboard{
	int **chess;
	int queencount;
	int size;
}myboard;

/*initializes board*/
void initBoard(myboard **b,int n);
/*prints board*/
void printBoard(myboard *board);
/*checks for safety of placing a queen*/
bool checkSafety(myboard *board, int row, int col);
/*places a queen in the particular coordinate*/
bool unplace(myboard **board,int row,int col);
/*removes a queen fromm the particular coordinate*/
bool place(myboard **board,int row ,int col);
/*copies the values from on board to another*/
void copyboard(myboard **temp,myboard *val);
/*calculates the number of queens in the board that arre under attack*/
int queensUnderAttack(myboard *board);
/*unplaces a queen from a certain row*/
int unplacerow(myboard **board,int row);
/*checks and counts surrounding queens*/
int issafe(myboard *board,int row,int col);
/*frees the board struct memory held */
void freeBoard(myboard *b);
#endif /* BOARD_H_ */

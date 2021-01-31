/*
 * @file dfsalgorithm.c
 * @brief Uses the dfs algorithm to solve the n queens problem.
 *
 *Contains the dfs function algorithm, implemented with the use of stack and
 *as a chess board myboard struct
 *
 * Created on: Nov 2, 2019
 * @Author: Antonis Louca
 */
#include "algorithms.h"

/**
 * @brief the dfs function implemented with stack and myboard struct
 *
 * The starting state is pushed in stack and for each board that is a valid
 * state of the queens in the chess board, its pushed in the stack. When a
 * solution is found its printed and the function returns false.
 * When the running time of the function is bigger than the required waiting
 * time the process is terminated and returns false.
 *
 * @param n the size of the board
 * @param times the time in seconds the user can wait to get a result.
 * @return true if a solution is found else false
 *
 */
bool dfsAlgo1(int n,int times){
Stack *s;
initStack(&s);
myboard *board;
initBoard(&board, n);
push(board, s);		//pushes in stack
freeBoard(board);
float seconds=0;
clock_t t1=clock();
clock_t t2;
while(IsEmpty(s)!=true){

	t2=clock();
	seconds=((float) t2-t1)/CLOCKS_PER_SEC;
	if(times<=seconds){
	printf("Maxtime of the  algorithm was reached.\nNo Solution Found.\n"
											"Terminating...\n");
	freeBoard(board);
	freeStack(s);
	return false;

	}

	pop(s, &board);
	//printBoard(board);//prints board DEBUG
	for(int i=0;i<n;i++){
		if(checkSafety(board, i, board->queencount)==true){
			place(&board, i, board->queencount);
			//printBoard(board); DEBUG
			if(board->queencount==n){
				printf("Solution Found:");
				printBoard(board);
				t2=clock();
				seconds=((float) t2-t1)/CLOCKS_PER_SEC;
				printf("Needed %f seconds ",seconds);
				freeBoard(board);
				freeStack(s);
				return true;
			}
			else{

				push(board, s);
				unplace(&board, i, board->queencount-1);
			}

		}
		t2=clock();
		seconds=((float) t2-t1)/CLOCKS_PER_SEC;
		if(times<=seconds){
		printf("Maxtime of the  algorithm was reached.\nNo Solution Found.\n"
										"Terminating...\n");
		freeBoard(board);
		freeStack(s);
		return false;

		}

		}
	freeBoard(board);


	}
printf("\nNo solution found.");
//freeBoard(board);
freeStack(s);
return false;
}

#ifdef DEBUG_DFS

int main(void){
printf("Checking dfs algorithm...\n");

dfsAlgo1(4,3);
dfsAlgo1(8,3);
dfsAlgo1(16,3);
return EXIT_SUCCESS;
}
#endif

/*
 * @file hillclimb.c
 * @brief The file that contains the hill climb function and solves
 * the nqueens problem.
 *
 *  Created on: Nov 2, 2019
 *  @Author: Antonis Louca
 */

#include "algorithms.h"

/**
 *@brief The hill climb function that solves the nqueens problem
 *
 * Initializes starting board and then places every queen in a separate row in
 * the board. Counts the couples of queens that threat each other and every time
 * that we find less threats in a state we place the queen in that particular
 * coordinate. For each move increase the step count and check if the time is
 * up. When we get to a point that we have  no other options left we restart the
 * algorithm and increase the restart count. The coordinates for the queens are
 * randomly generated. When a solution is found its shown along with time needed
 * and number of restarts.
 *
 * @param n the size of the board
 * @prama times the time the user can wait for a solution.
 * @return returns true if a solution is found else false
 *
 */
bool hillclimb(int n,int times){
myboard *board;
int restarts=0,steps=0;
float seconds;
int k=0;

bool restart=true,checkagain=false;
int t0=0,underA=0,x=0,y=0;
clock_t t1=clock();
clock_t t2;
while(restart==true){

if(checkagain==false){

	initBoard(&board, n);
for(int i=0;i<n;i++){
	place(&board,i,rand()% (n) );
}

	t0=queensUnderAttack(board);
	//printBoard(board);
	underA=t0,x=0,y=0;
}


for(int i=0;i<n;i++){

	int prevC=unplacerow(&board, i);

	for(int j=0;j<n ;j++){

		if(j!=prevC){
		place(&board, i, j);

		k=queensUnderAttack(board);
		unplacerow(&board, i);
		if(k<underA){
			underA=k; x=i;y=j;
		}

		}
	}
	place(&board, i, prevC);

}

if(underA<t0){
	t0=underA;
	unplacerow(&board, x);
	place(&board, x, y);
	steps++;
	if(underA==0){
		printf("Solution Found:");
		printBoard(board);
		printf("Restarted %d times\n",restarts);
		printf("Number of steps used: %d\n",steps);
		t2=clock();
		seconds=((float) t2-t1)/CLOCKS_PER_SEC;
		printf("Needed %f seconds ",seconds);
		freeBoard(board);
		return true;
	}else{
		//check if time is up
		t2=clock();
		if(times<=((float) t2-t1)/CLOCKS_PER_SEC){
		printf("Maxtime of the  algorithm was reached.\nNo Solution Found.\n"
						"Terminating...\n");
		freeBoard(board);
		return false;
			}
		checkagain=true;
		continue;
	}

}else{
	restarts++;
	checkagain=false;
	freeBoard(board);
	//check if time if up
	t2=clock();
	if(times<=((float) t2-t1)/CLOCKS_PER_SEC){
	printf("Maxtime of the  algorithm was reached.\nNo Solution Found.\n"
			"Terminating...\n");
	freeBoard(board);
	return false;
		}

	continue;
}

}
freeBoard(board);
return false;
}


#ifdef DEBUG_HILL
int main(void){
printf("Checking hill climbing algorithm...\n");
for(int i=0;i<8;i++){
 hillclimb(8, 30); //call algo
 if(i!=7)printf("\nReinitializing...\n\n");
}
return EXIT_SUCCESS;
}


#endif


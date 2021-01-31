/*
 * @file simannealing.c
 * @brief The file that contains the simulation annealing function and solves
 * the nqueens problem.
 *
 * Created on: Nov 2, 2019
 * @Author: Antonis Louca
 */

#include "algorithms.h"
/**
 *@brief The simulation annealing function that solves the nqueens problem
 *
 * Initializes the board in zeros and then places the queens randomly in the
 * board and uses a temporary board to check if you can
 * move one queen to a coordinate based on the number of queens that threat
 * each other. The coordinates are generated based on random numbers. If the
 * threatening queens are less that the base threatening counter then the queen
 * is moved to than coordinate,else its checked based on another the difference
 * and then if it's valid the queen is again moved. Every move is one step.
 * When we find a solution the solution is printed and true is returned else
 * if the available time is up the process is terminated and at that point no
 * solution is found.
 *
 *@param n the size of the chess board
 *@param times the time the user wants to wait for an answer
 *@return returns true if a solution is found else false
 */
bool simannealing(int n, int times){
	myboard *board;
	myboard *temp;
	int steps=0;
	int underA=0,t0=0,x=0,y=0,Dt=0;
	const double a=0.001;
	float seconds;
	clock_t t1=clock();
	clock_t t2;


	initBoard(&board, n);
	initBoard(&temp, n);
for(int i=0;i<n;i++){
	place(&board,i,rand()% (n) );
}
while(true){
t0=queensUnderAttack(board);
x= rand()% (n);
y=rand()% (n);


copyboard(&temp,board);
unplacerow(&temp, x);
place(&temp, x, y);
underA=queensUnderAttack(temp);
if(underA<=t0){
	unplacerow(&board, x);
	place(&board, x, y);
	steps++;
	if(underA==0){
		printf("Solution Found:");
		printBoard(board);
		printf("Number of steps used: %d\n",steps);
		t2=clock();
		seconds=((float) t2-t1)/CLOCKS_PER_SEC;
		printf("Needed %f seconds ",seconds);
		freeBoard(board);
		freeBoard(temp);
		return true;
	}
}else{
	Dt=t0-underA;
	double p=a*exp(Dt);
	double r= (double)rand()/1;
	//printf("%f",r);
	if(r<=p){
		unplacerow(&board, x);
		place(&board, x, y);
		steps++;
	}
}
t2=clock();
if(times<=((float) t2-t1)/CLOCKS_PER_SEC){
printf("Maxtime of the  algorithm was reached.\nNo Solution Found.\n"
			"Terminating...\n");
		freeBoard(board);
		freeBoard(temp);
return false;
}

}

return false;
}


#ifdef DEBUG_SIM
int main(void){
printf("Checking simulation annealing algorithm...\n");

for(int i=0;i<8;i++){
simannealing(8, 30); //call algo
 if(i!=7)printf("\nReinitializing...\n\n");
}
return EXIT_SUCCESS;
}

#endif

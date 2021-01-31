/*
 * @file: board.c
 * @brief: The file contains the functions that utilize myboard struct.
 *
 * Through this file you can create a board struct initialize it and make a copy
 * of that board struct.
 * Also there are functions that print the board and check if the state of a
 * particular board is safe or not, along with placing or unplacing queens
 * from the board and counting the number of queens that are under attack in
 * a particular state.
 *
 *Created on: Oct 22, 2019
 *@Author: Antonis Louca
 */

#include "board.h"

/**
 * @brief a function that prints the chess board
 *
 *@param *board the given board that is printed
 *@return void
 **/
void printBoard(myboard *board){
//	printf("\n  ");
//	for(int i=0;i<board->size;i++){
//		printf("+----");
//	}
//	printf("+");
	for(int i=0;i<board->size;i++){
		printf("\n ");
			for(int i=0;i<board->size;i++){
				printf("%c%c%c%c",'+','-','-','-');
			}
			printf("%c\n",'+');
			for(int j=0;j<board->size;j++){
				if(board->chess[i][j]==1){
				printf("%2c%2c",'|','Q');
				}else{
					printf( "%2c%2c",'|',' ');
				}
			}
			printf("%2c",'|');
	}
	printf("\n ");
	for(int i=0;i<board->size;i++){
				printf("%c%c%c%c",'+','-','-','-');
			}
	printf("+\n");
}



/**
 *@brief initializes the given board
 * Allocates space for a myboard struct given the size from the user.
 * And initializes the board, the size and queencount in zeros.
 *
 * @param **b the board to be initialized
 * @param n the size of the board
 *
 **/
void initBoard(myboard **b,int n){
	*b= (myboard *)malloc(sizeof(myboard));
	if(*b==NULL){
		printf("Cannot alocate memory");
		exit(-1);
	}
	(*b)->chess = (int **)malloc(n*sizeof(int *));
		for(int i=0;i<n;i++){
			(*b)->chess[i]=(int *)malloc(n*sizeof(int));
			for(int j=0;j<n;j++){
				(*b)->chess[i][j]=0;
			}
		}
		(*b)->queencount=0;
		(*b)->size=n;
}


/**
 *@brief checks the safety of a certain coordinate in a state board.
 *
 *from the coordinate checks current row from zero to collumn,
 *diagonal left up and left low. Checks only the half board until the coordinate.
 *
 *@param *board the board to be checked
 *@param row the x coordinte
 *@param col the y coordinate
 *@return true if the coordinate is safe for placing else false
 */
bool checkSafety(myboard *board, int row, int col) {

	//current row on the left side
    for (int i = 0; i < col; i++)
        if (board->chess[row][i]==1)
            return false;

    //diagonal up left
    for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
        if (board->chess[i][j]==1)
            return false;

    //diagonal low left
    for (int i = row,j = col; j >= 0 && i < board->size; i++, j--)
        if (board->chess[i][j]==1)
            return false;

    return true;
}

/**
 * @brief places a queen on a certain coordinate and increases the queencount
 *
 * @param **board the chess board on which the queen is placed on
 * @param row the x coordinate
 * @param col the y coordinate
 * @return returns true when added
 */
bool place(myboard **board,int row ,int col){
	(*board)->chess[row][col]=1;
	(*board)->queencount++;
	return true;
}

/**
 * @brief removes a queen from a certain coordinate and decreases the queencount
 *
 * @param **board the chess board on which the queen is placed on
 * @param row the x coordinate
 * @param col the y coordinate
 * @return returns true when added
 */
bool unplace(myboard **board,int row,int col){
	(*board)->chess[row][col]=0;
	(*board)->queencount--;
	return true;
}

/**
 *@brief checks the safety of the board from a certain coordinate
 *The function counts all the occurences of queens in the chess board
 * on a certain row, colloumn and all diagonals. Queens are counted twice
 * The sum is used from queens under attack and the function is an asssisting
 * one to that.
 *
 * @param *board the board to be checked
 * @param row the x coordinate
 * @param col the y coordinate
 * @return the sum of queens
 */
int issafe(myboard *board,int row,int col){
	int sum=0;

	  for(int i=0;i<board->size;i++)
		  if (board->chess[i][col]==1 &&i!=row )
			  sum++;


	//printf("coordinates: %d %d \n",row,col);
	  for (int i = 0; i < board->size; i++){
	        if (board->chess[row][i]==1 && i!=col)
	           sum++;
	      // printf("1: ( %d %d )\n",row,i);
	  }
	    //diagonal up left
	    for (int i = row, j = col; i >= 0 && j >= 0; i--, j--){
	        if (board->chess[i][j]==1 && i!=row && j!=col)
	            sum++;
	   //     printf("2: (%d %d) \n",i,j);
	    }
	  //  printf("\nsum= %d\n ",sum);
	    //diagonal low left
	    for(int i = row,j = col; j >= 0 && i < board->size; i++, j--){
	        if (board->chess[i][j]==1 &&i!=row && j!=col){
	        	sum++;
	        }
	      //  printf("3: (%d %d) \n",i,j);
	    }


	    	//checks diagonal up right
	    	 for (int i = row, j = col; i >=0 &&
	    	 	 	 	 	 	 j < board->size; i--, j++){
	    			//printf("4: (%d %d) \n",i,j);
	    	        if (board->chess[i][j]==1 && i!=row && j!=col)
	    	        	  sum++;
	    	}
	    	 //checks diagonal low right
	    	for (int i = row,j = col; j < board->size && i < board->size; i++, j++){
	    		//printf("5: (%d %d) \n",i,j);
	    	       if (board->chess[i][j]==1 && i!=row && j!=col)
	    	    	   sum++;
	    	}
return sum;

}

/**
 * @brief calculates using is safe function the number of queen couples that
 * are under attack by each other.
 *
 * @param the board to be checked
 * @return the number of queens that threat each other
 */
int queensUnderAttack(myboard *board){
	int sum=0;
	for(int i=0;i<board->size;i++){
		for(int j=0;j<board->size;j++){
			if(board->chess[i][j]==1){
				sum=sum+issafe(board, i, j);
		}
	}
}
	return (sum/2);
}


/**
 * @brief  removes a queen from a certain row
 *
 * Takes the board and the row and removes the queen from that certain row
 * returns the column of the queen.
 * @param **board the board to modify
 * @param row the row of the queen to be removed
 * @return the column of that queen
 *
 */
int unplacerow(myboard **board,int row){
	int col=0;
	for(int i=0;i<(*board)->size;i++){
		if((*board)->chess[row][i]==1){
			(*board)->chess[row][i]=0;
			(*board)->queencount--;
			col=i;
			break;
		}
	}
	return col;
}

/**
 * @copies a myboard struct to another one
 *
 * copies val struct to temp.
 * @param **temp  the struct pointer that will be the copy of the board
 * @param *val the original board that is copied
 * @return void
 *
 */
void copyboard(myboard **temp,myboard *val){
	for(int i=0;i<val->size;i++){
				for(int j=0;j<val->size;j++){
					(*temp)->chess[i][j]=val->chess[i][j];
				}
			}
	(*temp)->queencount=val->queencount;
	(*temp)->size=val->size;
}

/*
 * @brief frees the space allocated for the board struct
 *
 * @param *b the board to be freed
 */
void freeBoard(myboard *b){
	for(int i=0;i<b->size;i++){
		free(b->chess[i]);
	}
	free(b->chess);
	free(b);
}

#ifdef DEBUG_BOARD
int main(void){
myboard *b;
initBoard(&b,4); // allocates memory and initializes in zeros
					// a 7 x 7 integer array
printBoard(b); //prints the array
//place(&b,0,0);		// puts value 1 in
printf("\n");
printBoard(b); //prints new board of given length
if(checkSafety(b, 1, 1)==false){
	printf("\nworks\n");
}
//unplace(&b, 0, 0);
printBoard(b);
if(checkSafety(b, 1, 1)==true){
	printf("\nworks\n");
}

place(&b,0,1);
place(&b,1,1);
place(&b,2,1);
place(&b,3,2);
printBoard(b);
int sum=queensUnderAttack(b);
printf("\n%d",sum);
return EXIT_SUCCESS;
}

#endif

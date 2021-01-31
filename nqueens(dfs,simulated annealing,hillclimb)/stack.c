/*
 * @file stack.c
 * @brief the implementation of stack header file
 *
 * This file contains functions that initializes a stack struct pushes and pops
 * the stack and checks if the stack is empty.
 *
 *  Created on: Oct 20, 2019
 *  @Author: Antonis Louca
 */

#include "stack.h"

/**
 *@brief checks if the stack is empty or not
 *@param *s the stack to be checked
 *@return true if is empty else false
 */
bool IsEmpty(Stack *s){
	if((s->size==0)||(s==NULL))
			return true;
return false;
}

/**
 *@brief pushes the given board to the stack
 * Given a board makes a copy of it and pushes it to stack
 *@param *val the board to be pushed in the stack
 *@param *s the stack to be pushed in
 *@return 1 or zero given a succes or failure
 */
int push(myboard *val,Stack *s){
 Node *p=NULL;
 	 if(s == NULL){
 		 return EXIT_FAILURE;
 	 }
 	 p=(Node *) malloc(sizeof(Node));
 	 if(p== NULL){
 		 printf("\nFailed to allocate memory.\n");
 		 return EXIT_FAILURE;
 	 }
 	 initBoard(&p->board,val->size );
 	 copyboard(&p->board,val);
 	 p->next=s->top;
 	 s->top=p;

 	 (s->size)++;

 	 return EXIT_SUCCESS;
}

/**
 *@brief initializes stack to starting values but first allocates memory for it
 *@param **s stack to be initialized
 *@return void
 */
void initStack(Stack **s){
	(*s)=(Stack *)malloc(sizeof(Stack));
	if((*s)==NULL){
		printf("\nStack initialization failure\n");
		return;
	}
	(*s)->top= NULL;
	(*s)->size=0;
}

/**
 *@brief pops a board from the stack and puts it on returnVal
 * Revomes the top  element and decreases the queenccount
 *@param *s the stack to be popped
 *@param the board to be returned back. Contains the board that was popped.
 *@return 1 or 0 if the procedure was successful or not.
 */
int pop(Stack *s,myboard **returnVal ){
	if(s==NULL ||s->top==NULL){
		printf("\nStack Empty\n");
		return EXIT_FAILURE;
	}
	if(returnVal==NULL){
		printf("\nNull Value Pointer\n");
		return EXIT_FAILURE;
	}
  Node *p;
  p=s->top;
  (*returnVal)=p->board;
  s->top=p->next;
  (s->size)--;
  free(p);
  return EXIT_SUCCESS;

}

/*
 * @brief frees the space allocated for stack
 *
 * @param *s stack to be freed
 */
void freeStack(Stack *s){
	Node *temp=NULL;
	while(s->top!=NULL){
		temp=s->top;
		s->top =temp->next;
		freeNode(temp);

	}
	free(s);
}



#ifdef DEBUG_STACK
int main(void){
	Stack *s;
	initStack(&s);

	if(IsEmpty(s)==true){
		printf("Success");
	}

myboard *board;
initBoard(&board, 7);
printBoard(board);
	push(board, s);
	place(&board,1, 1);
	push(board,s);
	myboard *temp;
	pop(s,&temp);
	printf("\n");
	printBoard(temp);
	pop(s,&temp);
	printf("\n");
	printBoard(temp);
	pop(s,&temp);
	freeStack(s);
	return EXIT_SUCCESS;

}


#endif

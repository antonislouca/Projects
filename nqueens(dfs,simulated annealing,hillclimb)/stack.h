 /*
 * @file stack.h
 * @brief the header file of the stack source file contains the function
 * prototypes of stack.c file.
 *  Created on: Oct 20, 2019
 * @Author: Antonis Louca
 */

#ifndef STACK_H_
#define STACK_H_

#include "Node.h"
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>

typedef struct{
	Node *top;
	int size;
}Stack;

/*initializes stack to starting values.*/
void initStack(Stack **s);
/*checks if stack is empty or not*/
bool IsEmpty(Stack *s);
/*removes top element*/
int pop(Stack *s,myboard **returnVal );
/*adds a new elemnt to the stack*/
int push(myboard *val,Stack *s);
/*frees stack*/
void freeStack(Stack *s);
#endif /* STACK_H_ */

/*
 *@file Node.h
 *@brief the header file that contains the prototype of node struct
 *  Created on: Oct 20, 2019
 *@ Author: Antonis Louca
 */

#ifndef NODE_H_
#define NODE_H_
#include "board.h"
//node struct
typedef struct myNode{
	myboard *board;
	struct myNode *next;
}Node;

void freeNode(Node *p);

#endif /* NODE_H_ */

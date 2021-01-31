/*
 * @file Node.c
 * @brief contains the free function for the node
 *  Created on: Nov 5, 2019
 * @Author: Antonis Louca
 */


#include "Node.h"
#include "board.h"

/**
 *@brief frees memory of a node
 *@param *p node to be freed.
 */
void freeNode(Node *p){
	freeBoard(p->board);
	free(p);

}

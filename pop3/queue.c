/** 
 * @author Pavlos Antoniou
 * @bug No known bugs
 */

#include "mailserver.h"

//===========================================================================//

int initQueue(Queue **queue)
{
  *queue = (Queue *)malloc(sizeof(Queue));
  if ((*queue) == NULL)
    return EXIT_FAILURE;
  // Just set both pointers to NULL!
  (*queue)->head = (*queue)->tail = NULL;
  return EXIT_SUCCESS;
}

int qEnqueue(Queue *qPtr, int val)
{

  struct node *temp;
  printf("ENQUE VALUE: %d\n", val);
  // Allocate space for a new node to add into the queue.
  temp = (struct node *)malloc(sizeof(struct node));

  // This case checks to make sure our space got allocated.
  if (temp != NULL)
  {
    // Set up our node to enqueue into the tail of the queue.
    temp->data = val;
    temp->next = NULL;

    // If the queue is NOT empty, we must set the old "last" node to point
    // to this newly created node.
    if (qPtr->tail != NULL)
      qPtr->tail->next = temp;

    // Now, we must reset the tail of the queue to our newly created node.
    qPtr->tail = temp;

    // If the queue was previously empty we must ALSO set the head of the
    // queue.
    if (qPtr->head == NULL)
      qPtr->head = temp;

    // Signifies a successful operation.
    return 1;
  }
  // No change to the queue was made because we couldn't find space for our
  // new enqueue.
  else
    return 0;
}

int qDequeue(Queue *qPtr)
{

  struct node *tmp;
  int retval;

  // Check the empty case.
  if (qPtr->head == NULL)
    return EMPTY;

  // Store the head value to return.
  retval = qPtr->head->data;

  // Set up a temporary pointer to use to free the memory for this node.
  tmp = qPtr->head;

  // Make head point to the next node in the queue.
  qPtr->head = qPtr->head->next;

  // If deleting this node makes the queue empty, we have to change the tail
  // pointer also!
  if (qPtr->head == NULL)
    qPtr->tail = NULL;

  // Free our memory.
  free(tmp);
  printf("RETURN VALUE: %d\n", retval);
  // Return the value that just got dequeued.
  return retval;
}

int qHead(Queue *qPtr)
{
  if (qPtr->head != NULL)
    return qPtr->head->data;
  else
    return EMPTY;
}

int qEmpty(Queue *qPtr)
{
  return qPtr->head == NULL;
}

#ifdef DEBUGQUEUE
int main(int argc, char **argv)
{
  int i;

  if (argc != 3)
  {
    fprintf(stderr,
            " usage:\n"
            " ./josephus n m \n"
            " n : the first n integers\n"
            " m : the step for eliminating an integer\n"
            "\n");
    exit(1);
  }
  int n = atoi(argv[1]);
  int m = atoi(argv[2]);

  // initialize the queue
  Queue *josephusQueue; // = (Queue *)malloc(sizeof(Queue));;
  initQueue(&josephusQueue);

  for (i = 1; i <= n; i++)
    qEnqueue(josephusQueue, i);

  while (!qEmpty(josephusQueue))
  {
    for (i = 0; i < m - 1; i++)
      qEnqueue(josephusQueue, qDequeue(josephusQueue));
    printf("%d ", qDequeue(josephusQueue));
  }
  printf("\n");

  free(josephusQueue);

  return 0;
}
#else
#endif

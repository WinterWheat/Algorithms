#ifndef _RBTREE_H_
#define _RBTREE_H_
#include <stdio.h>
#include <stdlib.h>
#define RBTDataType int
#define BLACK 'b'
#define RED 'r'
typedef struct RBTreeNode
{
    char        color;
    struct      RBTreeNode *parent;
    struct      RBTreeNode *left;
    struct      RBTreeNode *right;
    RBTDataType key;
}tRBTreeNode;
tRBTreeNode NilRBTNode = {'b',NULL,NULL,NULL};
/*
 * Search a RBTreeNode in RBTree
 */
tRBTreeNode* RBTreeSearch(tRBTreeNode* T, RBTDataType data);
/*
 * Search a RBTreeNode in RBTree with condition specified by callback function
 */
tRBTreeNode* RBTConSearch(tRBTreeNode* T, RBTDataType data, int *Contition(tRBTreeNode* T, RBTDataType data));
/*
 * Insert a RBTreeNode to RBTree
 */
int RBTreeInsert(tRBTreeNode* T, RBTDataType data);
/*
 * Fix up the RBTree after insertion to maintain it's RBTree property
 */
int RBTInsertFixUp(tRBTreeNode* T, tRBTreeNode* tNode);
/*
 * Delete a RBTreeNode from RBTree
 */
int RBTreeDelete(tRBTreeNode* T, RBTDataType data);
/*
 * Left rotate
 */
int RBTLeftRotate(tRBTreeNode *T, tRBTreeNode *tNode);
/*
 * Right rotate
 */
int RBTRightRotate(tRBTreeNode *T, tRBTreeNode *tNode);
int ShowAllRBTNode(tRBTreeNode *T);
int DestroyRBTree(tRBTreeNode *T);
tRBTreeNode *RBTreeMinimum(tRBTreeNode *T);
#endif //_RBTREE_H_

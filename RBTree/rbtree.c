#include "rbtree.h"

/*
 * Insert a RBTreeNode to RBTree
 */
int RBTreeInsert(tRBTreeNode* T, RBTDataType data)
{
   // tRBTreeNode *tmp = (tRBTreeNode *)malloc(sizeof(tRBTreeNode));
    if(T == NULL)
    {
        return -1;
    }
    tRBTreeNode *y = NULL;
	tRBTreeNode *x = T;
	tRBTreeNode *z = malloc(sizeof(tRBTreeNode));
	z->parent = NULL;
	z->left = NULL;
	z->right = NULL;
	z->key = data;
	z->color = RED;
	while(x != NULL)
	{
	    y = x;
		if(x->key <= data)
        {
            x = x->right;
        }
        else
        {
            x = x->left;
        }
	}
    z->parent = y;
    if(y == NULL)
    {
        T = z;
    }
    else if(z->key < y->key)
    {
        y->left = z;
    }
    else
    {
        y->right = z;
    }
    //tmp = z;
   RBTInsertFixUp(T, z);
    return 0;
}

/*
 * Fix up the RBTree after insertion to maintain it's RBTree property
 */
int RBTInsertFixUp(tRBTreeNode* T, tRBTreeNode* tNode)
{
    tRBTreeNode* tmp;
    while(tNode->parent->color == RED)
    {
        /*tNode's parent is the left child of tNode's parent's parent*/
        if(tNode->parent == tNode->parent->parent->left)
        {
            tmp = tNode->parent->parent->right;
            /*Condition 1*/
            if(tmp->color == RED)
            {
                tNode->parent->color = BLACK;
                tNode->parent->parent->color = RED;
                tNode->parent->parent->right->color = BLACK;
                tNode = tNode->parent->parent;
            }
            /*Condition 2*/
            else if(tNode == tNode->parent->right)
            {
                tNode = tNode->parent;
                RBTLeftRotate(T, tNode);
            }
            /*Condition 3*/
            tNode->parent->color = BLACK;
            tNode->parent->parent->color = RED;
            tNode = tNode->parent->parent;
            RBTLeftRotate(T, tNode);
        }
        else
        {
            tmp = tNode->parent->parent->left;
            /*Condition 1*/
            if(tmp->color == RED)
            {
                tNode->parent->color = BLACK;
                tNode->parent->parent->color = RED;
                tNode->parent->parent->left->color = BLACK;
                tNode = tNode->parent->parent;
            }
            /*Condition 2*/
            else if(tNode == tNode->parent->left)
            {
                tNode = tNode->parent;
                RBTRightRotate(T, tNode);
            }
            /*Condition 3*/
            tNode->parent->color = BLACK;
            tNode->parent->parent->color = RED;
            tNode = tNode->parent->parent;
            RBTRightRotate(T, tNode);
        }
    }
    T->color = BLACK;
    return 0;
}

/*
 * Delete a RBTreeNode from RBTree
 */
int RBTreeDelete(tRBTreeNode* T, RBTDataType data)
{
    return 0;
}

/*
 * Left Rotate if the specified not has a nonNULL right child
 */
 /*
 * Search a RBTreeNode in RBTree
 */
tRBTreeNode* RBTreeSearch(tRBTreeNode* T, RBTDataType data)
{
    return 0;
}

/*
 * Search a RBTreeNode in RBTree with condition specified by callback function
 */
tRBTreeNode* RBTConSearch(tRBTreeNode* T, RBTDataType data, int *Contition(tRBTreeNode* T, RBTDataType data))
{
    return 0;
}


int RBTLeftRotate(tRBTreeNode *T, tRBTreeNode* tNode)
{
    tRBTreeNode *tmp = (tRBTreeNode *)malloc(sizeof(tRBTreeNode));
    tmp = tNode->right;
    /*turn tmp's left subtree into tNode's right subtree*/
    if(tmp->left != NULL)
    {
        tNode->right = tmp->left;
        tNode->left->parent = tmp;
    }
    /*link tmp's parent to tNode's parent*/
    tmp->parent = tNode->parent;
    if(tNode->parent == NULL)
    {
        T = tNode;
    }
    else
    {
        if(tNode == tNode->parent->left)
        {
            tmp = tNode->parent->left;
        }
        else
        {
            tmp = tNode->parent->right;
        }
    }
    /*put tNode on tmp's left*/
    tmp->left = tNode;
    tNode->parent = tmp;
    return 0;
}

int RBTRightRotate(tRBTreeNode * T, tRBTreeNode *tNode)
{
    tRBTreeNode *tmp = (tRBTreeNode *)malloc(sizeof(tRBTreeNode));
    tmp = tNode->left;
    /*turn tmp's right subtree into tNode's left subtree*/
    if(tmp->right != NULL)
    {
        tNode->left = tmp->right;
        tNode->right->parent = tmp;
    }
    /*link tmp's parent to tNode's parent*/
    tmp->parent = tNode->parent;
    if(tNode->parent == NULL)
    {
        T = tNode;
    }
    else
    {
        if(tNode == tNode->parent->right)
        {
            tmp = tNode->parent->right;
        }
        else
        {
            tmp = tNode->parent->left;
        }
    }
    /*put tNode on tmp's right*/
    tmp->right = tNode;
    tNode->parent = tmp;
    return 0;
}

int ShowAllRBTNode(tRBTreeNode * T)
{

    if(T != NULL)
    {
        printf("%d, %c\n", T->key, T->color);
        if(T->left != NULL)
        {
            ShowAllRBTNode(T->left);
        }
        if(T->right != NULL)
        {
            ShowAllRBTNode(T->right);
        }
    }
    return 0;
}

int DestroyRBTree(tRBTreeNode * T)
{
    return 0;
}

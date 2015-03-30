#include <stdio.h>
#include <stdlib.h>
#include "rbtree.h"

int main()
{
    tRBTreeNode *T = (tRBTreeNode *)malloc(sizeof(tRBTreeNode));
    T->color = 'b';
    T->key = 100;
    T->left = NULL;
    T->right = NULL;
    T->parent = NULL;
    ShowAllRBTNode(T);
    RBTreeInsert(T, 97);
    RBTreeInsert(T, 98);
/*
    RBTreeInsert(T, 96);
    RBTreeInsert(T, 99);
    RBTreeInsert(T, 91);
    RBTreeInsert(T, 95);
    RBTreeInsert(T, 92);
    RBTreeInsert(T, 94);
    RBTreeInsert(T, 93);
*/
    ShowAllRBTNode(T);
    return 0;
}
/*
tRBTreeNode *RBTreeInit()
{
    int flag;
    RBTDataType data;
    tRBTreeNode *T;
    printf("Input data:");
    scanf("%d", &data);
    T = (tRBTreeNode *)malloc(sizeof(tRBTreeNode));
    T->key = data;
    T->color = BLACK;
    T->parent = NULL;
    T->left = NULL;
    T->right = NULL;
    printf("Input another data? 1 = yes , 0 = no\n");
    scanf("%d",&flag);
    while(flag)
    {
        printf("Input data:");
        scanf("%d", &data);
        RBTreeInsert(T,data);
        printf("Input another data? 1 = yes , 0 = no\n");
        scanf("%d",&flag);
    }
    return T;
}
*/

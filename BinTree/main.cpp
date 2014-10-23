#include <iostream>
#include "BinTree.h"
int main()
{
    BinTree *BTree = new BinTree();
    BTree->BinTreeInsert(1);
    BTree->BinTreeInsert(3);
    BTree->BinTreeInsert(3);
    BTree->BinTreeInsert(6);
    BTree->BinTreeInsert(20);
    BTree->BinTreeInsert(0);
    BTree->BinTreeInsert(15);
    BTree->BinTreeInsert(10);
    BTree->Display(BTree->getBinTreeRoot());
    cout<<endl<<"Recursive Inorder Visit:"<<endl;
    BTree->RecurInOrderVisit(BTree->getBinTreeRoot());
    cout<<endl<<"Non Recursive Inorder Visit:"<<endl;
    BTree->NonRecurInOrderVisit(BTree->getBinTreeRoot());
    return 0;
}

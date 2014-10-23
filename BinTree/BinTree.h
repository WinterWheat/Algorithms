#ifndef _BINTREE_H_
#define _BINTREE_H_
#include <stack>

using namespace std;

#define BTNType int

class BinTreeNode
{
public:
	BTNType data;
	BinTreeNode *parent;
	BinTreeNode *right;
	BinTreeNode *left;
	bool visited;
	BinTreeNode();
	BinTreeNode(BTNType data);
};
BinTreeNode::BinTreeNode()
{
	parent = NULL;
	right = NULL;
	left = NULL;
	visited = false;
}
BinTreeNode::BinTreeNode(BTNType d)
{
	data = d;
	parent = NULL;
	right = NULL;
	left = NULL;
	visited = false;
}

class BinTree
{
public:
	BinTreeNode *root;
	BinTree();
	BinTree(BTNType d);
	BinTree(BinTreeNode *r);
	BinTreeNode *getBinTreeRoot() const
	{
		return root;
	}
	void BinTreeInsert(BTNType d);
	void BinTreeDelete(BTNType d);
	void Display(BinTreeNode *root) const;
	void RecurInOrderVisit(BinTreeNode *root) const;
	void RecurPreOrderVisit(BinTreeNode *root) const;
	void RecurPostOrderVist(BinTreeNode *root) const;
	void NonRecurInOrderVisit(BinTreeNode *root) const;
};
BinTree::BinTree()
{
	root = NULL;
}
BinTree::BinTree(BTNType d)
{
	root = new BinTreeNode(d);
}
BinTree::BinTree(BinTreeNode *r)
{
	root = r;
}
void BinTree::BinTreeInsert(BTNType d)
{
	BinTreeNode *pNodeX = root;
	BinTreeNode *pNodeY = NULL;
	BinTreeNode *pNode = new BinTreeNode(d);
	while (pNodeX != NULL)
	{
		pNodeY = pNodeX;
		if (pNode->data <pNodeX->data)
		{
			pNodeX = pNodeX->left;
		}
		else
		{
			pNodeX = pNodeX->right;
		}
	}
	pNode->parent = pNodeY;
	if (pNodeY == NULL)
	{
		root = pNode;
	}
	else if (pNode->data < pNodeY->data)
	{
		pNodeY->left = pNode;
	}
	else
	{
		pNodeY->right = pNode;
	}
}
void BinTree::Display(BinTreeNode *root) const
{
	if (NULL != root)
	{
		cout << root->data;
		if (NULL != root->left)
		{
			cout << "{";
			Display(root->left);
		}
		if (NULL != root->right)
		{
			cout << ",";
			Display(root->right);
			cout << "}";
		}
	}
}
void BinTree::RecurInOrderVisit(BinTreeNode *root) const
{
	if (NULL != root)
	{
		RecurInOrderVisit(root->left);
		cout << root->data << " ";
		RecurInOrderVisit(root->right);
	}
}
void BinTree::RecurPreOrderVisit(BinTreeNode *root) const
{
	if (root != NULL)
	{
		cout << root->data << " ";
		RecurPreOrderVisit(root->left);
		RecurPreOrderVisit(root->right);
	}
}

void BinTree::RecurPostOrderVist(BinTreeNode *root) const
{
	if (root != NULL)
	{
		RecurPreOrderVisit(root->left);
		RecurPreOrderVisit(root->right);
		cout << root->data << " ";
	}
}
void BinTree::NonRecurInOrderVisit(BinTreeNode *root) const
{
	stack <BinTreeNode *> s;
	BinTreeNode *pNode = root;
	while (pNode != NULL || !s.empty())
	{
		while (pNode != NULL)
		{
			s.push(pNode);
			pNode = pNode->left;
		}
		if (!s.empty())
		{
			pNode = s.top();
			cout << pNode->data << " ";
			s.pop();
			pNode = pNode->right;
		}
	}
}
#endif
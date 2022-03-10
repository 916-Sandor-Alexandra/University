#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
using namespace std;

/// COMPLEXITY:
///  Overall: T(1)
SortedMultiMap::SortedMultiMap(Relation r) {
    this->length = 0;
    this->root = nullptr;
    this->relation = r;
}

void SortedMultiMap::add(TKey c, TValue v) {
    TElem el{c,v};
    this->root = this->insert(this->root, el);
}

/// COMPLEXITY:
///  Overall: O(n)
Node *SortedMultiMap::insert(Node *node, TElem e)
{
    if (node == nullptr)
    {
        node = new Node();
        node->info = e;
        node->left = nullptr;
        node->right = nullptr;
        this->length++;
    }
    else if (!relation(node->info.first, e.first))
        node->left = insert(node->left, e);
    else
        node->right = insert(node->right, e);
    return node;
}

/// COMPLEXITY:
///  Overall: O(n)
vector<TValue> SortedMultiMap::search(TKey c) const {
    vector<TValue> tmp;
    Node *node = this->root;

    while (node != nullptr) {
        if (c == node->info.first) {
            tmp.push_back(node->info.second);
        }

        if (!relation(node->info.first, c))
        {
            node = node->left;
        }
        else{
            node = node->right;
        }
    }

    return tmp;
}

/// COMPLEXITY:
///  Overall: O(n)
Node* getMinimumKey(Node *current)
{
    while (current->left != nullptr)
    {
        current = current->left;
    }
    return current;
}


/// COMPLEXITY:
///  Overall: O(n)
bool SortedMultiMap::removeRec(Node* node, TKey c, TValue v)
{
    Node *lastNode = nullptr;

    while (node != nullptr)
    {
        if (c == node->info.first && v == node->info.second)
        {
                // if the current node has no children
                if (node->left == nullptr && node->right == nullptr)
                {
                    // if the node is a leaf node, but it's not the root
                    if (node  != this->root)
                    {
                        if (lastNode->left == node)
                        {
                            lastNode->left = nullptr;
                        }
                        // if it's the root, we set the tree as empty
                        else
                        {
                            lastNode->right = nullptr;
                        }
                    }
                    else
                    {
                        this->root = nullptr;
                    }
                }
                // if the node has two children
                else if (node->left && node->right)
                {
                    Node* successor = getMinimumKey(node->right);
                    TElem val = successor->info;
                    this->removeRec(node, successor->info.first, successor->info.second);
                    node->info = val;
                }
                // if the node has only want child
                else
                {
                    Node* child = (node->left) ? node->left : node->right;
                    // if the node is not the root
                    if (node != root)
                    {
                        if (node == lastNode->left)
                        {
                            lastNode->left = child;
                        }
                        else
                        {
                            lastNode->right = child;
                        }
                    }
                    else
                     {
                        root = child;
                    }
                }
                return true;
        }
        if (!relation(node->info.first, c))
        {
            lastNode = node;
            node = node->left;
        }
        else
         {
            lastNode = node;
            node = node->right;
        }
    }
    return false;
}



bool SortedMultiMap::remove(TKey c, TValue v) {
    if (this->removeRec(root, c, v))
    {
        this->length--;
        return true;
    }
    return false;
}

/// COMPLEXITY:
///  Overall: T(1)
int SortedMultiMap::size() const {
	return this->length;
}

/// COMPLEXITY:
///  Overall: T(1)
bool SortedMultiMap::isEmpty() const {
	if(this->length == 0)
	    return true;
	return false;
}


SMMIterator SortedMultiMap::iterator() const {
	return SMMIterator(*this);
}


/// COMPLEXITY:
///  Overall: T(n)
SortedMultiMap::~SortedMultiMap() {
    Node* node = this->root;
    this->destructor(node);
}


/// COMPLEXITY:
///  Overall: T(n)
void SortedMultiMap::destructor(Node *node) {
    if (node != nullptr) {
        this->destructor(node->right);
        this->destructor(node->left);
        delete node;
    }
}

/// COMPLEXITY:
///  Overall: O(n^2)
vector<TValue> SortedMultiMap::removeKey(TKey key) {
        vector<TValue> values = this->search(key);

        for(int value : values)
        {
            remove(key, value);
        }
        return values;
}


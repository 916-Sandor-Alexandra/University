#include <iostream>
#include "SMMIterator.h"
#include "SortedMultiMap.h"

/// COMPLEXITY:
///  Overall: T(n)
SMMIterator::SMMIterator(const SortedMultiMap& d) : map(d){
    stack = new Node *[map.size()];
    for (int i = 0; i < map.size(); i++)
        stack[i] = nullptr;
    first();
}


/// COMPLEXITY:
///  Overall: T(n)
void SMMIterator::first() {

    index = 0;
    Node *current = map.root;

    while (current != nullptr)
    {
        stack[index] = current;
        index++;
        current = current->left;
    }
}

/// COMPLEXITY:
///  Overall: T(n)
void SMMIterator::next() {
    if (!valid())
        throw exception();
    Node *current = stack[index - 1]->right;
    index--;
    while (current != nullptr) {
        stack[index] = current;
        index++;
        current = current->left;
    }
}


/// COMPLEXITY:
///  Overall: T(1)
bool SMMIterator::valid() const {
    if (index == 0)
        return false;
    return true;
}

/// COMPLEXITY:
///  Overall: T(1)
TElem SMMIterator::getCurrent() const {
    if (!valid())
        throw exception();
    Node *current = stack[index - 1];
    return current->info;
}



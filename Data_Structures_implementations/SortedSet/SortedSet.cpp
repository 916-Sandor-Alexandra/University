#include "SortedSet.h"
#include "SortedSetIterator.h"

SortedSet::SortedSet(Relation r)
{
	this->relation = r;
	this->length = 0;
	this->head = nullptr;
}

// COMPLEXITY:
// Best Case: Theta(1)
// Best Worst: Theta(1)
// Overall: Theta(1)
bool SortedSet::add(TComp elem) {
    // if the element exists already in the set
    if(this->search(elem))
        return false;
    // if this is the first elem that is added to the set
    if(this->length == 0)
    {
        Node *newNode = new Node;
        newNode->data = elem;
        newNode->next = nullptr;
        this->head = newNode;
        this->length++;
        return true;
    }
    else
        // if there is one element in the set
    if(this->length == 1)
    {
        // if the relation is fulfilled by the new element and the existing element in that order
        // the new element becomes the first element
        if(relation(elem, head->data))
        {
            Node* ptr = this->head;
            Node* newNode = new Node;
            newNode->data = elem;
            newNode->next = ptr;
            this->head = newNode;
            this->length++;
            return true;
        }
        // if the relation is fulfilled by the existing element and the new element in that order
        // the new element becomes the second element
        if(relation(head->data, elem))
        {
            Node* newNode = new Node;
            newNode->data = elem;
            newNode->next = nullptr;
            head->next = newNode;
            this->length++;
            return true;
        }
    }
    // if there is more than one element in the set
    else
    {
        int i = 1;
        Node* ptr = this->head;
        while(ptr)
        {
            // if the new element becomes the first element
            if(i == 1 && relation(elem, ptr->data))
            {
                Node *newNode = new Node;
                newNode->data = elem;
                newNode->next = ptr;
                this->head = newNode;
                this->length++;
                return true;
            }
            // if the new element is placed between 2 elements
            if(i != this->length && relation(ptr->data, elem) && relation(elem, ptr->next->data))
            {
                Node *newNode = new Node;
                newNode->data = elem;
                Node* nxt = ptr->next;
                newNode->next = nxt;
                ptr->next = newNode;
                this->length++;
                return true;
            }
            // if the new element is placed at the end of the set
            if(i == this->length && relation(ptr->data, elem))
            {
                Node *newNode = new Node;
                newNode->data = elem;
                newNode->next = nullptr;
                ptr->next = newNode;
                this->length++;
                return true;
            }
            i++;
            ptr = ptr->next;
        }
    }
}

// COMPLEXITY:
// Best Case: Theta(1)
// Worst Case: Theta(n)
// Overall: O(n)
bool SortedSet::remove(TComp elem) {
    // the element must exist in the set
    if(this->search(elem))
    {
        Node* ptr = this->head;
        Node* prev;
        while(ptr)
        {
            if(ptr->data == elem)
            {
                // if the element that we want to remove is the first in the set
                if(ptr == this->head)
                {
                    this->head = this->head->next;
                    this->length--;
                    return true;
                }
                // link the previous and next node
                // delete the current node
                Node* nxt = ptr->next;
                prev->next = nxt;
                delete ptr;
                this->length--;
                return true;
            }
            prev = ptr;
            ptr = prev->next;
        }
    }
	return false;
}

// COMPLEXITY:
// Best Case: Theta(1)
// Worst Case: Theta(n)
// Overall: O(n)
bool SortedSet::search(TComp elem) const
{
    Node* ptr;
    ptr = this->head;
    while(ptr != nullptr)
    {
        if(ptr->data == elem)
            return true;
        ptr = ptr->next;
    }
    return false;
}

// COMPLEXITY:
// Overall: Theta(1)
int SortedSet::size() const {
	return this->length;
}

// COMPLEXITY:
// Overall: Theta(1)
bool SortedSet::isEmpty() const {
    if(this->length == 0)
        return true;
    return false;
}

// COMPLEXITY:
// Overall: Theta(1)
SortedSetIterator SortedSet::iterator() const {
	return SortedSetIterator(*this);
}

// COMPLEXITY:
// Overall: Theta(n)
SortedSet::~SortedSet() {
    Node* current = this->head;
    Node* next;
    for(int i = 0; i < this->size(); i++)
    {
        next = current->next;
        delete current;
        current = next;
    }
}

// COMPLEXITY:
// Overall: Theta(n)
void SortedSet::empty()
{
    if(this->isEmpty())
        throw std::exception();

    Node* current = this->head;
    Node* next;
    while(current != nullptr)
    {
        next = current->next;
        delete current;
        current = next;
    }
    this->length = 0;
    this->head = nullptr;
}


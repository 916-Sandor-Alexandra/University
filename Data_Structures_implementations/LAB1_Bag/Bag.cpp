#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;


Bag::Bag() {
    this->noElements = 0;
    this->capacity = 20;
    this->elements = new TElem[this->capacity];
    this->minElement = INT_MAX;
    this->maxElement = INT_MIN;
}

// COMPLEXITY:
// Best case: Theta(1)
// Worst case: O(n^2)
void Bag::add(TElem elem) {

    if(this->noElements == 0 && this->minElement == INT_MAX && this->maxElement == INT_MIN)
    {
        this->minElement = this->maxElement = elem;
        this->elements[0] = 1;
        this->noElements++;
    }
    else
    if(elem < this->minElement)
    {
        int toAdd =  this->minElement - elem;
        int newBagLength = this->maxElement - elem + 1;
        while(newBagLength > this->capacity)
            resizeBag();

        int bagLength = this->getBagLength();
        for(int k = 0 ;k < toAdd; k++)
        {
            for(int i = bagLength; i > 0; i--)
                this->elements[i] = this->elements[i - 1];
            bagLength++;
            this->elements[0] = 0;
        }
        this->minElement = elem;
        this->noElements++;
        this->elements[0]++;
    }
    else
    if(elem > this->maxElement)
    {
        int toAdd =  elem - this->maxElement;
        int newBagLength = elem - this->minElement + 1;
        while(newBagLength > this->capacity)
            resizeBag();

        int bagLength = this->getBagLength();
        for(int i= bagLength; i < bagLength + toAdd; i++)
            this->elements[i] = 0;

        this->noElements++;
        this->maxElement = elem;
        bagLength = this->getBagLength();
        this->elements[bagLength - 1]++;
    }
    else
    {
        this->elements[elem - this->minElement]++;
        this->noElements++;
    }

}

// COMPLEXITY:
// Overall: Theta(1)
void Bag::addOccurrences(int nrOccurrences, TElem elem)
{
    if(nrOccurrences < 0)
        throw exception();
    if(this->minElement <= elem <= this->maxElement)
    {
        this->elements[elem - this->minElement] += nrOccurrences;
    }
    else
    {
        this->add(elem);
        this->elements[elem - this->minElement] += nrOccurrences - 1;
    }
}


// COMPLEXITY:
// Best case: Theta(1),
// Worst case: O(n^2)
bool Bag::remove(TElem elem) {

    int bagLength = this->maxElement - this->minElement + 1;
    if(elem >= this->minElement && elem <= this->maxElement && this->elements[elem - this->minElement] > 0 && this->noElements > 0)
    {
        this->elements[elem - this->minElement]--;
        if(elem == this->minElement && elem == this->maxElement)
        {
            if(elements[elem - this->minElement] == 0)
            {
                this->minElement = INT_MAX;
                this->maxElement = INT_MIN;
            }
            this->noElements--;
            return true;
        }

        else if(elem == this->minElement && this->elements[0] == 0)
        {
            while(this->elements[0] == 0)
            {
                for(int i=1; i< bagLength; i++)
                    this->elements[i - 1] = this->elements[i];
                bagLength--;
                this->minElement++;
            }
            this->noElements--;
            return true;
        }

        else if(elem == this->maxElement && this->elements[bagLength - 1] == 0)
        {
            while(this->elements[bagLength - 1] == 0)
            {
                bagLength--;
                this->maxElement--;
            }
            this->noElements--;
            return true;
        }
        this->noElements--;
        return true;
    }
    return false;
}

// COMPLEXITY:
// Overall: Theta(1)
bool Bag::search(TElem elem) const {
    if(elem < this->minElement || elem > this->maxElement)
        return false;
    if(this->elements[elem - this->minElement] == 0)
        return false;
    return true;
}

// COMPLEXITY:
// Overall: Theta(1)
int Bag::nrOccurrences(TElem elem) const {
    if(elem >= this->minElement && elem <= this->maxElement && this->elements[elem - this->minElement] > 0)
        return this->elements[elem - this->minElement];
    else
        return 0;
}

// COMPLEXITY:
// Overall: Theta(1)
int Bag::size() const {
    return this->noElements;
}

// COMPLEXITY:
// Overall: Theta(1)
bool Bag::isEmpty() const {
    if(this->size() == 0)
        return true;
    else
        return false;
}

// COMPLEXITY:
// Overall: Theta(1)
BagIterator Bag::iterator(){
    return BagIterator(*this);
}

// COMPLEXITY:
// Overall: Theta(1)
Bag::~Bag() {
    delete[] this->elements;
}


// COMPLEXITY:
// OVERALL: Theta(n)
void Bag::resizeBag()
{
    this->capacity *= 2;
    TElem* newBag;
    newBag = new TElem[this->capacity];
    int bagLength = this->getBagLength();
    for (int i = 0; i < bagLength; i++)
    {
        newBag[i] = this->elements[i];
    }
    delete [] this->elements;
    this->elements = newBag;
}

// COMPLEXITY:
// Overall: Theta(1)
int Bag::getBagLength() const {
    if(this->noElements == 0)
        return 0;
    else
        return this->maxElement - this->minElement + 1;
}



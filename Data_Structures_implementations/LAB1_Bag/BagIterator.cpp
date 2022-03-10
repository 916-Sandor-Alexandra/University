#include <exception>
#include "BagIterator.h"
#include "Bag.h"
#include <iostream>

using namespace std;

// COMPLEXITY
// Overall: Theta(1)
BagIterator::BagIterator(Bag& c): bag(c)
{
	this->index = 0;
    this->frequency = this->bag.elements[0];
    this->element = this->bag.minElement;
    this->frequency--;
}

// COMPLEXITY
// Overall: Theta(1)
void BagIterator::first() {
    this->index = 0;
    this->frequency = this->bag.elements[0];
    this->element = this->bag.minElement;
    this->frequency--;
}

// COMPLEXITY
// Best Case: Theta(1)
// Worst Case: O(n) -> no other elements between the minimum and maximum
void BagIterator::next() {
    int bagLength = this->bag.getBagLength();
	if(this->index == bagLength && this->frequency == 0 || !this->valid())
    {
        throw exception();
    }
	else
    {
	    if(this->frequency == 0)
        {
            this->index++;
            while(this->bag.elements[index] == 0)
                this->index++;
            this->frequency = this->bag.elements[index];
            this->element = this->bag.minElement + index;
        }
	    if(this->frequency > 0)
        {
            this->frequency--;
        }
    }
}

// COMPLEXITY
// Overall: Theta(1)
bool BagIterator::valid(){
    int bagLength;
    if(this->bag.noElements > 0)
        bagLength = this->bag.maxElement - this->bag.minElement + 1;
    else
        bagLength = 0;
	if(this->index < bagLength)
    {
        return true;
    }
	return false;
}

// COMPLEXITY
// Overall: Theta(1)
TElem BagIterator::getCurrent()
{
    int bagLength;
    if(this->bag.noElements > 0)
        bagLength = this->bag.maxElement - this->bag.minElement + 1;
    else
        bagLength = 0;
    if(this->index == bagLength || !this->valid())
    {
        throw exception();
    }
    else
    {
        return this->element;
    }
}

TElem BagIterator::remove()
{
    if(this->bag.isEmpty() || this->bag.noElements == 1 || this->getCurrent() == this->bag.maxElement)
        throw exception();
    int current = this->getCurrent();
    this->next();
    int nxt = this->getCurrent();
    this->bag.remove(current);
    while(this->valid())
    {
        if(this->getCurrent() == nxt)
        {
            return this->getCurrent();
        }
        else
            this->next();
    }
}

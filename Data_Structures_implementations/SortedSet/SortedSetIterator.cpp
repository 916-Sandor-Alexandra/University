#include "SortedSetIterator.h"
#include <exception>

using namespace std;

// COMPLEXITY:
// Overall: Theta(1)
SortedSetIterator::SortedSetIterator(const SortedSet& m) : multime(m)
{
	this->index = 0;
	this->current = m.head;
}

// COMPLEXITY:
// Overall: Theta(1)
void SortedSetIterator::first() {
    this->index = 0;
    this->current = multime.head;
}

// COMPLEXITY:
// Overall: Theta(1)
void SortedSetIterator::next() {
    if(!this->valid() || this->multime.isEmpty())
    {
        throw exception();
    }
    this->index++;
    this->current = this->current->next;
}

// COMPLEXITY:
// Overall: Theta(1)
TElem SortedSetIterator::getCurrent()
{
    if(!this->valid())
    {
        throw exception();
    }
    return this->current->data;
}

// COMPLEXITY:
// Overall: Theta(1)
bool SortedSetIterator::valid() const {
	if(this->index < this->multime.length)
	    return true;
	return false;
}


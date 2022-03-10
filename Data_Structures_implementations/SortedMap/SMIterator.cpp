#include "SMIterator.h"
#include "SortedMap.h"
#include <exception>

using namespace std;

/// COMPLEXITY:
/// Overall: T(1)
SMIterator::SMIterator(const SortedMap& m) : map(m)
{
	this->currentIndex = m.map.head;
	this->pair = m.map.elems[this->currentIndex];
}

/// COMPLEXITY:
/// Overall: T(1)
void SMIterator::first(){
    this->currentIndex = this->map.map.head;
    this->pair = this->map.map.elems[this->currentIndex];
}

/// COMPLEXITY:
/// Overall: T(1)
void SMIterator::next(){
	if(this->valid())
    {
	    this->currentIndex = this->map.map.next[currentIndex];
	    this->pair = this->map.map.elems[this->currentIndex];
    }
	else
	    throw exception();
}

/// COMPLEXITY:
/// Overall: T(1)
bool SMIterator::valid() const{
	if(this->currentIndex != -1)
	    return true;
	return false;
}

/// COMPLEXITY:
/// Overall: T(1)
TElem SMIterator::getCurrent() const{
    if(this->valid())
	    return this->pair;
    throw exception();
}



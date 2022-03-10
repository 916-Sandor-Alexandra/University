#include "Bag.h"

class BagIterator
{
	//DO NOT CHANGE THIS PART
	friend class Bag;
	
private:
	Bag& bag;
	int index;
	int element;
	int frequency;

	BagIterator(Bag&);
public:
	void first();
	void next();
	TElem getCurrent();
	bool valid();
    //removes and returns the current element from the iterator
    //after the operation, the current element from the Iterator is the next element from the Bag,
    // or, if the removed element was the last one, the iterator is invalid
    //throws exception if the iterator is invalid
    TElem remove();
};

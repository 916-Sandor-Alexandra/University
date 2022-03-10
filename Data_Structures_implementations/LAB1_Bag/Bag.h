#pragma once
//DO NOT INCLUDE BAGITERATOR


//DO NOT CHANGE THIS PART
#define NULL_TELEM -111111;
typedef int TElem;
class BagIterator; 
class Bag {

private:
	TElem* elements;
	int noElements;
	int capacity;
	TElem minElement;
	TElem maxElement;

	//DO NOT CHANGE THIS PART
	friend class BagIterator;
public:
	//constructor
	Bag();

	//adds an element to the bag
	void add(TElem e);

	//removes one occurrence of an element from a bag
	//returns true if an element was removed, false otherwise (if e was not part of the bag)
	bool remove(TElem e);

	//checks if an element appears is the bag
	bool search(TElem e) const;

	//returns the number of occurrences for an element in the bag
	int nrOccurrences(TElem e) const;

	//returns the number of elements from the bag
	int size() const;

	//returns an iterator for this bag
	BagIterator iterator();

	//checks if the bag is empty
	bool isEmpty() const;

	//destructor
	~Bag();

	void addOccurrences(int nrOccurrences, TElem elem);

private:
    // doubles the allocated space for the bag, when the initial capacity is reached
    void resizeBag();

    // returns the length of representation of the bag
    int getBagLength() const;

};
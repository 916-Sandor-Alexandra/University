#include "SMIterator.h"
#include "SortedMap.h"
#include <exception>
#include <iostream>

using namespace std;

/// COMPLEXITY:
/// Overall: O(n)
SortedMap::SortedMap(Relation r) {
	this->relation = r;
	this->map.head = -1;
	this->map.tail = -1;
	this->map.firstEmpty = 1;
	this->map.capacity = 10000;
	this->map.size = 0;
	for(int i = 1; i < this->map.capacity; i++)
    {
	    this->map.next[i] = i + 1;
	    this->map.prev[i] = i - 1;
    }
    this->map.next[this->map.capacity] = -1;
	this->map.prev[this->map.head] = -1;
}

/// COMPLEXITY:
/// Overall: O(n)
TValue SortedMap::add(TKey k, TValue v)
{

    TElem newElem(k, v);

    // if this is the first element in the map
    if(this->isEmpty())
    {
        this->map.elems[1] = newElem;
        this->map.head = 1;
        this->map.tail = 1;
        this->map.size++;
        this->map.firstEmpty = this->map.next[this->map.firstEmpty];
        this->map.next[1] = -1;
        return NULL_TVALUE;
    }

    int current = this->map.head;
    // if key exists in the map
    if(this->search(k) != NULL_TVALUE)
    {
        TValue oldValue = this->search(k);
        while(current != -1)
        {
            if(this->map.elems[current].first == k)
            {
                this->map.elems[current].second = v;
                return oldValue;
            }
            current = this->map.next[current];
        }
    }
    while(current != -1)
    {
        if(relation(k, this->map.elems[current].first))
        {

            // the element we want to add will be at the beginning of the map
            if(current == this->map.head)
            {
                int pos = this->map.firstEmpty;
                this->map.firstEmpty = this->map.next[this->map.firstEmpty];
                this->map.elems[pos] = newElem;
                this->map.size++;
                this->map.next[pos] = current;
                this->map.prev[pos] = -1;
                this->map.prev[current] = pos;
                this->map.head = pos;

                return NULL_TVALUE;
            }

            // the element we want to add will be between 2 elements
            int pos = this->map.firstEmpty;
            this->map.firstEmpty = this->map.next[this->map.firstEmpty];
            this->map.elems[pos] = newElem;
            this->map.size++;
            int prev;
            prev = this->map.prev[current];
            this->map.next[pos] = current;
            this->map.prev[pos] = prev;
            this->map.prev[current] = pos;
            this->map.next[prev] = pos;
            return NULL_TVALUE;
        }

        // the element we want to add will be at the end of the map
        if(current == this->map.tail && relation(this->map.elems[current].first, k))
        {
            int pos = this->map.firstEmpty;
            this->map.firstEmpty = this->map.next[this->map.firstEmpty];
            this->map.elems[pos] = newElem;
            this->map.size++;
            this->map.next[pos] = -1;
            this->map.prev[pos] = current;
            this->map.next[current] = pos;
            this->map.tail = pos;
            return NULL_TVALUE;
        }
        current = this->map.next[current];
    }
}


/// COMPLEXITY:
/// Overall: O(n)
TValue SortedMap::search(TKey k) const
{
	int current = this->map.head;
	while(current != -1)
    {
	    if(this->map.elems[current].first == k)
	        return this->map.elems[current].second;
	    current = this->map.next[current];
    }
	return NULL_TVALUE;
}


/// COMPLEXITY:
/// Overall: O(n)
TValue SortedMap::remove(TKey k) {
    TValue returnedValue = this->search(k);
	if(returnedValue == NULL_TVALUE)
	    return NULL_TVALUE;
	int current = this->map.head;

	while(current != -1)
    {
	    if(this->map.elems[current].first == k)
        {
	        int next, prev;
	        next = this->map.next[current];
	        prev = this->map.prev[current];
	        this->map.elems[current] = NULL_TPAIR;
	        this->map.next[current] = this->map.firstEmpty;
	        this->map.next[prev] = next;
	        this->map.prev[next] = prev;
	        this->map.firstEmpty = current;
	        this->map.size--;
	        if(current == this->map.head)
	            this->map.head = next;
	        if(current == this->map.tail)
	            this->map.tail = prev;

	        return returnedValue;
        }
	    current = this->map.next[current];
    }
}


/// COMPLEXITY:
/// Overall: T(1)
int SortedMap::size() const {
	return this->map.size;
}

/// COMPLEXITY:
/// Overall: T(1)
bool SortedMap::isEmpty() const {
	if (this->map.size == 0)
		return true;
	return false;
}

/// COMPLEXITY:
/// Overall: T(1)
SMIterator SortedMap::iterator() const {
	return SMIterator(*this);
}


/// COMPLEXITY:
/// Overall: T(n)
SortedMap::~SortedMap() {
	this->map.size = 0;
	this->map.capacity = 20;
	this->map.head = -1;
	this->map.firstEmpty = 1;
	this->map.tail = -1;
    for(int i = 1; i < this->map.capacity; i++)
    {
        this->map.next[i] = i + 1;
        this->map.prev[i] = i - 1;
    }
    this->map.prev[1] = -1;
    this->map.next[this->map.capacity] = -1;
}

/// COMPLEXITY:
/// Overall: O(n*n)
int SortedMap::updateValues(SortedMap &sm) {
    int noOfPairs = 0;
    int current = this->map.head;
    while(current != -1)
    {
        TKey key = this->map.elems[current].first;
        if(sm.search(key) != NULL_TVALUE)
        {
            this->map.elems[key].second = sm.search(key);
            noOfPairs++;
        }
        current = this->map.next[current];
    }
    return noOfPairs;
}


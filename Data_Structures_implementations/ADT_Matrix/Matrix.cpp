#include "Matrix.h"
#include <exception>
#include <iostream>
#include "MatrixIterator.h"

using namespace std;


/// COMPLEXITY:
/// Overall : T(n)
Matrix::Matrix(int nrLines, int nrCols)
{
    /// number of lines must be positive
    if(nrLines <= 0 || nrCols <=0)
        throw exception();

    this->noOfLines = nrLines;
    this->noOfColumns = nrCols;
    this->capacity = 8;
    this->count = 0;
    this->alpha = 0.4;  /// load factor
    this->threshold = (int)(this->alpha * this->capacity);
    // space allocation and array initialization
    this->elems = new Triplet[this->capacity];
    for(int i = 0 ; i < this->capacity; i++)
    {
        this->elems[i].line = -1;
        this->elems[i].column = -1;
        this->elems[i].value = NULL_TELEM;
    }
}

/// COMPLEXITY:
/// Overall: T(1)
int Matrix::nrLines() const {
	return this->noOfLines;
}

/// COMPLEXITY:
/// Overall: T(1)
int Matrix::nrColumns() const
{
	return this->noOfColumns;
}

/// COMPLEXITY:
/// Overall: O(n)
TElem Matrix::element(int i, int j)
{
	if(i < 0 || i >= this->noOfLines || j < 0 || j >= this->noOfColumns)
        throw exception();

	int result = this->search(i, j);
	if(result == -1)
	    return NULL_TELEM;
    return this->elems[result].value;
}

/// COMPLEXITY:
/// Overall : O(n)
TElem Matrix::modify(int i, int j, TElem e)
{
    if(i < 0 || i >= this->noOfLines)
        throw exception();
    if(j < 0 || j >= this->noOfColumns)
        throw exception();
    TElem result = this->search(i, j);
    // if there is no element in the table that has line = i and column = j
    if(result == -1)
    {
        Triplet elem{i, j, e};
        this->insert(elem);
        return NULL_TELEM;
    }
    else
    {
        TElem oldValue = this->elems[result].value;
        this->elems[result].value = e;
        return oldValue;
    }
}


/// COMPLEXITY:
/// Average: O(1)
/// Worst Case: O(n)
void Matrix::insert(Triplet elem) {
    /// when threshold value is reached -> RESIZE and REHASH table
    while(this->count == this->threshold)
        this->resize();

    int index = this->findValidPosition(elem);
    this->elems[index] = elem;
    this->count++;
}


/// COMPLEXITY:
/// Overall : T(n)
void Matrix::resize()
{
    int newCapacity = 2 * this->capacity;

    // Create new empty table
    Triplet* newElems = new Triplet[newCapacity];
    for(int i = 0 ; i < newCapacity; i++)
    {
        newElems[i].line = -1;
        newElems[i].column = -1;
        newElems[i].value = 0;
    }

    int newThreshold = int(newCapacity * this->alpha);
    this->threshold = newThreshold;
    int oldCapacity = this->capacity;
    this->capacity = newCapacity;

    for(int i = 0 ; i < oldCapacity; i++)
        // if the slot is not empty
        if(this->elems[i].value != NULL_TELEM)
        {
            Triplet elem = this->elems[i];

            int hash = this->hashing(elem.column * elem.line);
            int p = 0;
            int index = hash;
            while(newElems[index].value != NULL_TELEM  || index >= this->capacity)
            {
                index = (hash + this->probing(p)) % this->capacity;
                p++;
            }
            newElems[index] = this->elems[i];
        }

    delete [] this->elems;
    this->elems = newElems;
}

/// COMPLEXITY:
/// Overall: O(n)
/// Best Case: T(1)
/// Worst Case: T(n)
int Matrix::search(int l, int c) const
{
    for(int i = 0; i < this->capacity; i++)
        if(this->elems[i].line == l && this->elems[i].column == c)
            return i;
    return -1;
}

/// COMPLEXITY:
/// Overall: O(n)
int Matrix::findValidPosition(Triplet elem)
{
    int hash = this->hashing(elem.column * elem.line);
    int i = 0;
    int index = hash;
    while(this->elems[index].value != NULL_TELEM  || index >= this->capacity)
    {
        index = (hash + this->probing(i)) % this->capacity;
        i++;
    }
    return index;
}

MatrixIterator Matrix::iterator() const {
    return MatrixIterator(*this);
}



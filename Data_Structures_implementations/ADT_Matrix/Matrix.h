#pragma once

//DO NOT CHANGE THIS PART
typedef int TElem;
#define NULL_TELEM 0

class MatrixIterator;

struct Triplet
{
    int line;
    int column;
    TElem value;   // value != 0
};

class Matrix {
    friend class MatrixIterator;

private:
    int noOfLines;
    int noOfColumns;
    Triplet* elems;
    int capacity;
    double alpha;
    int count;
    int threshold;

    int probing(int x){ return (x*x + x) / 2;};
    /// in this case, the table size MUST be a power of 2
    int hashing(int k){ return k % this->capacity;};

public:
	//constructor
	Matrix(int nrLines, int nrCols);

	//returns the number of lines
	int nrLines() const;

	//returns the number of columns
	int nrColumns() const;

	//returns the element from line i and column j (indexing starts from 0)
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem element(int i, int j);

	//modifies the value from line i and column j
	//returns the previous value from the position
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem modify(int i, int j, TElem e);

    //creates an iterator over all the elements of the Matrix (no matter if they are NULL_TELEM or not).
    // The iterator will return the elements by line (first elements of the first line, then from second line, etc).

    MatrixIterator iterator() const;

    //Obs: You will have to implement the class MatrixIterator as well.

private:
    int findValidPosition(Triplet elem);

    void insert(Triplet elem);

    void resize();

    int search(int l, int c) const;
};



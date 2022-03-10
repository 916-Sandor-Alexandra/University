#include "MatrixIterator.h"
#include <exception>
#include <iostream>

/// COMPLEXITY:
/// T(1)
MatrixIterator::MatrixIterator(const Matrix &matrix) : matrix(matrix)
{
    this->line = 0;
    this->column = 0;
    int index = this->matrix.search(0,0);
    if(index != -1)
        this->current = this->matrix.elems[index].value;
    else
        this->current = 0;
}

/// COMPLEXITY:
/// T(1)
void MatrixIterator::first()
{
    this->line = 0;
    this->column = 0;
    int index = this->matrix.search(0,0);
    if(index != -1)
        this->current = this->matrix.elems[index].value;
    else
        this->current = 0;
}

/// COMPLEXITY:
/// T(1)
void MatrixIterator::next()
{
    if(valid())
    {
        if(this->column < this->matrix.nrColumns() - 1)
        {
            this->column++;
            int index = this->matrix.search(this->line,this->column);
            if(index != -1)
                this->current = this->matrix.elems[index].value;
            else
                this->current = 0;
        }
        else
        {
            this->line++;
            this->column = 0;
            int index = this->matrix.search(this->line,this->column);
            if(index != -1)
                this->current = this->matrix.elems[index].value;
            else
                this->current = 0;
        }
    }
}

/// COMPLEXITY:
/// T(1)
bool MatrixIterator::valid() const {
    if(this->line < this->matrix.nrLines() && this->column < this->matrix.nrColumns())
        return true;
    return false;
}

/// COMPLEXITY:
/// T(1)
TElem MatrixIterator::getCurrent() const {
    if(this->valid())
        return this->current;
}

#ifndef ADT_MATRIX_MATRIXITERATOR_H
#define ADT_MATRIX_MATRIXITERATOR_H
#pragma once

#include "Matrix.h"

class MatrixIterator {
    friend class Matrix;

private:
    const Matrix& matrix;
    explicit MatrixIterator(const Matrix& matrix);

    int line;
    int column;
    TElem current;

public:
    void first();
    void next();
    bool valid() const;
    TElem getCurrent() const;

};


#endif //ADT_MATRIX_MATRIXITERATOR_H

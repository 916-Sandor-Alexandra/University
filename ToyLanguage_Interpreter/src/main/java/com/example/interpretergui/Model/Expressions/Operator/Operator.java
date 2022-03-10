package com.example.interpretergui.Model.Expressions.Operator;

import com.example.interpretergui.Exceptions.Expr_Exceptions.DivisionByZeroException;

public interface Operator<T, R> {
    R calc(T x, T y) throws DivisionByZeroException;
    String toString();
}
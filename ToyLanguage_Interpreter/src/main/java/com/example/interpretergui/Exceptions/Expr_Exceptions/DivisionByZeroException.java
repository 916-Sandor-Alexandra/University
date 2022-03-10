package com.example.interpretergui.Exceptions.Expr_Exceptions;

public class DivisionByZeroException extends Exception{
    public DivisionByZeroException(String errorMessage) {
        super(errorMessage);
    }
}

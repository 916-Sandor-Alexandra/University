package com.example.interpretergui.Exceptions.Expr_Exceptions;

public class InvalidConstantException extends Exception{
    public InvalidConstantException(String errorMessage) {
        super(errorMessage);
    }
}

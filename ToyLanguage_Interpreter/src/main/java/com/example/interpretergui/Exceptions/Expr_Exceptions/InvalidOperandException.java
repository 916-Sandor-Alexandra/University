package com.example.interpretergui.Exceptions.Expr_Exceptions;

public class InvalidOperandException extends Exception{
    public InvalidOperandException(String errorMessage) {
        super(errorMessage);
    }
}
package com.example.interpretergui.Exceptions.Stmnt_Exceptions;

public class InvalidExpressionException extends Exception{
    public InvalidExpressionException(String errorMessage) {
        super(errorMessage);
    }
}

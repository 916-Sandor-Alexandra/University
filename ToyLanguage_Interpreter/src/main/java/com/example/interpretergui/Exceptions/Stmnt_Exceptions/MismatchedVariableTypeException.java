package com.example.interpretergui.Exceptions.Stmnt_Exceptions;

public class MismatchedVariableTypeException extends Exception{
    public MismatchedVariableTypeException(String errorMessage) {
        super(errorMessage);
    }
}
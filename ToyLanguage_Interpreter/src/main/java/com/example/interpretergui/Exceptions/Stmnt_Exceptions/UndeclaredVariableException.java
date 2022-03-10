package com.example.interpretergui.Exceptions.Stmnt_Exceptions;

public class UndeclaredVariableException extends Exception{
    public UndeclaredVariableException(String errorMessage) {
        super(errorMessage);
    }
}

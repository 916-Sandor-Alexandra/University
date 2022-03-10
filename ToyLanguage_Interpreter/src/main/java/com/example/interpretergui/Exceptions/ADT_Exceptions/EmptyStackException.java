package com.example.interpretergui.Exceptions.ADT_Exceptions;

public class EmptyStackException extends Exception{
    public EmptyStackException(String errorMessage) {
        super(errorMessage);
    }
}

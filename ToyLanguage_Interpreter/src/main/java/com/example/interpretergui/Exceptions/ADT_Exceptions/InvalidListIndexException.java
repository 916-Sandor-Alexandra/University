package com.example.interpretergui.Exceptions.ADT_Exceptions;

public class InvalidListIndexException extends Exception{
    public InvalidListIndexException(String errorMessage) {
        super(errorMessage);
    }
}

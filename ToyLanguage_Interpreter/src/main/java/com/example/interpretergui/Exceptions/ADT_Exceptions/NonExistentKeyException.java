package com.example.interpretergui.Exceptions.ADT_Exceptions;

public class NonExistentKeyException extends Exception{
    public NonExistentKeyException(String errorMessage) {
        super(errorMessage);
    }
}

package com.example.interpretergui.Exceptions.Expr_Exceptions;

public class ExpressionTypeCheckException extends Exception{
    public ExpressionTypeCheckException(String errorMessage) {
        super(errorMessage);
    }
}
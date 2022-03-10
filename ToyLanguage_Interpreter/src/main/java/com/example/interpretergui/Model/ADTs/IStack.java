package com.example.interpretergui.Model.ADTs;

import com.example.interpretergui.Exceptions.ADT_Exceptions.EmptyStackException;
import com.example.interpretergui.Exceptions.ADT_Exceptions.FullStackException;

import java.util.Stack;

public interface IStack<T> {

    T pop() throws EmptyStackException;
    void push(T v) throws FullStackException;
    boolean isEmpty();
    T top() throws EmptyStackException;
    String toString();
    IStack<T> copy();
    Stack<T> getStack();
}


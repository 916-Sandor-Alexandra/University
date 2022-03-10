package com.example.interpretergui.Model.ADTs;
import com.example.interpretergui.Exceptions.ADT_Exceptions.EmptyStackException;
import com.example.interpretergui.Exceptions.ADT_Exceptions.FullStackException;

import java.util.Stack;

public class ADTStack<T> implements IStack<T> {
    Stack<T> stack;

    public ADTStack() {
        this.stack = new Stack<T>();
    }

    public ADTStack(Stack<T> stack) {
        this.stack = stack;
    }

    @Override
    public T pop() throws EmptyStackException {
        if (this.stack.empty())
            throw new EmptyStackException("The stack is currently empty!");

        return this.stack.pop();
    }

    public T top() throws EmptyStackException {
        if (this.stack.empty())
            throw new EmptyStackException("The stack is currently empty!");
        return this.stack.peek();
    }

    @Override
    public void push(T v) throws FullStackException {
        if (this.stack.capacity() == this.stack.size())
            throw new FullStackException("The stack is full!");
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.empty();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

    @Override
    public IStack<T> copy() {
        Stack<T> myStack = new Stack<T>();
        myStack.addAll(this.stack);
        return new ADTStack<T>(myStack);
    }

    @Override
    public Stack<T> getStack() {
        return this.stack;
    }
}

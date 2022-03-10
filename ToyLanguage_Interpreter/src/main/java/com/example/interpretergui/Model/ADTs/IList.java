package com.example.interpretergui.Model.ADTs;

import com.example.interpretergui.Exceptions.ADT_Exceptions.EmptyListException;
import com.example.interpretergui.Exceptions.ADT_Exceptions.InvalidListIndexException;

import java.util.List;

public interface IList<T> {
    void add(T v);
    T pop() throws EmptyListException;
    String toString();
    T get(int index) throws InvalidListIndexException;
    List<T> getList();
    boolean empty();
    void clear();
    int size();
    IList<T> copy();
}

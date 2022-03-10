package com.example.interpretergui.Model.ADTs;
import com.example.interpretergui.Exceptions.ADT_Exceptions.EmptyListException;
import com.example.interpretergui.Exceptions.ADT_Exceptions.InvalidListIndexException;

import java.util.ArrayList;
import java.util.List;

public class ADTList<T> implements IList<T> {
    List<T> list;

    public ADTList() {
        this.list = new ArrayList<T>();
    }

    public ADTList(List<T> list) {
        this.list = list;
    }

    @Override
    public void add(T v) {
        this.list.add(v);
    }

    @Override
    public T pop() throws EmptyListException {
        if (this.list.size() == 0)
            throw new EmptyListException("List is currently empty!\n");

        T elem = this.list.get(this.list.size() - 1);
        this.list.remove(this.list.size() - 1);
        return elem;
    }

    public T get(int index) throws InvalidListIndexException {
        if (index >= this.size())
            throw new InvalidListIndexException("List index is out of range!\n");
        return this.list.get(index);
    }

    @Override
    public List<T> getList() {
        return this.list;
    }

    public T getFirstElement() throws EmptyListException {
        if (this.list.size() == 0)
            throw new EmptyListException("List is currently empty!\n");
        return this.list.get(0);
    }

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    public int size() {
        return this.list.size();
    }

    @Override
    public IList<T> copy() {
        return new ADTList<T>(new ArrayList<>(this.list));
    }

    @Override
    public void clear(){
        this.list.clear();
    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        for (T el : this.list) {
            list.append(el.toString()).append(" ");
        }
        return list.toString();
    }
}

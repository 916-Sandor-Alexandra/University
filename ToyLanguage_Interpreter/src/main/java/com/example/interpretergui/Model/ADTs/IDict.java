package com.example.interpretergui.Model.ADTs;

import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;

import java.util.Map;
import java.util.Set;

public interface IDict<T1,T2>{
    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2) throws NonExistentKeyException;
    T2 lookup(T1 id) throws NonExistentKeyException;
    void remove(T1 id) throws NonExistentKeyException;
    Map<T1, T2> getDictionary();
    Set<T1> getKeySet();
    boolean isDefined(T1 id);
    String toString();
    IDict<T1, T2> copy();
}

package com.example.interpretergui.Model.ADTs;

import com.example.interpretergui.Exceptions.ADT_Exceptions.HeapException;
import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

import java.util.Map;

public interface IHeap {
    RefValue allocate(Value value);
    void update(int address, Value new_value);
    Value getValueByAddress(int address) throws NonExistentKeyException;
    Map<Integer, Value> getMappings();
    public void setMappings(Map<Integer, Value> mappings) throws HeapException;
    int size();
    boolean empty();
    IHeap copy() throws HeapException;
}

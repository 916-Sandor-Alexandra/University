package com.example.interpretergui.Model.ADTs;

import com.example.interpretergui.Exceptions.ADT_Exceptions.HeapException;
import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap{
    Map<Integer, Value> mappings;
    Integer freeLocation;

    public Heap() {
        mappings = new HashMap<>();
        freeLocation = 1;
    }

    public Heap(Map<Integer, Value> dictionary, Integer freeLocation) throws HeapException {
        if (freeLocation < 1)
            throw new HeapException("Invalid heap location value!\n");
        if (!isHeap(dictionary))
            throw new HeapException("Invalid key values in dictionary!\n");

        this.mappings = dictionary;
        this.freeLocation = freeLocation;
    }

    public Map<Integer, Value> getMappings() {
        return mappings;
    }

    public void setMappings(Map<Integer, Value> mappings) throws HeapException {
        if (!isHeap(mappings))
            throw new HeapException("Invalid key values in dictionary!\n");
        this.mappings = mappings;
    }

    public Integer getFreeLocation() {
        return freeLocation;
    }

    public boolean isHeap(Map<Integer, Value> dictionary) {
        for (Integer address : dictionary.keySet()) {
            if (address < 1)
                return false;
        }
        return true;
    }

    @Override
    public RefValue allocate(Value value) {
        mappings.put(freeLocation, value);
        freeLocation++;
        return new RefValue(freeLocation - 1, value.getType());
    }

    @Override
    public void update(int address, Value new_value) {
        mappings.put(address, new_value);
    }

    @Override
    public Value getValueByAddress(int address) throws NonExistentKeyException {
        if (!mappings.containsKey(address)){
            throw new NonExistentKeyException("No value was found in heap at given address!\n");
        }

        return mappings.get(address);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<Integer, Value> e : this.mappings.entrySet()) {
            string.append(e.getKey()).append("->").append(e.getValue()).append("\n");
        }
        return string.toString();
    }

    @Override
    public int size() {
        return getMappings().size();
    }

    @Override
    public boolean empty() {
        return getMappings().size() == 0;
    }

    @Override
    public IHeap copy() throws HeapException {
        return new Heap(mappings, freeLocation);
    }
}

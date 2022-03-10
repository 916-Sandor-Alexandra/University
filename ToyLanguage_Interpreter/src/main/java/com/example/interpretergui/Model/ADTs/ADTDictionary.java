package com.example.interpretergui.Model.ADTs;
import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ADTDictionary<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public ADTDictionary() {
        this.dictionary = new HashMap<T1,T2>();
    }

    public ADTDictionary(Map<T1, T2> dictionary) {
        this.dictionary = dictionary;
    }

    public Map<T1, T2> getDictionary() {
        return dictionary;
    }

    @Override
    public void add(T1 v1, T2 v2) {
        this.dictionary.put(v1, v2);
    }

    @Override
    public void update(T1 v1, T2 v2) throws NonExistentKeyException {
        if (!this.dictionary.containsKey(v1)) {
            throw new NonExistentKeyException("Key " + v1 + " was not found!\n");
        }
        this.dictionary.put(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) throws NonExistentKeyException {
        if (!this.dictionary.containsKey(id)) {
            throw new NonExistentKeyException("Key " + id + " was not found!\n");
        }
        return this.dictionary.get(id);
    }

    @Override
    public void remove(T1 id) throws NonExistentKeyException {
        if (!this.dictionary.containsKey(id)) {
            throw new NonExistentKeyException("Key " + id + " was not found!\n");
        }
        this.dictionary.remove(id);
    }

    public Set<T1> getKeySet() {
        return this.dictionary.keySet();
    }

    @Override
    public boolean isDefined(T1 id) {
        return this.dictionary.containsKey(id);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<T1, T2> e : this.dictionary.entrySet()) {
            T1 key = e.getKey();
            T2 value = e.getValue();
            string.append(key.toString()).append("->").append(value.toString()).append("\n");
        }
        return string.toString();
    }

    @Override
    public IDict<T1, T2> copy() {
        return new ADTDictionary<T1,T2>(new HashMap<>(this.dictionary));
    }

}

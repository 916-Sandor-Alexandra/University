package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.Value;

public class BoolType implements Type {
    @Override
    public Value defaultValue() {
        return new BoolValue();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BoolType;
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString(){
        return "bool";
    }
}

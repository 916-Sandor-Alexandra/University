package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.Value;
import com.example.interpretergui.Model.Values.IntValue;

public class IntType implements Type {
    @Override
    public Value defaultValue() {
        return new IntValue();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IntType;
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public String toString(){
        return "int";
    }
}

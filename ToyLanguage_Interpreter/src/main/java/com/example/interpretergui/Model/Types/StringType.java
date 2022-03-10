package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

public class StringType implements Type{
    @Override
    public Value defaultValue() {
        return new StringValue();
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }
}

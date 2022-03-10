package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

import java.util.Objects;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RefType)
            return ((RefType) o).inner.equals(inner);
        return false;
    }

    @Override
    public String toString() {
        return String.format("Ref %s", inner.toString());
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
}

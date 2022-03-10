package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Types.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof RefValue))
            return false;

        RefValue ref_value = (RefValue) o;
        return ref_value.address == this.address;
    }

    @Override
    public Type getType() {
        return new RefType(this.locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(this.address, this.locationType.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", address, locationType);
    }
}

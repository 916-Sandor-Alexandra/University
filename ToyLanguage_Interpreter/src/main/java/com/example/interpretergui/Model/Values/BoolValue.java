package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.Type;

public class BoolValue implements Value {

    boolean value;

    public BoolValue(){
        this.value = false;
    }

    public BoolValue(boolean val){
        this.value = val;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof BoolValue))
            return false;

        BoolValue o_value = (BoolValue) o;
        return o_value.value == this.value;
    }

    public boolean getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return this.value ? "true" : "false";
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(this.value);
    }
}

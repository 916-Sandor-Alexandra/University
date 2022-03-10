package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Types.IntType;

public class IntValue implements Value {
    int value;

    public IntValue(){
        this.value = 0;
    }

    public IntValue(int i){
        this.value = i;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof IntValue))
            return false;

        IntValue o_value = (IntValue) o;
        return o_value.value == this.value;
    }

    @Override
    public String toString(){
        return Integer.toString(this.value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }
}

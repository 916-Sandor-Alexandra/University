package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.StringType;
import com.example.interpretergui.Model.Types.Type;

public class StringValue implements Value{
    String value;

    public StringValue() {
        this.value = "";
    }

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        return String.format("\"%s\"", this.value);
    }

    @Override
    public Value deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof StringValue))
            return false;

        StringValue strVal = (StringValue)o;
        return strVal.getValue().equals(this.value);
    }
}

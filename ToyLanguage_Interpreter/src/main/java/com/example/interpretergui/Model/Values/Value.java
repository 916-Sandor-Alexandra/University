package com.example.interpretergui.Model.Values;

import com.example.interpretergui.Model.Types.Type;

public interface Value {
    Type getType();
    Value deepCopy();
    boolean equals(Object obj);
}

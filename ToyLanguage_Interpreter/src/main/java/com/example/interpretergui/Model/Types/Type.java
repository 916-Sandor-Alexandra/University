package com.example.interpretergui.Model.Types;

import com.example.interpretergui.Model.Values.Value;

public interface Type {
    Value defaultValue();
    Type deepCopy();
    boolean equals(Object o);
}

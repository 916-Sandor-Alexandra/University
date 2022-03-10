package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidConstantException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.Value;

public class ValueExp implements Expression{
    Value constant;

    public ValueExp(Value constant) {
        this.constant = constant;
    }

    public Value eval(IDict<String, Value> table, IHeap heap) throws InvalidConstantException {
        Value myVal = constant.getType().defaultValue();
        return (myVal.getClass()).cast(constant);
    }

    public String toString() {
        return constant.toString();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExp(this.constant.deepCopy());
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) {
        return constant.getType();
    }
}

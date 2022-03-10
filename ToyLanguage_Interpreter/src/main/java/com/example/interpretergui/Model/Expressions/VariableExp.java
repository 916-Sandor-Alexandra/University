package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.Value;

public class VariableExp implements Expression {
    String variable_name;

    public VariableExp(String name){
        this.variable_name = name;
    }

    @Override
    public Value eval(IDict<String, Value> table, IHeap heap) throws NonExistentKeyException {
        return table.lookup(this.variable_name);
    }

    public String toString() {
        return this.variable_name;}

    public Type typeCheck(IDict<String,Type> typeEnv) throws Exception{
        return typeEnv.lookup(variable_name);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExp(this.variable_name);
    }

}

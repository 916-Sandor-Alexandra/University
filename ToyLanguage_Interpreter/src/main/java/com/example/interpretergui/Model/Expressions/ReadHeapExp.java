package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.DivisionByZeroException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.ExpressionTypeCheckException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidConstantException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidOperandException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

public class ReadHeapExp implements Expression{
    Expression expression;

    public ReadHeapExp(Expression expression) {
        this.expression = expression;
    }


    @Override
    public Value eval(IDict<String, Value> table, IHeap heap) throws Exception {
        Value ex_value = this.expression.eval(table, heap);
        RefValue ref_val = (RefValue) ex_value;
        int address= ref_val.getAddress();
        return heap.getValueByAddress(address);
    }

    @Override
    public String toString() {
        return String.format("readH(%s)", expression);
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExp(expression.deepCopy());
    }

    @Override
    public Type typeCheck(IDict<String,Type> typeEnv) throws Exception {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            return ((RefType) type).getInner();
        }
        else throw new ExpressionTypeCheckException("The ReadHeap argument is not a Ref Type!");
    }
}

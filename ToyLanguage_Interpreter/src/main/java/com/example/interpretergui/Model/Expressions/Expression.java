package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.DivisionByZeroException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidConstantException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidOperandException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.Value;

public interface Expression {
    Value eval(IDict<String, Value> table, IHeap heap) throws DivisionByZeroException, InvalidOperandException, InvalidConstantException, NonExistentKeyException, MismatchedVariableTypeException, Exception;
    String toString();
    Expression deepCopy();
    Type typeCheck(IDict<String, Type> typeEnv) throws Exception;
}

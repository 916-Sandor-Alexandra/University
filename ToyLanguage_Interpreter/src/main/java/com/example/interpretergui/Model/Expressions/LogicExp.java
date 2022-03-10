package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Exceptions.Expr_Exceptions.ExpressionTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Expressions.Operator.LogicOperator;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.Value;

public class LogicExp implements Expression{
    Expression e1, e2;
    LogicOperator operator;

    public LogicExp(Expression e1, Expression e2, LogicOperator operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    public Value eval(IDict<String, Value> table, IHeap heap) throws Exception{
        Value val1, val2;
        val1 = e1.eval(table, heap);
        val2 = e2.eval(table, heap);
        return this.operator.calc((BoolValue) val1,(BoolValue) val2);
    }

    @Override
    public Expression deepCopy() {
        return new LogicExp(this.e1.deepCopy(), this.e2.deepCopy(), this.operator);
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type type1 = e1.typeCheck(typeEnv);
        Type type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())){
            if(type2.equals(new BoolType())){
                return new BoolType();
            }
            else throw new ExpressionTypeCheckException("Logic Expression: First operand isn't Type Bool!");
        }
        else throw new ExpressionTypeCheckException("Logic Expression: Second operand isn't Type Bool!");
    }

    public Expression getFirst() {
        return this.e1;
    }

    public Expression getSecond() {
        return this.e2;
    }

    public LogicOperator getOperator() {
        return this.operator;
    }
}

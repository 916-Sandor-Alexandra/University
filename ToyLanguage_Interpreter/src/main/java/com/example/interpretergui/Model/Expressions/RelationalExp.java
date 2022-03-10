package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.DivisionByZeroException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.ExpressionTypeCheckException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidConstantException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidOperandException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Expressions.Operator.RelationalOperator;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;

public class RelationalExp implements Expression{
    Expression e1, e2;
    RelationalOperator operator;

    public RelationalExp(Expression e1, Expression e2, RelationalOperator operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value eval(IDict<String, Value> table, IHeap heap) throws Exception {
        Value val1, val2;
        val1 = e1.eval(table, heap);
        val2 = e2.eval(table, heap);
        return operator.calc((IntValue)val1, (IntValue)val2);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", e1.toString(), operator.toString(), e2.toString());
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExp(this.e1.deepCopy(), this.e2.deepCopy(), this.operator);
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type type1 = e1.typeCheck(typeEnv);
        Type type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new BoolType();
            }
            else throw new ExpressionTypeCheckException("Relational Expression: First operand isn't Type Int!");
        }
        else throw new ExpressionTypeCheckException("Relational Expression: Second operand isn't Type Int!");
    }
}

package com.example.interpretergui.Model.Expressions;
import com.example.interpretergui.Exceptions.Expr_Exceptions.ExpressionTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Expressions.Operator.ArithOperator;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;


public class ArithmeticExp implements Expression {
    ArithOperator operator;
    Expression e1, e2;

    public ArithmeticExp(ArithOperator operator, Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value eval(IDict<String, Value> table, IHeap heap) throws Exception {
        Value val1, val2;
        IntValue result = null;
        val1= e1.eval(table, heap);
        val2 = e2.eval(table, heap);
        return this.operator.calc((IntValue) val1, (IntValue) val2);
    }

    public ArithOperator getOperation() {
        return this.operator;
    }

    public Expression getFirst() {
        return this.e1;
    }

    public Expression getSecond() {
        return this.e2;
    }

    public String toString() {
        return e1.toString() + " " + operator + " " + e2.toString();
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExp(this.operator, this.e1.deepCopy(), this.e2.deepCopy());
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type type1 = e1.typeCheck(typeEnv);
        Type type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new IntType();
            }
            else throw new ExpressionTypeCheckException("Arithmetic Expression: First operand isn't Type Int!");
        }
        else throw new ExpressionTypeCheckException("Arithmetic Expression: Second operand isn't Type Int!");
    }
}

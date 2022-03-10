package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.DivisionByZeroException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidConstantException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidOperandException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IList;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.Value;

public class PrintStatement implements Statement {
    Expression expression;

    public PrintStatement(Expression exp){
        this.expression = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IList<Value> output = state.getOutput();
        IDict<String, Value> table = state.getSymTable();
        output.add(expression.eval(table, state.getHeap()));
        state.setOutput(output);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + "); ";
    }

    @Override
    public IDict<String,Type> typeCheck(IDict<String, Type> typeEnv) throws Exception{
        expression.typeCheck(typeEnv);
        return typeEnv;
    }
}

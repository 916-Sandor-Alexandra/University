package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.StatementTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.StringType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;

public class CloseRFileStatement implements Statement{
    Expression expression;

    public CloseRFileStatement(Expression expr) {
        this.expression = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, Value> table = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value val = this.expression.eval(table, state.getHeap());
        StringValue filename = (StringValue) val;
        BufferedReader reader = fileTable.lookup(filename);
        reader.close();
        fileTable.remove(filename);
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public String toString() {
        return String.format("closeFile(%s); ", this.expression.toString());
    }

    @Override
    public Statement deepCopy() {
        return new CloseRFileStatement(this.expression.deepCopy());
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> typeEnv) throws Exception{
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new StringType()))
            return typeEnv;
        else
            throw new StatementTypeCheckException("CloseRFile Statement: Argument is not String Type!");
    }
}

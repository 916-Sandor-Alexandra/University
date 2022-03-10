package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.StatementTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Types.StringType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;

public class ReadFileStatement implements Statement{
    String variable_name;
    Expression expression;

    public ReadFileStatement(String variable_name, Expression expr) {
        this.variable_name = variable_name;
        this.expression = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, Value> table = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        if(table.isDefined(this.variable_name)) {
            Value varValue = table.lookup(this.variable_name);
            Value val = expression.eval(table, state.getHeap());
            StringValue fileName = (StringValue)val;
            BufferedReader reader = fileTable.lookup(fileName);
            String line = reader.readLine();
            IntValue number = line == null ? new IntValue(0) : new IntValue(Integer.parseInt(line));
            table.update(this.variable_name, number);
        }
        else {
            throw new NonExistentKeyException("Variable wasn't previously declared!");
        }

        state.setSymTable(table);
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public String toString() {
        return String.format("readFile(%s, %s); ", this.variable_name, this.expression.toString());
    }

    @Override
    public Statement deepCopy() {
        return new ReadFileStatement(this.variable_name, this.expression.deepCopy());
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type varType = typeEnv.lookup(variable_name);
        Type exprType = expression.typeCheck(typeEnv);
        if (varType.equals(new IntType())){
            if (exprType.equals(new StringType())){
                return typeEnv;
            }
            else throw new StatementTypeCheckException("ReadFile Statement: Expression type must be String Type!");
        }
        else throw new StatementTypeCheckException("ReadFile Statement: Variable type must be Int Type!");
    }
}

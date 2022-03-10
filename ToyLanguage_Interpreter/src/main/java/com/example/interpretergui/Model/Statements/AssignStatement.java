package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.ADT_Exceptions.*;
import com.example.interpretergui.Exceptions.Expr_Exceptions.*;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.*;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.Value;

public class AssignStatement implements Statement {
    String variable_name;
    Expression expression;

    public AssignStatement(String variable_name, Expression expression) {
        this.variable_name = variable_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, Value> table = state.getSymTable();
        if(table.isDefined(variable_name)) {
            Value val = expression.eval(table, state.getHeap());
            table.update(variable_name, val);
            state.setSymTable(table);
        }
        else throw new UndeclaredVariableException("Variable in use was not previously declared!\n");
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new AssignStatement(this.variable_name, this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return this.variable_name + " = " + this.expression.toString() + ";";
    }

    @Override
    public IDict<String,Type> typeCheck(IDict<String,Type> typeEnv) throws Exception{
        Type typeVar = typeEnv.lookup(variable_name);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new StatementTypeCheckException("Assignment Statement: Variable and expression type don't match!");
    }

}

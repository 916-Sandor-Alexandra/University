package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.Stmnt_Exceptions.DuplicateVariableException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Statements.Statement;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.Value;

public class VarDeclStatement implements Statement {
    String name;
    Type type;

    public VarDeclStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws DuplicateVariableException {
        IDict<String, Value> table = state.getSymTable();
        if (table.isDefined(name)) {
            throw new DuplicateVariableException("Variable was already defined!\n");
        }
        else {
            Value defaultValue = this.defaultValue();
            table.add(name, defaultValue);
        }
        state.setSymTable(table);
        return null;
    }

    public Value defaultValue() {
            return this.type.defaultValue();
    }

    @Override
    public Statement deepCopy() {
        return new VarDeclStatement(this.name, this.type.deepCopy());
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        typeEnv.add(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name + ";";
    }
}

package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.Type;

public class NoOperationStatement implements Statement {
    String nop;

    public NoOperationStatement(String nop) {
        this.nop = nop;
    }

    public PrgState execute(PrgState state){
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NoOperationStatement(this.nop);
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        return typeEnv;
    }

    @Override
    public String toString(){
        return nop + ";";
    }
}

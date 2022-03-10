package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.ADTs.ADTStack;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IStack;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.Type;

public class ForkStatement implements Statement {
    Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<Statement> forkStack = new ADTStack<>();
        forkStack.push(statement);
        return new PrgState(forkStack, state.getSymTable().copy(), state.getOutput(), statement, state.getFileTable(), state.getHeap());
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("fork(%s)", statement);
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception{
        return statement.typeCheck(typeEnv);
    }
}

package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.ADT_Exceptions.FullStackException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.StatementTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IStack;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.Type;

public class CompoundStatement implements Statement {
    Statement first;
    Statement snd;

    public CompoundStatement(Statement first, Statement snd) {
        this.first = first;
        this.snd = snd;
    }

    public String toString() {
        return first.toString() + " " + snd.toString() + "";
    }

    public PrgState execute(PrgState state) throws FullStackException {
        IStack<Statement> stack = state.getStack();
        stack.push(snd);
        stack.push(first);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(first.deepCopy(), snd.deepCopy());
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> typeEnv) throws Exception{
        return snd.typeCheck(first.typeCheck(typeEnv));
    }
}

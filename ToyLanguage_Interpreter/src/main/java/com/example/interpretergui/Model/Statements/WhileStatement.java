package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.Stmnt_Exceptions.StatementTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IStack;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.Value;

public class WhileStatement implements Statement{
    Expression expression;
    Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<Statement> exeStack = state.getStack().copy();
        Value expr_val = expression.eval(state.getSymTable(), state.getHeap());
        BoolValue condition = (BoolValue) expr_val;
        if (condition.getValue()) {
            exeStack.push(this);
            exeStack.push(this.statement);
        }
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public String toString() {
        return String.format("while(%s) { %s }", expression, statement);
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())){
            return statement.typeCheck(typeEnv);
        }
        throw new StatementTypeCheckException("While Statement: Expression must be Type Bool!");
    }
}

package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.ADT_Exceptions.FullStackException;
import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.DivisionByZeroException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidConstantException;
import com.example.interpretergui.Exceptions.Expr_Exceptions.InvalidOperandException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.InvalidExpressionException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.StatementTypeCheckException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.UndeclaredVariableException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IStack;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.Value;

public class IfStatement implements Statement {
    Expression expression;
    Statement then_statement;
    Statement else_statement;

    public IfStatement(Expression expression, Statement then_statement, Statement else_statement) {
        this.expression = expression;
        this.then_statement = then_statement;
        this.else_statement = else_statement;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<Statement> stack = state.getStack();
        IDict<String, Value> table = state.getSymTable();
        Value cond = this.expression.eval(table, state.getHeap());
        BoolValue cond_val = (BoolValue)cond;
        if (cond_val.getValue()){
            stack.push(then_statement);
        }
        else {
            stack.push(else_statement);
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(this.expression.deepCopy(), this.then_statement.deepCopy(), this.else_statement.deepCopy());
    }

    @Override
    public String toString() {
        return "if (" + expression.toString() +
                ") then " + then_statement.toString() +
                " else " + else_statement.toString();
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception{
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            then_statement.typeCheck(typeEnv.copy());
            else_statement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else
            throw new StatementTypeCheckException("If Statement: Conditional expression is not Type Bool!");
    }
}

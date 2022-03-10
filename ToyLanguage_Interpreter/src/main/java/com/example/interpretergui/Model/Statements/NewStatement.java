package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.ADT_Exceptions.NonExistentKeyException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.StatementTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

public class NewStatement implements Statement{
    String variable_name;
    Expression expression;

    public NewStatement(String variable_name, Expression expression) {
        this.variable_name = variable_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        if (!symTable.isDefined(variable_name)) {
            throw new NonExistentKeyException("Undefined variable name in usage!\n");
        }
        Value var_value = symTable.lookup(variable_name);
        RefValue var_val_ref = (RefValue) var_value;
        Value expr_value = this.expression.eval(symTable, heap);
        RefValue new_value = heap.allocate(expr_value);
        symTable.update(variable_name, new_value);
        state.setSymTable(symTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public String toString() {
        return String.format("new(%s, %s); ", variable_name, expression);
    }

    @Override
    public Statement deepCopy() {
        return new NewStatement(variable_name, expression.deepCopy());
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type varType = typeEnv.lookup(variable_name);
        Type expType = expression.typeCheck(typeEnv);
        if (varType instanceof RefType) {
            if (((RefType) varType).getInner().equals(expType)){
                return typeEnv;
            }
            else throw new StatementTypeCheckException("New Statement: Ref Inner Type and Expression type are different!");
        }
        else throw new StatementTypeCheckException("New Statement: Variable must be Ref Type!");
    }
}

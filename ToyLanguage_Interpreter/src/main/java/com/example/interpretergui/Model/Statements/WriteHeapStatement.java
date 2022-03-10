package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Exceptions.Stmnt_Exceptions.StatementTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

public class WriteHeapStatement implements Statement{
    String variable_name;
    Expression expression;

    public WriteHeapStatement(String variable_name, Expression expression) {
        this.variable_name = variable_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IHeap heap = state.getHeap();
        IDict<String, Value> symTable = state.getSymTable();
        Value var_value = symTable.lookup(variable_name);

        RefValue ref_val = (RefValue) var_value;
        Value val = heap.getValueByAddress(ref_val.getAddress());
        Value expr_val = expression.eval(symTable, heap);

        heap.update(ref_val.getAddress(), expr_val);
        state.setHeap(heap);
        return null;
    }

    @Override
    public String toString() {
        return String.format("writeH(%s, %s);", variable_name, expression);
    }

    @Override
    public Statement deepCopy() {
        return new WriteHeapStatement(variable_name, expression.deepCopy());
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type typeVar = typeEnv.lookup(variable_name);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar instanceof RefType) {
            if (((RefType)typeVar).getInner().equals(typeExp)){
                return typeEnv;
            }
            else throw new StatementTypeCheckException("WriteHeap Statement: Variable Inner Type and expression type must be the same!");
        }
        else throw new StatementTypeCheckException("WriteHeap Statement: Variable must be RefType!");
    }
}

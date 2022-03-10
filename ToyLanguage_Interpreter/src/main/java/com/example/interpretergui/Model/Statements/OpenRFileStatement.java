package com.example.interpretergui.Model.Statements;
import com.example.interpretergui.Exceptions.File_Exceptions.InvalidFilePathException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.MismatchedVariableTypeException;
import com.example.interpretergui.Exceptions.Stmnt_Exceptions.StatementTypeCheckException;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.Expressions.Expression;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.StringType;
import com.example.interpretergui.Model.Types.Type;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OpenRFileStatement implements Statement {
    Expression expression;

    public OpenRFileStatement(Expression expr) {
        this.expression = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, Value> symTable = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value val = this.expression.eval(symTable, state.getHeap());
        StringValue strVal = (StringValue) val;
        if(fileTable.isDefined(strVal)){
            throw new Exception("File is already open!\n");
        }
        String fileName = strVal.getValue();
        Path filePath = Paths.get(fileName);
        if(!Files.exists(filePath)) {
            throw new InvalidFilePathException("File does not exist!\n");
        }

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        fileTable.add(strVal, reader);
        state.setFileTable(fileTable);
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public String toString() {
        return String.format("openRFile(%s); ", this.expression.toString());
    }

    @Override
    public Statement deepCopy() {
        return new OpenRFileStatement(this.expression.deepCopy());
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws Exception {
        Type exprType = expression.typeCheck(typeEnv);
        if (exprType.equals(new StringType())){
            return typeEnv;
        }
        throw new StatementTypeCheckException("OpenRFile Statement: Argument type is not Type String!");
    }
}

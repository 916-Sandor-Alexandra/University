package com.example.interpretergui.Model.Statements;


import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Types.Type;

public interface Statement {
    PrgState execute(PrgState state) throws Exception;
    Statement deepCopy();
    IDict<String, Type> typeCheck(IDict<String,Type> typeEnv) throws Exception;
}

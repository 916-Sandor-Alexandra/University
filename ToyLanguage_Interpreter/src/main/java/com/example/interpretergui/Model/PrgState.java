package com.example.interpretergui.Model;
import com.example.interpretergui.Model.ADTs.IDict;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.ADTs.IList;
import com.example.interpretergui.Model.ADTs.IStack;
import com.example.interpretergui.Model.Statements.Statement;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;

public class PrgState {
    IStack<Statement> exeStack;
    IDict<String, Value> symTable;
    IList<Value> out;
    Statement originalProgram;
    IDict<StringValue, BufferedReader> fileTable;
    IHeap heap;
    private static int ID = 1;
    int myID;

    public PrgState(IStack<Statement> exeStack, IDict<String, Value> symTable, IList<Value> out, Statement originalProgram, IDict<StringValue, BufferedReader> fileTable, IHeap heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        this.myID = ID;
        incrementID();
    }

    public synchronized static int incrementID() {
        return ID++;
    }

    public int getID() {
        return myID;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws Exception {
        Statement currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public IStack<Statement> getStack() {
        return this.exeStack;
    }

    public IList<Value> getOutput() {
        return this.out;
    }

    public IDict<String, Value> getSymTable() {
        return this.symTable;
    }

    public Statement getOriginalProgram() {
        return this.originalProgram;
    }

    public IDict<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public IHeap getHeap() { return heap;}

    public void setExeStack(IStack<Statement> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(IDict<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOutput(IList<Value> output) {
        this.out = output;
    }

    public void setFileTable(IDict<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setHeap(IHeap heap) { this.heap = heap;}

    @Override
    public String toString() {
        return "ID: " + ID + "\n" +
                "ExeStack:\n" + this.exeStack +
                "\nSymTable:\n" + this.symTable +
                "\nOutput:\n" + this.out +
                "\nFiletable:\n" + this.fileTable +
                "\nHeap:\n" + this.heap;
    }

    public PrgState copy() {
        return new PrgState(exeStack.copy(), symTable.copy(), out.copy(), originalProgram, fileTable, heap);
    }
}
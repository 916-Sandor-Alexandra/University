package com.example.interpretergui.Repo;

import com.example.interpretergui.Exceptions.File_Exceptions.InvalidFilePathException;
import com.example.interpretergui.Model.ADTs.*;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Statements.Statement;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> programStates;
    private PrgState selectedProgram;
    private Path logFilePath;

    public Repository() {
        this.programStates = new ArrayList<PrgState>();
    }

    public Repository(PrgState selectedProgram, Path logFilePath) throws Exception {
        this.programStates = new ArrayList<PrgState>();
        this.programStates.add(selectedProgram);
        this.selectedProgram = selectedProgram;
        setLogFilePath(logFilePath.toString());
    }

    public Path getLogFilePath() {
        return this.logFilePath;
    }

    public void setLogFilePath(String file_path) throws Exception {
        Path filePath = Paths.get(file_path);
        if(!Files.exists(filePath)) {
            throw new InvalidFilePathException("Invalid path!\n");
        }
        this.logFilePath = filePath;
    }

    public List<PrgState> getPrgList() {
        return this.programStates;
    }

    public void setPrgList(List<PrgState> newList) {
        this.programStates = newList;
    }

    public boolean isEmpty() {
        return this.programStates.isEmpty();
    }

    @Override
    public String toString() {
        return this.selectedProgram.toString();
    }

    @Override
    public void logProgStateExec(PrgState state) throws Exception {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath.toString(), true)));

        IStack<Statement> exeStack = state.getStack().copy();
        IDict<String, Value> symTable = state.getSymTable().copy();
        IList<Value> output = state.getOutput().copy();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable().copy();

        // TEXT FILE STRUCTURE
        // -> ID
        logFile.println(String.format("ID: %s\n", Integer.toString(state.getID())));
        // -> exe stack
        logFile.println("EXESTACK:");
        while(!exeStack.isEmpty()) {
            logFile.println(exeStack.top().toString());
            exeStack.pop();
        }
        // -> sym table
        logFile.println("\nSYMTABLE:");
        logFile.println(symTable.toString());
        // -> output
        logFile.println("OUTPUT:");
        logFile.println(output.toString());
        // -> filename
        logFile.println("FILETABLE:");
        for (StringValue file: fileTable.getKeySet()) {
            Path filePath = Paths.get(file.getValue());
            logFile.println(filePath.getFileName() + "\n");
        }
        // -> heap
        logFile.println("\nHEAP:\n" + state.getHeap());
        logFile.println("--------------------------------------------------------------------------------\n");
        logFile.close();
    }

    @Override
    public void addPrg(PrgState newPrg) {
        this.programStates.add(newPrg);
    }
}

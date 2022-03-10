package com.example.interpretergui.Controller;
import com.example.interpretergui.Exceptions.ADT_Exceptions.*;
import com.example.interpretergui.Model.ADTs.Heap;
import com.example.interpretergui.Model.ADTs.IHeap;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.*;
import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;
import com.example.interpretergui.Repo.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private final Repository repository;
    ExecutorService executor;

    public Controller(Repository repository) {
        this.repository = repository;
    }

    public List<PrgState> getPrograms() {
        return this.repository.getPrgList();
    }

    public void addProgram(PrgState newPrg) {
        this.repository.addPrg(newPrg);
    }

    public void setLogFilePath(String path) throws Exception {
        this.repository.setLogFilePath(path);
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTable, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTable.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTable, Map<Integer,Value> heap){
        List<Integer> refInSym = heap.keySet().stream().filter(symTable::contains).collect(Collectors.toList());
        List<Integer> heapRefs = heap.entrySet().stream().filter(e -> refInSym.contains(e.getKey()))
                .filter(e -> (e.getValue().getType() instanceof RefType))
                .map(e -> (((RefValue) e.getValue()).getAddress())).collect(Collectors.toList());
        List<Integer> allAddresses = Stream.concat(refInSym.stream(), heapRefs.stream()).collect(Collectors.toList());

        return heap.entrySet().stream().filter(e -> allAddresses.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void startOneStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        repository.setPrgList(removeCompletedPrg(repository.getPrgList()));
    }


    public boolean prgCheck(List<PrgState> prgList){
        for(PrgState prg: prgList){
            if(!prg.getStack().isEmpty())
                return true;
        }
        return false;
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws Exception {
        if (!prgCheck(prgList))
            throw new EmptyStackException("The stack is currently empty!");
        //before the execution, print the PrgState List into the log file
        prgList=removeCompletedPrg(prgList);
        prgList.forEach(prg -> {
            try {
                repository.logProgStateExec(prg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());
        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                        . map(future -> { try { return future.get();}
                                catch(Exception e) {
                                    System.out.println(e.getMessage());
                                    return null;
                                }
                                }).filter(p -> p != null)
                                            .collect(Collectors.toList());
        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repository.logProgStateExec(prg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        //Save the current programs in the repository
        repository.setPrgList(prgList);

    }


    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repository.getPrgList());
        while(prgList.size() > 0) {
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }


    public void oneStepForAllPrgGUI(List<PrgState> prgList) throws Exception {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repository.logProgStateExec(prg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> { if(!p.getStack().isEmpty()) return p.oneStep();
                    return p;
                }))
                .collect(Collectors.toList());
        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                . map(future -> { try { return future.get();}
                catch(Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
                }).filter(p -> p != null)
                .collect(Collectors.toList());
        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repository.logProgStateExec(prg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        //Save the current programs in the repository
        repository.setPrgList(prgList);
    }

    public void allStepGUI() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList=repository.getPrgList();
        while(prgCheck(prgList)){
            oneStepForAllPrgGUI(prgList);
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    @Override
    public String toString() {
        return this.repository.getPrgList().get(0).getOriginalProgram().toString();
    }

    public boolean isEmpty() {
        return this.repository.isEmpty();
    }

}

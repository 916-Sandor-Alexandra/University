package com.example.interpretergui.Repo;
import com.example.interpretergui.Model.*;

import java.io.IOException;

public interface IRepository {
    boolean isEmpty();
    void addPrg(PrgState newPrg);
//    PrgState getCrtPrg() throws EmptyListException, InvalidListIndexException;
    void logProgStateExec(PrgState state) throws Exception;
    }

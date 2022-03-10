package com.example.interpretergui.Model.Commands;

import com.example.interpretergui.Controller.Controller;

public class RunCommand extends Command {
    Controller controller;

    public RunCommand(String key, String description, Controller cntrl) {
        super(key, description);
        this.controller = cntrl;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

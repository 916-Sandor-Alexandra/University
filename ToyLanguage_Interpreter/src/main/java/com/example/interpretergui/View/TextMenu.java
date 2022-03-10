package com.example.interpretergui.View;

import com.example.interpretergui.Model.Commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    private void printMenu() {
        System.out.println("Options:");
        for(Command command : this.commands.values()){
            String key = command.getKey();
            String desc = command.getDescription();
            String line = String.format("(%s) %s", key, desc);
            System.out.println(line);
        }
    }

    public void addCommand(Command command) {
        this.commands.put(command.getKey(), command);
    }

    public void show() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            this.printMenu();
            try {
                System.out.println("Enter choice(0-10):");
                String op = sc.nextLine();
                Command command = this.commands.get(op);
                if (op == null) {
                    System.out.println("Invalid option!");
                    sc.next();
                }
                else
                    command.execute();
            }
            catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
    }
}

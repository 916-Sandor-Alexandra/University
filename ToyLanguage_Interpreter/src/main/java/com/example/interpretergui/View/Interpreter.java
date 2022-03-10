package com.example.interpretergui.View;
import com.example.interpretergui.Controller.Controller;
import com.example.interpretergui.Model.ADTs.*;

import com.example.interpretergui.Model.Commands.ExitCommand;
import com.example.interpretergui.Model.Commands.RunCommand;
import com.example.interpretergui.Model.Expressions.*;
import com.example.interpretergui.Model.Expressions.Operator.ArithOperator;
import com.example.interpretergui.Model.Expressions.Operator.RelationalOperator;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Statements.*;
import com.example.interpretergui.Model.Types.*;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;
import com.example.interpretergui.Repo.Repository;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Paths;



public class Interpreter {
    public static Path newFilePath(String number) {
        String path;
        path = String.format("src/main/java/com/example/interpretergui/Files/logFile%s.txt", number);
        return Paths.get(path);
    }

    public static Controller createController(Statement statement, String number) throws Exception {
        statement.typeCheck(new ADTDictionary<String, Type>());
        IStack<Statement> exeStack = new ADTStack<Statement>();
        IList<Value> output = new ADTList<Value>();
        IDict<String, Value> symTable = new ADTDictionary<String, Value>();
        exeStack.push(statement);
        IDict<StringValue, BufferedReader> fileTable = new ADTDictionary<>();
        IHeap heap = new Heap();
        PrgState prog = new PrgState(exeStack, symTable, output, statement, fileTable, heap);
        Repository repo = new Repository(prog, newFilePath(number));
        return new Controller(repo);
    }

    public static void main(String[] args) throws Exception {
        // ex 1:  int v; v = 2; Print(v)
        Statement st1 = new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExp(new IntValue(2))),
                        new PrintStatement(new VariableExp("v"))));

        // ex 2: a=2+3*5; b=a+1; Print(b)
        Statement st2 = new CompoundStatement(new VarDeclStatement("a", new IntType()), new CompoundStatement(new VarDeclStatement("b", new IntType()),
                new CompoundStatement(new AssignStatement("a", new ArithmeticExp(ArithOperator.PLUS, new ValueExp(new IntValue(2)), new ArithmeticExp(ArithOperator.MULTIPLY,
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompoundStatement(
                        new AssignStatement("b", new ArithmeticExp(ArithOperator.PLUS, new VariableExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStatement(new VariableExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3); Print(v)
        Statement st3 = new CompoundStatement(new VarDeclStatement("a", new BoolType()), new CompoundStatement(new VarDeclStatement("v",
                new IntType()), new CompoundStatement(new AssignStatement("a", new ValueExp(new BoolValue(true))),
                new CompoundStatement(new IfStatement(new VariableExp("a"), new AssignStatement("v", new ValueExp(new IntValue(2))),
                        new AssignStatement("v", new ValueExp(new IntValue(3)))), new PrintStatement(new
                        VariableExp("v"))))));

        // ex 4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc) closeRFile(varf)
        Statement st4 = new CompoundStatement(new VarDeclStatement("varf", new StringType()), new CompoundStatement(new AssignStatement("varf",
                new ValueExp(new StringValue("src/main/java/com/example/interpretergui/Files/test.in"))), new CompoundStatement(new OpenRFileStatement(new VariableExp("varf")),
                new CompoundStatement(new VarDeclStatement("varc", new IntType()), new CompoundStatement(new ReadFileStatement("varc",
                        new VariableExp("varf")), new CompoundStatement(new PrintStatement(new VariableExp("varc")),
                        new CompoundStatement((new ReadFileStatement("varc", new VariableExp("varf"))), new CompoundStatement(new
                                PrintStatement(new VariableExp("varc")), new CloseRFileStatement(new VariableExp("varf"))))))))));

        // ex 5: int a; a = 10; int b; b = 15; if (a > b) then print("a is greater than b.") else print("a is less than b.")
        Statement st5 = new CompoundStatement(new VarDeclStatement("a", new IntType()), new CompoundStatement(new AssignStatement("a",
                new ValueExp(new IntValue(10))), new CompoundStatement(new VarDeclStatement("b", new IntType()), new CompoundStatement(new
                AssignStatement("b", new ValueExp(new IntValue(15))), new IfStatement(new RelationalExp(new VariableExp("a"), new
                VariableExp("b"), RelationalOperator.GREATER), new PrintStatement(new ValueExp(new StringValue("a is greater than b"))), new
                PrintStatement(new ValueExp(new StringValue("a is less than b"))))))));

        // ex 6: Ref int v; new(v,20); Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        Statement st6 = new CompoundStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompoundStatement(new NewStatement("v",
                new ValueExp(new IntValue(20))), new CompoundStatement(new VarDeclStatement("a", new
                RefType(new RefType(new IntType()))), new CompoundStatement(new NewStatement("a", new VariableExp("v")),
                new CompoundStatement(new PrintStatement(new ReadHeapExp(new VariableExp("v"))), new PrintStatement(new
                        ArithmeticExp(ArithOperator.PLUS, new ReadHeapExp(new ReadHeapExp(new VariableExp("a"))), new
                        ValueExp(new IntValue(5)))))))));

        // ex 7: Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
        Statement st7 = new CompoundStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompoundStatement(new NewStatement("v",
                new ValueExp(new IntValue(20))), new CompoundStatement(new PrintStatement(new ReadHeapExp(new VariableExp("v"))),
                new CompoundStatement(new WriteHeapStatement("v", new ValueExp(new IntValue(30))), new PrintStatement(
                        new ArithmeticExp(ArithOperator.PLUS, new ReadHeapExp(new VariableExp("v")), new ValueExp(new IntValue(5))))))));

        // ex 8: Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        Statement st8 = new CompoundStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompoundStatement(new NewStatement("v",
                new ValueExp(new IntValue(20))), new CompoundStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                new CompoundStatement(new NewStatement("a", new VariableExp("v")), new CompoundStatement(new
                        NewStatement("v", new ValueExp(new IntValue(30))), new PrintStatement(new ReadHeapExp(new ReadHeapExp
                        (new VariableExp("a")))))))));

        // ex 9: int v; v=4; (while (v>0) print(v);v=v-1); print(v)
        Statement st9 = new CompoundStatement(new VarDeclStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new
                ValueExp(new IntValue(4))), new CompoundStatement(new WhileStatement(new RelationalExp(new VariableExp("v"),
                new ValueExp(new IntValue(0)), RelationalOperator.GREATER), new AssignStatement("v",
                new ArithmeticExp(ArithOperator.MINUS, new VariableExp("v"), new ValueExp(new IntValue(1))))), new PrintStatement
                (new VariableExp("v")))));

        // ex 10: int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a)));
        Statement st10 = new CompoundStatement(new VarDeclStatement("v", new IntType()), new CompoundStatement(new VarDeclStatement("a", new
                RefType(new IntType())), new CompoundStatement(new NewStatement("a", new ValueExp(new IntValue(22))), new
                CompoundStatement(new ForkStatement(new WriteHeapStatement("a", new ValueExp(new IntValue(30)))), new
                CompoundStatement(new AssignStatement("v", new ValueExp(new IntValue(32))), new CompoundStatement(new
                PrintStatement(new VariableExp("v")),new PrintStatement(new ReadHeapExp(new VariableExp("a")))))))));

        try {
///           > FILE PATH READING
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Enter path to the log file1:");
//            String path = sc.nextLine();
//            String path;
//            Path filePath1 = Paths.get(path);

//            System.out.println("Enter path to the log file2:");
//            path = sc.nextLine();
//            Path filePath2 = Paths.get(path);

//            System.out.println("Enter path to the log file3:");
//            path = sc.nextLine();
//            Path filePath3 = Paths.get(path);

//            System.out.println("Enter path to the log file4:");
//            path = sc.nextLine();
//            Path filePath4 = Paths.get(path);

//            System.out.println("Enter path to the log file5:");
//            path = sc.nextLine();
//            Path filePath5 = Paths.get(path);

//            System.out.println("Enter path to the log file6:");
//            String path = sc.nextLine();
//            Path filePath6 = Paths.get(path);

//            System.out.println("Enter path to the log file7:");
//            String path = sc.nextLine();
//            Path filePath7 = Paths.get(path);

            Controller controller1 = createController(st1, "1");
            Controller controller2 = createController(st2, "2");
            Controller controller3 = createController(st3, "3");
            Controller controller4 = createController(st4, "4");
            Controller controller5 = createController(st5, "5");
            Controller controller6 = createController(st6, "6");
            Controller controller7 = createController(st7, "7");
            Controller controller8 = createController(st8, "8");
            Controller controller9 = createController(st9, "9");
            Controller controller10 = createController(st10, "10");

            TextMenu menu = new TextMenu();
            menu.addCommand(new RunCommand("1", st1.toString(), controller1));
            menu.addCommand(new RunCommand("2", st2.toString(), controller2));
            menu.addCommand(new RunCommand("3", st3.toString(), controller3));
            menu.addCommand(new RunCommand("4", st4.toString(), controller4));
            menu.addCommand(new RunCommand("5", st5.toString(), controller5));
            menu.addCommand(new RunCommand("6", st6.toString(), controller6));
            menu.addCommand(new RunCommand("7", st7.toString(), controller7));
            menu.addCommand(new RunCommand("8", st8.toString(), controller8));
            menu.addCommand(new RunCommand("9", st9.toString(), controller9));
            menu.addCommand(new RunCommand("10", st10.toString(), controller10));
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.show();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

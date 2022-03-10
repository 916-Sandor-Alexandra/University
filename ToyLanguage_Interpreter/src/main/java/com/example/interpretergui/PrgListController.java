package com.example.interpretergui;

import com.example.interpretergui.Controller.Controller;
import com.example.interpretergui.Model.ADTs.*;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrgListController implements Initializable {

    @FXML
    private ListView<Controller> PrgList;
    @FXML
    private Button RunButton;


    public List<Statement> programs() {
        List<Statement> list = new ArrayList<>();
        // ex 1:  int v; v = 2; Print(v)
        Statement st1 = new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExp(new IntValue(2))),
                        new PrintStatement(new VariableExp("v"))));
        list.add(st1);

        // ex 2: a=2+3*5; b=a+1; Print(b)
        Statement st2 = new CompoundStatement(new VarDeclStatement("a", new IntType()), new CompoundStatement(new VarDeclStatement("b", new IntType()),
                new CompoundStatement(new AssignStatement("a", new ArithmeticExp(ArithOperator.PLUS, new ValueExp(new IntValue(2)), new ArithmeticExp(ArithOperator.MULTIPLY,
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompoundStatement(
                        new AssignStatement("b", new ArithmeticExp(ArithOperator.PLUS, new VariableExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStatement(new VariableExp("b"))))));
        list.add(st2);

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3); Print(v)
        Statement st3 = new CompoundStatement(new VarDeclStatement("a", new BoolType()), new CompoundStatement(new VarDeclStatement("v",
                new IntType()), new CompoundStatement(new AssignStatement("a", new ValueExp(new BoolValue(true))),
                new CompoundStatement(new IfStatement(new VariableExp("a"), new AssignStatement("v", new ValueExp(new IntValue(2))),
                        new AssignStatement("v", new ValueExp(new IntValue(3)))), new PrintStatement(new
                        VariableExp("v"))))));
        list.add(st3);

        // ex 4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc) closeRFile(varf)
        Statement st4 = new CompoundStatement(new VarDeclStatement("varf", new StringType()), new CompoundStatement(new AssignStatement("varf",
                new ValueExp(new StringValue("src/main/java/com/example/interpretergui/Files/test.in"))), new CompoundStatement(new OpenRFileStatement(new VariableExp("varf")),
                new CompoundStatement(new VarDeclStatement("varc", new IntType()), new CompoundStatement(new ReadFileStatement("varc",
                        new VariableExp("varf")), new CompoundStatement(new PrintStatement(new VariableExp("varc")),
                        new CompoundStatement((new ReadFileStatement("varc", new VariableExp("varf"))), new CompoundStatement(new
                                PrintStatement(new VariableExp("varc")), new CloseRFileStatement(new VariableExp("varf"))))))))));
        list.add(st4);

        // ex 5: int a; a = 10; int b; b = 15; if (a > b) then print("a is greater than b.") else print("a is less than b.")
        Statement st5 = new CompoundStatement(new VarDeclStatement("a", new IntType()), new CompoundStatement(new AssignStatement("a",
                new ValueExp(new IntValue(10))), new CompoundStatement(new VarDeclStatement("b", new IntType()), new CompoundStatement(new
                AssignStatement("b", new ValueExp(new IntValue(15))), new IfStatement(new RelationalExp(new VariableExp("a"), new
                VariableExp("b"), RelationalOperator.GREATER), new PrintStatement(new ValueExp(new StringValue("a is greater than b"))), new
                PrintStatement(new ValueExp(new StringValue("a is less than b"))))))));
        list.add(st5);

        // ex 6: Ref int v; new(v,20); Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        Statement st6 = new CompoundStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompoundStatement(new NewStatement("v",
                new ValueExp(new IntValue(20))), new CompoundStatement(new VarDeclStatement("a", new
                RefType(new RefType(new IntType()))), new CompoundStatement(new NewStatement("a", new VariableExp("v")),
                new CompoundStatement(new PrintStatement(new ReadHeapExp(new VariableExp("v"))), new PrintStatement(new
                        ArithmeticExp(ArithOperator.PLUS, new ReadHeapExp(new ReadHeapExp(new VariableExp("a"))), new
                        ValueExp(new IntValue(5)))))))));
        list.add(st6);

        // ex 7: Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
        Statement st7 = new CompoundStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompoundStatement(new NewStatement("v",
                new ValueExp(new IntValue(20))), new CompoundStatement(new PrintStatement(new ReadHeapExp(new VariableExp("v"))),
                new CompoundStatement(new WriteHeapStatement("v", new ValueExp(new IntValue(30))), new PrintStatement(
                        new ArithmeticExp(ArithOperator.PLUS, new ReadHeapExp(new VariableExp("v")), new ValueExp(new IntValue(5))))))));
        list.add(st7);

        // ex 8: Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        Statement st8 = new CompoundStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompoundStatement(new NewStatement("v",
                new ValueExp(new IntValue(20))), new CompoundStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                new CompoundStatement(new NewStatement("a", new VariableExp("v")), new CompoundStatement(new
                        NewStatement("v", new ValueExp(new IntValue(30))), new PrintStatement(new ReadHeapExp(new ReadHeapExp
                        (new VariableExp("a")))))))));
        list.add(st8);

        // ex 9: int v; v=4; (while (v>0) print(v);v=v-1); print(v)
        Statement st9 = new CompoundStatement(new VarDeclStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new
                ValueExp(new IntValue(4))), new CompoundStatement(new WhileStatement(new RelationalExp(new VariableExp("v"),
                new ValueExp(new IntValue(0)), RelationalOperator.GREATER), new AssignStatement("v",
                new ArithmeticExp(ArithOperator.MINUS, new VariableExp("v"), new ValueExp(new IntValue(1))))), new PrintStatement
                (new VariableExp("v")))));
        list.add(st9);

        // ex 10: int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a)));
        Statement st10 = new CompoundStatement(new VarDeclStatement("v", new IntType()), new CompoundStatement(new VarDeclStatement("a", new
                RefType(new IntType())), new CompoundStatement(new NewStatement("a", new ValueExp(new IntValue(22))), new
                CompoundStatement(new ForkStatement(new WriteHeapStatement("a", new ValueExp(new IntValue(30)))), new
                CompoundStatement(new AssignStatement("v", new ValueExp(new IntValue(32))), new CompoundStatement(new
                PrintStatement(new VariableExp("v")),new PrintStatement(new ReadHeapExp(new VariableExp("a")))))))));
        list.add(st10);
        return list;
    }

    public Path newFilePath(String number) {
        String path;
        path = String.format("src/main/java/com/example/interpretergui/Files/logFile%s.txt", number);
        return Paths.get(path);
    }

    public Controller createController(Statement statement, String number) throws Exception {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Controller> myData = FXCollections.observableArrayList();
        List<Statement> prgList = programs();
        for (int idx = 0; idx < prgList.size(); idx++){
            Controller cntr = null;
            try {
                cntr = createController(prgList.get(idx), Integer.toString(idx + 1));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            myData.add(cntr);
        }
        PrgList.setItems(myData);
        PrgList.getSelectionModel().selectFirst();
        RunButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == PrgStateController.class) {
                        return new PrgStateController(PrgList.getSelectionModel().getSelectedItem());
                    } else {
                        try {
                            return type.newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not create controller for "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("program-view.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    programStage.setTitle("Toy Language - Program running");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}

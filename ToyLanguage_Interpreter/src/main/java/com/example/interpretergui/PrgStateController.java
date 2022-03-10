package com.example.interpretergui;

import com.example.interpretergui.Controller.Controller;
import com.example.interpretergui.Model.PrgState;
import com.example.interpretergui.Model.Statements.Statement;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PrgStateController implements Initializable {

    @FXML
    private ListView<String> ExeStack;
    @FXML
    private ListView<String> FileTable;
    @FXML
    private TableView<HashMap.Entry<Integer, Value>> HeapTable;
    @FXML
    private TableColumn<HashMap.Entry<Integer, Value>, String> HeapTableAddress;
    @FXML
    private TableColumn<HashMap.Entry<Integer,Value>, String> HeapTableValue;
    @FXML
    private ListView<String> Output;
    @FXML
    private Label PrgCounter;
    @FXML
    private Label OrgProgram;
    @FXML
    private ListView<String> PrgStateIds;
    @FXML
    private Button AllStep;
    @FXML
    private Button OneStep;
    @FXML
    private TableView<HashMap.Entry<String, Value>> SymTable;
    @FXML
    private TableColumn<HashMap.Entry<String, Value>, String> SymTableValue;
    @FXML
    private TableColumn<HashMap.Entry<String, Value>, String> SymTableVar;

    Controller controller;

    public PrgStateController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialRun();
        try {
            controller.startOneStep();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrgStateIds.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSymTableAndExeStack();
            }
        });
        AllStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                try {
                    controller.allStepGUI();
                } catch (Exception e1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Toy Language - Current program finished");
                    alert.setHeaderText(null);
                    alert.setContentText("Program successfully finished!");
                    Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    Stage stage = (Stage) HeapTable.getScene().getWindow();
                    stage.close();
                }
                updateUIComponents();
            }
        });
        OneStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                try {
                        controller.oneStepForAllPrg(controller.getPrograms());
                } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Toy Language - Current program finished");
                        alert.setHeaderText(null);
                        alert.setContentText("Program successfully finished!");
                        Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                        confirm.setDefaultButton(false);
                        confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                        alert.showAndWait();
                        Stage stage = (Stage) HeapTable.getScene().getWindow();
                        stage.close();
                }
                updateUIComponents();
            }
        });
    }

    public void initialRun() {
        OrgProgram.setText("Original Program: "+ this.controller.getPrograms().get(0).getOriginalProgram());
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        PrgStateIds.getSelectionModel().selectFirst();
        setSymTableAndExeStack();
    }

    public void updateUIComponents() {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        if(PrgStateIds.getSelectionModel().getSelectedItem() == null) {
            PrgStateIds.getSelectionModel().selectFirst();
        }
        setSymTableAndExeStack();
    }

    public void setNumberLabel() {
        PrgCounter.setText("Current number of Program States:" + controller.getPrograms().size());
    }

    public void setHeapTable() {
        ObservableList<HashMap.Entry<Integer, Value>> heapTableList = FXCollections.observableArrayList();
        HeapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        HeapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        heapTableList.addAll(controller.getPrograms().get(0).getHeap().getMappings().entrySet());
        HeapTable.setItems(heapTableList);
        HeapTable.refresh();
    }

    public void setOutList() {
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        for(Value e : controller.getPrograms().get(0).getOutput().getList()) {
            outObservableList.add(e.toString());
        }
        Output.setItems(outObservableList);
        Output.refresh();
    }

    public void setFileTable() {
        ObservableList<String> fileTableList = FXCollections.observableArrayList();
        for(StringValue file : controller.getPrograms().get(0).getFileTable().getKeySet()){
            fileTableList.add(file.getValue());
        }
        FileTable.setItems(fileTableList);
    }

    public void setPrgStateList() {
        ObservableList<String> prgStateIdList = FXCollections.observableArrayList();
        for(PrgState e : controller.getPrograms()) {
            prgStateIdList.add(Integer.toString(e.getID()));
        }
        PrgStateIds.setItems(prgStateIdList);
        PrgStateIds.refresh();
    }

    public void setSymTableAndExeStack() {
        ObservableList<HashMap.Entry<String, Value>> symTableList = FXCollections.observableArrayList();
        ObservableList<String> exeStackItemsList = FXCollections.observableArrayList();
        SymTableVar.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        SymTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        List<PrgState> allPrgs = controller.getPrograms();
        PrgState prgResult = null;
        for(PrgState e : allPrgs) {
            if(e.getID() == Integer.parseInt(PrgStateIds.getSelectionModel().getSelectedItem())) {
                prgResult = e;
                break;
            }
        }

        if(prgResult != null) {
            symTableList.addAll(prgResult.getSymTable().getDictionary().entrySet());
            for(Statement e : prgResult.getStack().getStack()) {
                exeStackItemsList.add(e.toString());
            }
            SymTable.setItems(symTableList);
            ExeStack.setItems(exeStackItemsList);
        }
        SymTable.refresh();
        ExeStack.refresh();
    }
}
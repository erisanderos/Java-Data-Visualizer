package com.javaFX;

//import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.sound.sampled.Line;
import java.io.File;
import java.util.Arrays;

public class GenerateGraph extends Application {

    ComboBox comboBox;
    private ArraySetup arraySetup = new ArraySetup();
    private String[][] sArray = arraySetup.getArray();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GraphPaneManager gpManager = new GraphPaneManager(this);

        StackPane root = MainUIInitialization(primaryStage, gpManager);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    private StackPane MainUIInitialization(Stage primaryStage, GraphPaneManager gpManager) {
        primaryStage.setTitle("Graph Generator");
        //TextArea textArea = new TextArea();
        Text t = new Text("Array Display");
        t.setUnderline(true);
        t.setTextAlignment(TextAlignment.LEFT);
        //Generate Graph Button
        Button btn = new Button();
        btn.setText("Generate Graph");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            //Stage secondaryStage = new Stage();

            @Override
            public void handle(ActionEvent event) {
                gpManager.GenerateGraph(event);
            }
        });

        //Find Excel Button Handler
        Button findExcelBtn = new Button();
        findExcelBtn.setText("Find Excel");
        findExcelBtn.setOnAction(new EventHandler<ActionEvent>() {
            //Stage secondaryStage = new Stage();
            @Override
            public void handle(ActionEvent event) {
                //gpManager.GenerateGraph(event);}
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Excel File: ");
                File selectedFile = fileChooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
                if(selectedFile != null){
                    try{
                        System.out.println(selectedFile.getAbsolutePath());
                        arraySetup.setArray(selectedFile.getAbsolutePath());
                        //textArea.setWrapText(true);
                        t.setText(Arrays.deepToString(arraySetup.setArray(selectedFile.getAbsolutePath())));
                    } catch (Exception e){
                        AlertHelper.showAlert(Alert.AlertType.WARNING, (Window) Window.getWindows(), "Null pointer warning", "" +
                                "No file was selected");
                    }
                }

            }
        });

        //Save Excel Button
        Button saveExcelBtn = new Button();
        saveExcelBtn.setText("Save Excel");
        saveExcelBtn.setOnAction(new EventHandler<ActionEvent>() {
            //Stage secondaryStage = new Stage();
            @Override
            public void handle(ActionEvent event) {
                //gpManager.GenerateGraph(event);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("src"));
                File selectedDirectory = fileChooser.showSaveDialog(((Node)event.getTarget()).getScene().getWindow());
                if(selectedDirectory != null){
                    try{
                        System.out.println(selectedDirectory.getAbsolutePath());
                        //String[][] sArray = arraySetup.getArray();
                        System.out.println(Arrays.deepToString(sArray));
                        arraySetup.sendArray(selectedDirectory.getAbsolutePath(), arraySetup.getArray());
                    } catch (Exception e){
                        AlertHelper.showAlert(Alert.AlertType.WARNING, (Window) Window.getWindows(), "Null pointer warning", "" +
                                "No file was selected");
                    }
                }
            }
        });

        Button clrScrnBtn = new Button();
        clrScrnBtn.setText("Clear");
        clrScrnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
             sArray = null;
             t.setText("Array Display");
            }
        });

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Line Chart",
                        "Pie Chart",
                        "Option 3"
                );
        comboBox = new ComboBox(options);

        GridPane grid = new GridPane();
        //t.wrappingWidthProperty().bind(grid.widthProperty());
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        //grid.add(new Label("To: "), 0, 0);
        //grid.add(new Label("Priority: "), 2, 0);
        grid.add(comboBox, 0, 0);
        grid.add(btn, 0, 1);
        grid.add(findExcelBtn, 0,2);
        grid.add(saveExcelBtn, 0, 3);
        grid.add(clrScrnBtn, 0, 4);
        grid.add(t, 5, 2);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                gpManager.CloseStage();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(grid);

        return root;
    }

    public ComboBox GetComboBox() {
        return comboBox;
    }
}

class GraphPaneManager {
    Stage secondaryStage;
    GenerateGraph mainSystem;
    public GraphPaneManager(GenerateGraph input) {
        secondaryStage = new Stage();
        mainSystem = input;
    }

    public void GenerateGraph(ActionEvent event) {

        switch((String)mainSystem.comboBox.getValue()) {
            case "Line Chart":
                LineChartStage LStage = new LineChartStage();
                LStage.start(secondaryStage);
                break;
            case "Pie Chart":
                PieChartStage PStage = new PieChartStage();
                PStage.start(secondaryStage);
            default:
                break;
        }

    }

    public void CloseStage() {
        if(secondaryStage != null) {
            secondaryStage.close();
        }
    }
}
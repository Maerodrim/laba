import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model_javafx.Dialog;
import model_javafx.ErrorWindows;
import model_javafx.MadeFunction;
import model_javafx.ModelFunction;
import model_javafx.TableWindows;

import java.io.IOException;


public class Main extends Application {

    private Label label;
    private TabulatedFunction function;
    private String fileName;

    @Override
    public void start(final Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        // Create menus
        Menu fileMenu = new Menu("File");
        Menu tableMenu = new Menu("Table");

        // Create MenuItems
        MenuItem newItem = new MenuItem("New Function");
        MenuItem openFileItem = new MenuItem("Load Function");
        MenuItem table = new MenuItem("Table");

        // Add menuItems to the Menus
        fileMenu.getItems().addAll(newItem, openFileItem);
        tableMenu.getItems().addAll(table);
        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, tableMenu);

        newItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MadeFunction madeFunction = new MadeFunction();
                madeFunction.newFunction(primaryStage);
            }
        });

        openFileItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    model_javafx.Dialog dialog = new Dialog();
                    MadeFunction madeFunction = new MadeFunction();
                    ModelFunction modelFunction = new ModelFunction(madeFunction.loadFunctionAs(dialog.showInputTextDialog()));
                    TableWindows tableFunction = new TableWindows();
                    tableFunction.table(primaryStage, modelFunction);
                } catch (Exception e) {
                    ErrorWindows errorWindows = new ErrorWindows();
                    errorWindows.showError(e);
                }
            }
        });

        table.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    ModelFunction modelFunction = new ModelFunction(new MadeFunction().loadFunction());
                    TableWindows madeFunction = new TableWindows();
                    madeFunction.table(primaryStage, modelFunction);
                } catch (Exception e) {
                    ErrorWindows errorWindows = new ErrorWindows();
                    errorWindows.showError(e);
                }
            }
        });

        BorderPane root2 = new BorderPane();
        root2.setTop(menuBar);
        StackPane root = new StackPane();
        root.getChildren().addAll(root2);

        Scene scene = new Scene(root, 450, 250);

        primaryStage.setTitle("Main");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

}

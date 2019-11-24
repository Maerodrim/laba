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
import model_javafx.MadeFunction;
import model_javafx.TableWindows;


public class Main extends Application {

    private Label label;
    private  TabulatedFunction function;
    private  String fileName;

    @Override
    public void start(final Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        // Create menus
        Menu fileMenu = new Menu("File");
        Menu tableMenu = new Menu("Table");

        // Create MenuItems
        MenuItem newItem = new MenuItem("newFunction");
        MenuItem openFileItem = new MenuItem("loadFunction");
        MenuItem table = new MenuItem("table");

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
        table.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                TableWindows madeFunction = new TableWindows ();
                madeFunction.table(primaryStage);
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

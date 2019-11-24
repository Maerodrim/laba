package model_javafx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TableWindows {
    public void table(Stage stage) {

        TableView<ModelFunction> table = new TableView<ModelFunction>();

        // Create column X .
        TableColumn<ModelFunction, Double> x //
                = new TableColumn<ModelFunction, Double>("x");

        // Create column Y .
        TableColumn<ModelFunction, Double> y//
                = new TableColumn<ModelFunction, Double>("y");
        x.setCellValueFactory(new PropertyValueFactory<>("x"));
        y.setCellValueFactory(new PropertyValueFactory<>("x"));


        table.getColumns().addAll(x,y);

        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(table);

        stage.setTitle("TableView (AAAAAAAAAAAAAA)");

        Scene scene = new Scene(root, 450, 300);
        stage.setScene(scene);
        stage.show();
    }
}

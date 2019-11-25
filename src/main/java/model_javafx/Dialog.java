package model_javafx;

import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Dialog {
    private Label label;

    public String showInputTextDialog() {

        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle("Dialog Windows");
        dialog.setHeaderText("Enter your parametr:");
        dialog.setContentText("Text:");

        Optional<String> result = dialog.showAndWait();


         return  result.get();

    }
    public String showInputDoubleDialog() {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Dialog Windows");
        dialog.setHeaderText("Enter your base Log:");
        dialog.setContentText("Value:");

        Optional<String> result = dialog.showAndWait();


        return  result.get();

    }
}

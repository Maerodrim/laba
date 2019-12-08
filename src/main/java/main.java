import functions.Function;
import functions.Functions;
import functions.TabulatedFunction;
import functions.basic.Exp;
import functions.basic.Log;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model_javafx.Dialog;
import model_javafx.ErrorWindows;
import model_javafx.MadeFunction;
import model_javafx.ModelFunction;
import model_javafx.TableWindows;
import threads.*;

import java.io.IOException;
import java.io.InputStream;

public class main  /*extends Application*/ {

    public static void simpleThreads() {
        Task t = new Task(100);
        Thread generator = new Thread(new SimpleGenerator(t));
        //  generator.setPriority(Thread.MAX_PRIORITY);
        generator.start();
        Thread integrator = new Thread(new SimpleIntegrator(t));
        //   integrator.setPriority(Thread.MIN_PRIORITY);
        integrator.start();
    }

    public static void complicatedThreads() throws InterruptedException {
        Task t = new Task(100);
        Semaphore semaphore = new Semaphore();
        Generator generator = new Generator(t, semaphore);
        Integrator integrator = new Integrator(t, semaphore);

        integrator.setPriority(10);

        generator.start();
        integrator.start();
        Thread.sleep(50);
        generator.interrupt();
        integrator.interrupt();

    }

    public static void nonThread() {
        Task t = new Task(100);
        for (int i = 0; i < t.getTasks(); i++) {
            t.func = new Log(1 + (Math.random() * 9));
            t.leftX = Math.random() * 100;
            t.rigthX = Math.random() * 100 + 100;
            t.step = Math.random();
            System.out.println("Source leftX = " + t.leftX + " rightX = " + t.rigthX + " step = " + t.step);
            double res = Functions.integrate(t.func, t.leftX, t.rigthX, t.step);
            System.out.println("Result leftX = " + t.leftX + " rightX = " + t.rigthX + " step = " + t.step + " integrate = " + res);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("1");
        Function exp = new Exp();
        double theoreticValue = Math.E - 1;
        double step = 0.00000005;
        System.out.println("Значение полученное при помощи функции " + Functions.integrate(exp, 0, 1, step));
        System.out.println("Теоретическое значение " + theoreticValue);
        System.out.println("Шаг = " + step + "\n");
        System.out.println("2");
        main.nonThread();
        System.out.println("3");
        main.simpleThreads();
        System.out.println("4");
        main.complicatedThreads();
    }
/*
    private Label label;
    private TabulatedFunction function;
    private String fileName;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        MenuBar menuBar = new MenuBar();
        Label label = new Label("");
        Label label2 = new Label("Здесь могла быть ваша реклама!!!");
        // Use the font method of the Font class
        label2.setFont(Font.font("Cambria", 32));
        label2.setTextFill(Color.web("#0076a3"));
        label2.setCenterShape(true);

        String url = "https://static.tildacdn.com/tild3837-3835-4737-a165-376136313332/_111.png";
        boolean backgroundLoading = true;

        // The image is being loaded in the background
        Image image = new Image(url, backgroundLoading);

        ImageView imageView = new ImageView(image);

        label.setGraphic(imageView);
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
                    TableWindows tableWindows = new TableWindows();
                    tableWindows.table(primaryStage, modelFunction);
                } catch (Exception e) {
                    ErrorWindows errorWindows = new ErrorWindows();
                    errorWindows.showError(e);
                }
            }
        });

        BorderPane root2 = new BorderPane();
        root2.setTop(menuBar);
        root2.setCenter(label);
        root2.setBottom(label2);
        StackPane root = new StackPane();
        root.getChildren().addAll(root2);

        Scene scene = new Scene(root, 700, 500);

        primaryStage.setTitle("Main");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
*/
}

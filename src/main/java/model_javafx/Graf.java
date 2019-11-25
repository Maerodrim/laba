package model_javafx;

import functions.Function;
import functions.FunctionPoint;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;
import functions.basic.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Graf {

    public void grafFunction(Stage stage, TabulatedFunction tabulatedFunction) {

        MenuItem table2 = new MenuItem("Table");
        table2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    ModelFunction modelFunction = new ModelFunction(new MadeFunction().loadFunction());
                    TableWindows madeFunction = new TableWindows();
                    madeFunction.table(stage, modelFunction);
                } catch (Exception e) {
                    ErrorWindows errorWindows = new ErrorWindows();
                    errorWindows.showError(e);
                }
            }
        });

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
        areaChart.setTitle("Function");

        areaChart.setLegendSide(Side.LEFT);

        // Series data of 2014
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("graf");

        for (int i = 0; i < tabulatedFunction.getPointCount(); i++) {
            series.getData().add(new XYChart.Data<Number, Number>(tabulatedFunction.getPointX(i), tabulatedFunction.getPointY(i)));
        }
        // Changing random data after every 1 second.
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (XYChart.Series<Number, Number> series : areaChart.getData()) {
                    for (XYChart.Data<Number, Number> data : series.getData()) {
                        Number yValue = data.getYValue();
                        Number randomValue = yValue.doubleValue() * (0.5 + Math.random());
                        data.setYValue(randomValue);
                    }
                }
            }
        }));
// Repeat indefinitely until stop() method is called.
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();


        stage.setTitle("AreaChart ");
        Scene scene = new Scene(areaChart, 400, 300);
        areaChart.getData().addAll(series);
        stage.setScene(scene);
        stage.show();
    }
}

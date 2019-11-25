package model_javafx;

import functions.*;
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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Graf {

    public void grafFunction(Stage stage, TabulatedFunction tabulatedFunction) {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
        areaChart.setTitle("Function");

        areaChart.setLegendSide(Side.LEFT);

        // Series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("graf");

        for (int i = 0; i < tabulatedFunction.getPointCount(); i++) {
            series.getData().add(new XYChart.Data<Number, Number>(tabulatedFunction.getPointX(i), tabulatedFunction.getPointY(i)));
        }
        stage.setTitle("AreaChart ");
        Scene scene = new Scene(areaChart, 400, 300);
        areaChart.getData().addAll(series);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

            @Override

            public void handle(WindowEvent event) {
                try {
                    TableWindows tableFunction = new TableWindows();
                    tableFunction.table(stage, new ModelFunction(tabulatedFunction));
                } catch (Exception e) {
                    ErrorWindows errorWindows = new  ErrorWindows();
                    errorWindows.showError(e);
                }
                event.consume();

            }

        });
        stage.show();
    }
}

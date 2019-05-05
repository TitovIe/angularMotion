package angularMotion.view;

import angularMotion.controller.Calculate;
import angularMotion.controller.Validate;
import angularMotion.model.MoveParams;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.naming.InitialContext;

public class Window extends Application {

    private final TextField x0 = new TextField();
    private final TextField y0 = new TextField();
    private final TextField Vx0 = new TextField();
    private final TextField Vy0 = new TextField();
    private final TextField time = new TextField();
    private final TextField frequency = new TextField();
    private final Text error = new Text("Wrong input data");
    private final Text maxX = new Text();
    private final Text maxY = new Text();
    private final Text movementTime = new Text();
    private final Text distance = new Text();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movement at the angle to the horizon");

        //используем BorderPane как скилет нашего окна
        BorderPane border = new BorderPane();
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(200);
        primaryStage.setScene(new Scene(border));
        border.setStyle("-fx-background-color:#fff;");

        //создаем поле для ввода начальных данных
        StackPane stack = new StackPane();
        Button btn = new Button("Start");
        btn.setMinWidth(100);
        stack.getChildren().addAll(addGridPane(), btn);
        stack.setAlignment(Pos.CENTER_RIGHT);
        StackPane.setMargin(btn, new Insets(0, 10, 0, 0));
        border.setBottom(stack);

        //создаем поле для вывода праметров движения
        border.setRight(addMovingParams());

        //создаем поля для вывода траектории
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        final LineChart<Number, Number> numberNumberLineChart = new LineChart<Number, Number>(x, y);
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Trajectory");
        border.setCenter(numberNumberLineChart);

        btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                try{
                    if(!allValid()){throw new RuntimeException();}
                    error.setVisible(false);
                    Calculate traj = new Calculate(new MoveParams(Double.parseDouble(x0.getText()), Double.parseDouble(y0.getText()),
                            Double.parseDouble(Vx0.getText()), Double.parseDouble(Vy0.getText())));
                    traj.printTrajectory(numberNumberLineChart, series, time, frequency);
                    traj.printParams(maxX, maxY, movementTime, distance, time, frequency);
                } catch(RuntimeException ex){
                    error.setVisible(true);
                }

            }
        });
        primaryStage.show();
    }

    private GridPane addGridPane(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(10,10,10,10));

        Label initialCoordinates = new Label("Initial Coordinates:");
        initialCoordinates.setMinSize(100,5);
        grid.add(initialCoordinates, 1, 0);

        Label x = new Label("X:");
        x.setMinSize(10,5);
        grid.add(x, 0, 1);

        Label y = new Label("Y:");
        grid.add(y, 0, 2);

        grid.add(x0, 1, 1);
        grid.add(y0, 1, 2);

        Label initialSpeeds = new Label("Initial Speeds:");
        initialSpeeds.setMinSize(75,5);
        grid.add(initialSpeeds, 3, 0);

        Label Vx = new Label("Vx:");
        Vx.setMinSize(20,5);
        grid.add(Vx, 2, 1);

        Label Vy = new Label("Vy:");
        grid.add(Vy, 2, 2);

        grid.add(Vx0, 3, 1);
        grid.add(Vy0, 3, 2);

        Label simulationTime = new Label("Simulation Time:");
        simulationTime.setMinSize(90,5);
        grid.add(simulationTime, 4, 0);

        grid.add(time, 4, 1);

        Label freq = new Label("Frequency (units in second):");
        freq.setMinSize(150,5);
        grid.add(freq, 5, 0);

        grid.add(frequency, 5, 1);

        error.setFill(Color.FIREBRICK);
        error.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        error.setVisible(false);
        grid.add(error, 7, 1);

        grid.setStyle("-fx-background-color:#34b3f7;");
        return grid;
    }

    private GridPane addMovingParams(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        Text title = new Text("Moving parameters:");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(title, 0, 0, 2, 1);

        Text highestPoint = new Text("Highest point:");
        grid.add(highestPoint, 0, 2);
        Text x = new Text("X:");
        grid.add(x, 0, 3);
        Text y = new Text("Y:");
        grid.add(y, 0, 4);
        grid.add(maxX, 1, 3);
        grid.add(maxY, 1, 4);

        Text movTime = new Text("Movement time:");
        grid.add(movTime, 0, 6);
        grid.add(movementTime, 1, 6);

        Text dist = new Text("Distance:");
        grid.add(dist, 0, 8);
        grid.add(distance, 1, 8);

        grid.setStyle("-fx-background-color:#45f7ad;");

        return grid;
    }

    private boolean allValid(){
        return Validate.isNumber(x0.getText()) && Validate.isNumber(y0.getText()) && Validate.isNumber(Vx0.getText())
                && Validate.isNumber(Vy0.getText()) && Validate.isNumber(time.getText()) && Validate.isNumber(frequency.getText());
    }
}

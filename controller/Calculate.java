package angularMotion.controller;

import angularMotion.model.MoveParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Calculate {
    //ускорение свободного падения
    private static final double g = 9.81;
    //начальные условия
    private final MoveParams initialConditions;

    public Calculate(MoveParams initialConditions) {
        this.initialConditions = initialConditions;
    }

    //calcTraj принимает на вход время и возвращет параметры тела в данный момент времени
    public MoveParams calcTraj(double time){
        MoveParams result = new MoveParams();
        result.setVx(initialConditions.getVx());
        result.setVy(initialConditions.getVy() - g * time);
        result.setX(initialConditions.getX() + initialConditions.getVx() * time);
        result.setY(initialConditions.getY() + initialConditions.getVy() * time - g * Math.pow(time, 2) / 2);
        return result;
    }

    public void printTrajectory(LineChart<Number, Number> numberNumberLineChart, XYChart.Series<Number, Number> series, TextField simT, TextField freq){
        ObservableList<XYChart.Data<Number, Number>> datas = FXCollections.observableArrayList();
        for(double i = 0; i < Double.parseDouble(simT.getText()); i += (1 / Double.parseDouble(freq.getText()))){
            MoveParams movementNow = this.calcTraj(i);
            if(movementNow.getY() < 0){break;}
            datas.add(new XYChart.Data<Number, Number>(movementNow.getX(), movementNow.getY()));
        }
        series.setData(datas);
        numberNumberLineChart.getData().add(series);
    }

    public void printParams(Text highX, Text highY, Text movTime, Text distance, TextField simT, TextField freq){
        double maxy = 0;
        double maxx = 0;
        double movT;
        movTime.setText(simT.getText());
        for(double i = 0; i < Double.parseDouble(simT.getText()); i += (1 / Double.parseDouble(freq.getText()))){
            MoveParams movementNow = this.calcTraj(i);
            movT = i;
            if(movementNow.getY() < 0){
                movTime.setText(String.valueOf(movT));
                break;
            }
            distance.setText(String.valueOf(movementNow.getX()));
            if(movementNow.getY() > maxy){
                maxy = movementNow.getY();
                maxx = movementNow.getX();
            }
        }
        highX.setText(String.valueOf(maxx));
        highY.setText(String.valueOf(maxy));
    }
}

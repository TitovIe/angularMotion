package angularMotion.model;

//класс MoveParams содержит в себе поля, которые являются параметрами плоского движения тела под углом к горизонту
public class MoveParams {

    //координаты тела
    private double x;
    private double y;

    //проекции скорости тела
    private double Vx;
    private double Vy;

    public MoveParams(double x, double y, double Vx, double Vy) {
        this.x = x;
        this.y = y;
        this.Vx = Vx;
        this.Vy = Vy;
    }

    public MoveParams(){
        super();
    }

    public double getX() {return x;}

    public double getY() {return y;}

    public double getVx() {return Vx;}

    public double getVy() {return Vy;}

    public void setX(double x) {this.x = x;}

    public void setY(double y) {this.y = y;}

    public void setVx(double vx) {Vx = vx;}

    public void setVy(double vy) {Vy = vy;}
}

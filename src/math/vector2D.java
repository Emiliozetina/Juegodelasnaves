package math; 

public class vector2D {
    private double x,y; 


    //Constructores 
    public vector2D(double x, double y){

        this.x = x; 
        this.y = y; 
    }

    public vector2D(vector2D v){
        this.x = v.x; 
        this.y = v.y;

    }

    public vector2D(){
        x = 0; 
        y = 0;
    }
    //Fin de constructores

    //Inicio de setters y getters

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    //Fin de setters y getters

    //Funciones matematicas usadas para calcular la posición de la nave 
    //y su dirección mediante el ángulo de esta

    public vector2D sum(vector2D v){
        return new vector2D(x + v.getX(), y + v.getY());
    }

    public vector2D resta(vector2D v){
        return new vector2D(x - v.getX(), y - v.getY());
    }
    public vector2D escalar(double value){
        return new vector2D(x * value, y * value);
    }

    public double GetMagnitud(){
        return Math.sqrt(x*x + y*y);
    }

    public vector2D normal(){

        double magnitude = GetMagnitud();
        
        return new vector2D(x / magnitude, y /magnitude);
    }
    public vector2D limite(double  value){
        if (GetMagnitud() > value){
            return this.normal().escalar(value);
        }
        return this;
    }

    public double getAngulo(){
        return Math.asin(y/GetMagnitud());
    }

    public vector2D setDireccion(double angulo){
        double magnitude = GetMagnitud();

        return new vector2D(Math.cos(angulo)* magnitude, Math.sin(angulo)*magnitude);
        
    }
}


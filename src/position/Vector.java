package position;

public class Vector {
    private double x,y; 


    //Constructor
    public Vector(double x, double y){
        this.x = x; 
        this.y = y; 
    } 

    /**
     * Constructor de copia que crea un nuevo objeto Vector copiando los valores
     * de las coordenadas x e y de otro objeto Vector proporcionado como argumento.
     *
     * @param v El objeto Vector del cual se copiarán las coordenadas x e y.
     *          Este parámetro representa el vector fuente que se usará para
     *          inicializar el nuevo vector.
     */
    public Vector(Vector v){
        this.x = v.x;
        this.y = v.y; 
    }

    public Vector(){
        x = 0; 
        y = 0; 
    }
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

    public double getMagnitude(){
        return Math.sqrt(x*x + y*y);
    }

    public Vector normalize(){
        double magnitude = getMagnitude();

        return new Vector(x / magnitude, y /magnitude);
    }

    public double angulo(){
        return Math.asin(y/getMagnitude());
    }


    //Hasta aqui llegan los Setters, getters y operaciones matematicas
    //----------------------
    //De aqui hacia abajo son las operaciones principales
    //Mediante estas es que obtenemos la posicion del jugador o nave


    public Vector suma(Vector v){
        return new Vector(x + v.getX(), y + v.getY());
    }

    public Vector resta(Vector v){
        return new Vector(x - v.getX(), y + v.getY());
    }

    public Vector escalar(double value){
        return new Vector(x*value, y*value);
    }

    public Vector limite(double value){
        if(getMagnitude() > value){
            return this.normalize().escalar(value);
        }
        return this; 
    }

    public Vector direccion(double angulo){

        double magnitude = getMagnitude();

        return new Vector(Math.cos(angulo)* magnitude, Math.sin(angulo)*magnitude);
    }



}

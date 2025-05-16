package estados;

import java.awt.Graphics;

public abstract class estado {
    
    private static estado currentState = null; 

    public static estado getCurrentState() {return currentState;}
    public static void cambiarEstado(estado newEstado){
        currentState = newEstado;
    }


    public abstract void update(float dt);
    public abstract void draw(Graphics g);
}

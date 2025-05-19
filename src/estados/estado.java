package estados;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

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

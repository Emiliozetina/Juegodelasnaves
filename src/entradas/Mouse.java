package entradas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

//Rastrea la posición del ratón y el estado del botón izquierdo del ratón.
public class Mouse extends MouseAdapter {

    
    //Rastrea la coordenada X del mouse
    public static int X;
    //Corordenada Y del mouse
    public static int Y;

    //Para el click izquierdo del mouse 
    public static boolean CIZQ;

    
    //Se llama cuando se presiona un botón del ratón.
    //Si se presiona el botón izquierdo del ratón (BUTTON1), establece CIZQ en true.
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            CIZQ = true;
        }
    }

    
    //Se llama cuando se suelta un botón del ratón.
    //Si se suelta el botón izquierdo del ratón (BUTTON1), establece CIZQ en false.
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            CIZQ = false;
        }
    }


    //Se llama cuando el ratón se arrastra (se mueve mientras se mantiene presionado un botón).
    //Actualiza las coordenadas X e Y a la posición actual del ratón.
    @Override
    public void mouseDragged(MouseEvent e) {
        X = e.getX();
        Y = e.getY();
    }

   //Lo mismo que la anterior solo que es cuando el ratón se arraste (mientras no esta presionado el boton)
    @Override
    public void mouseMoved(MouseEvent e) {
        X = e.getX();
        Y = e.getY();
    }
}

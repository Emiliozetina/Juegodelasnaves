package entradas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class teclado implements KeyListener {

    private boolean[] press = new boolean[256];

    public static boolean w, a, d, shoot;

    public teclado(){

        w = false; 
        a = false; 
        d = false; 
        shoot = false; 
    }


    // Actualiza cuando se presiona una tecla para las acciones de movimiento y disparo.
    public void actualizar(){
        w = press[KeyEvent.VK_W];
        a = press[KeyEvent.VK_A];
        d = press[KeyEvent.VK_D];
        shoot = press[KeyEvent.VK_P];
    }

    @Override
    public void keyPressed(KeyEvent e){
        press[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e){
        press[e.getKeyCode()] = false; 
    }

    @Override
    public void keyTyped(KeyEvent e){}
}

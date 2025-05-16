package graphics;

import java.awt.image.BufferedImage;
import math.vector2D;

public class Animacion {
    
    private BufferedImage[] frames;
    private int velocidad; 
    private int index;
    private boolean running; 
    private vector2D posicion; 
    private long tiempo; 

    public Animacion(BufferedImage[] frames, int velocidad, vector2D posicion){
        this.frames = frames; 
        this.velocidad = velocidad; 
        this.posicion = posicion; 
        index = 0; 
        running = true; 
    }

    /**
     * Actualiza el estado de la animación en función del tiempo transcurrido.
     *
     * dt El tiempo transcurrido desde la última actualización, en segundos.
     * 
     * Este método incrementa el tiempo acumulado con el valor de `dt`. Si el tiempo acumulado
     * supera la velocidad establecida, se reinicia el tiempo acumulado a 0 y se avanza al 
     * siguiente cuadro de la animación incrementando el índice. Si el índice supera la cantidad 
     * de cuadros disponibles, se detiene la animación estableciendo `running` en false y 
     * reinicia el índice a 0.
     */
    public void actualizar (float dt){

        tiempo+= dt; 

        if (tiempo > velocidad){
            tiempo = 0; 
            index ++; 
            if (index >=frames.length){
                running = false; 
                index = 0;
            }
        }

    }
    public boolean isRunning(){
        return running;
    }

    public vector2D getPosicion(){
        return posicion;
    }
    public BufferedImage getCurrentFrame(){
        return frames[index];
    }
    
}

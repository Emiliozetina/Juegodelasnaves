package objetos; 

import estados.GameState;
import graphics.Assets;
import graphics.Sonido;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import math.vector2D;

public abstract class MovObjeto extends GameObject{

    protected vector2D velocity;
	protected AffineTransform at;
	protected double angulo;
	protected double maxVel;
	protected int width;
	protected int height;
    protected GameState gameState;

    private Sonido explSonido;
    protected boolean muerto;

    //Falta el public Moving Objetct que usa cosas de GameObject

    public MovObjeto(vector2D position, vector2D velocity, double maxVel, BufferedImage texture, GameState gameState){
        super(position, texture);
        this.velocity = velocity; 
        this.maxVel = maxVel; 
        this.gameState = gameState;
        width = texture.getWidth();
        height = texture.getHeight();
        angulo = 0; 
        explSonido = new Sonido(Assets.explosion);
        muerto = false;
    }
    protected void collidesWith(){

        ArrayList<MovObjeto> movingObjetos = gameState.getMovObjeto();

        for(int i=0; i < movingObjetos.size(); i++){

            MovObjeto m = movingObjetos.get(i);

            if(m.equals(this)){
                 continue;
            }

            double distance = m.getCenter().resta(getCenter()).GetMagnitud();

            if(distance < m.width/2 + width/2 && movingObjetos.contains(this) && !m.muerto && !muerto){
                objectColission(this,m);
            }
        }

    }

    private void objectColission(MovObjeto a, MovObjeto b){
        
        jugador p = null; 

        if(a instanceof jugador){
            p = (jugador)a;
        }
        else if(b instanceof jugador){
            p = (jugador)b;
        }

        if( p != null && p.isSpawning()){
            return; 
        }

        if(a instanceof Meteor && b instanceof Meteor){
            return; 
        }

        if(!(a instanceof PowerUp || b instanceof PowerUp)){
            a.Destroy();
            b.Destroy();
            return; 
        }

        if(p != null){
            if(a instanceof jugador){
                ((PowerUp)b).executeAction();
                b.Destroy();
            }
            else if(b instanceof jugador){
                ((PowerUp)a).executeAction();
                a.Destroy();
            }
        }
    }

    protected void Destroy(){
        muerto = true; 
        if(!(this instanceof Laser) && !(this instanceof PowerUp))
            explSonido.play();
    }
    protected vector2D getCenter(){
        return new vector2D(position.getX() + width/2, position.getY() + height/2);
    }

    public boolean isMuerto(){
        return muerto;
    }
}

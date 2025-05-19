package objetos;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

import estados.GameState;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import math.vector2D;


public class Laser extends MovObjeto {

    public Laser(vector2D position, vector2D velocity, double maxVel, double angulo, BufferedImage texture, GameState gameState){
        super(position, velocity, maxVel, texture, gameState);
        this.angulo = angulo; 
        this.velocity = velocity.escalar(maxVel);
    }
    
    @Override
    public void update(float dt){
        position = position.sum(velocity);
        if(position.getX() < 0 || position.getX() > Constantes.ancho || position.getY() < 0 || position.getY() > Constantes.alto){
            Destroy();
        }

        collidesWith();
    }

    @Override
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        at = AffineTransform.getTranslateInstance(position.getX() - width/2, position.getY());

        at.rotate(angulo, width/2, 0);

        g2d.drawImage(texture, at, null);
    }

    @Override
    public vector2D getCenter(){
        return new vector2D(position.getX() + width/2, position.getY() + width/2);
    }
}

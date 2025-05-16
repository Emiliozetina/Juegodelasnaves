package objetos;

import estados.GameState;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import math.vector2D;

public class Meteor extends MovObjeto {
    
    private Size size; 

    public Meteor(vector2D position, vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size){
        super(position, velocity, maxVel, texture, gameState);
        this.size = size; 
        this.velocity = velocity.escalar(maxVel);
    }

    private vector2D fleeForce(){
        vector2D desiredVelocity = gameState.getPlayer().getCenter().resta(getCenter());
        desiredVelocity = (desiredVelocity.normal()).escalar(Constantes.MeteorMaxVel);
        vector2D v = new vector2D(velocity);
        return v.resta(desiredVelocity);
    }

    @Override
    public void update(float dt){

        vector2D playerPos = new vector2D(gameState.getPlayer().getCenter());

        int distanceToPlayer = (int) playerPos.resta(getCenter()).GetMagnitud();

        if(distanceToPlayer < Constantes.ShieldDistance / 2 + width /2){

            if (gameState.getPlayer().isShieldOn()){
                vector2D fleeForce = fleeForce();
                velocity = velocity.sum(fleeForce);
            }
        } 

        if(velocity.GetMagnitud() >= this.maxVel){
            vector2D reversedVelocity = new vector2D(-velocity.getX(), -velocity.getY());
            velocity = velocity.sum(reversedVelocity.normal().escalar(0.01f));
        }

        velocity = velocity.limite(Constantes.MeteorMaxVel);

        position = position.sum(velocity);

        if(position.getX() > Constantes.ancho){
            position.setX(-width);
        }
        if(position.getY() > Constantes.alto){
            position.setY(-height);
        }

        if(position.getX() < -width){
            position.setX(Constantes.ancho);
        } 
        if(position.getY() < -height){
            position.setY(Constantes.alto);
        }

        angulo += Constantes.DELTAANGLE/2;
    }

    @Override
    public void Destroy(){
        gameState.divideMeteor(this);
        gameState.playExplosion(position);
        gameState.addScore(Constantes.MeteorScore, position);
        super.Destroy();
    }

    @Override
    public void draw(Graphics g){

        Graphics2D g2d = (Graphics2D)g;

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

        at.rotate(angulo, width/2, height/2);

        g2d.drawImage(texture, at, null);
    }

    public Size getSize(){
        return size;
    }
}

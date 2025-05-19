package objetos;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO



import estados.GameState;
import graphics.Assets;
import graphics.Sonido;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import math.vector2D;

public class Ufo extends MovObjeto {

    private ArrayList<vector2D> path; 

    private vector2D currentNode; 
    private int index; 
    private boolean following; 

    private long fireRate; 

    private Sonido shoot; 

    public Ufo(vector2D position, vector2D velocity, double maxVel, BufferedImage texture, ArrayList<vector2D> path, GameState gameState){
        super(position, velocity, maxVel, texture, gameState);
        this.path = path; 
        index = 0; 
        following = true; 
        fireRate = 0; 
        shoot = new Sonido(Assets.enemigoDisparar);
    }

    private vector2D pathFollowing(){
        currentNode = path.get(index);

        double distanceToNode = currentNode.resta(getCenter()).GetMagnitud();

        if (distanceToNode < Constantes.NodeRadius){
            index++;
            if (index >= path.size()){
                following = false; 
            }
        }

        return seekForce(currentNode);
        
    }

    private vector2D seekForce(vector2D target){
        vector2D desiredVelocity = target.resta(getCenter());
        desiredVelocity = desiredVelocity.normal().escalar(maxVel);
        return desiredVelocity.resta(velocity);
    }

    @Override
    public void update(float dt){

        fireRate += dt;

        vector2D pathFollowing;

        if(following){
            pathFollowing = pathFollowing();
        }
        else {
            pathFollowing = new vector2D();
        }
        pathFollowing = pathFollowing.escalar(1/Constantes.UfoMass);

        velocity = velocity.sum(pathFollowing);
        velocity = velocity.limite(maxVel);
        position = position.sum(velocity);
        

        if(position.getX() > Constantes.ancho || position.getY() > Constantes.alto || position.getX() < -width || position.getY() < -height){
            Destroy();
        }

        if(fireRate > Constantes.UFoFireRate){
            vector2D toPlayer = gameState.getPlayer().getCenter().resta(getCenter());   
            
            toPlayer = toPlayer.normal();

            double currentAngle = toPlayer.getAngulo();

            currentAngle += Math.random()*Constantes.UFOAngleRange - Constantes.UFOAngleRange/2;
            if(toPlayer.getX() < 0){
                currentAngle = -currentAngle + Math.PI;
            }
            toPlayer = toPlayer.setDireccion(currentAngle);

            Laser laser = new Laser(getCenter().sum(toPlayer.escalar(width)), toPlayer, Constantes.LaserVel, currentAngle + Math.PI/2, Assets.laserRojo, gameState);

            gameState.getMovObjeto().add(0, laser);

            fireRate = 0; 

            shoot.play();

        }
        
        if(shoot.getFramePosition() > 8500){
            shoot.stop();
        }

        angulo +=0.05;

        collidesWith();

        

    }
    @Override
    public void Destroy(){
        gameState.addScore(Constantes.UfoScore, position);
        gameState.playExplosion(position);
        super.Destroy();
    }
    
    @Override
    public void draw(Graphics g){

        Graphics2D g2d = (Graphics2D) g; 
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

        at.rotate(angulo, width/2, height/2);

        g2d.drawImage(texture, at, null);
    }
}

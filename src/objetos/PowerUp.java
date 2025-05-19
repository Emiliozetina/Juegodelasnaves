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
import math.vector2D;
import ui.Action;

public class PowerUp extends MovObjeto{

    private long duration;
    private Action action;
    private Sonido powerUpPick;
    private BufferedImage typeTexture;

    public PowerUp(vector2D position, BufferedImage texture, Action action, GameState gameState){
        super(position, new vector2D(), 0, Assets.orb, gameState);

        this.action = action; 
        this.typeTexture = texture; 
        duration = 0; 
        powerUpPick = new Sonido(Assets.powerUp);
        
    }

    void executeAction(){
        action.doAction();
        powerUpPick.play();
    }

    @Override
    public void update(float dt){
        angulo +=0.1; 
        duration += dt;

        if (duration > Constantes.PowerUpDuration){
            this.Destroy();
        }

        collidesWith();
    }

    @Override
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(
            position.getX() + Assets.orb.getWidth()/2 - typeTexture.getWidth()/2,
            position.getY() + Assets.orb.getHeight()/2 - typeTexture.getHeight()/2);

        at.rotate(angulo, typeTexture.getWidth()/2, typeTexture.getHeight()/2);

        g.drawImage(Assets.orb, (int)position.getX(), (int)position.getY(), null);

        g2d.drawImage(typeTexture, at, null);
    }
    
}

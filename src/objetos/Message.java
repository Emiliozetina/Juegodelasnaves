package objetos;

import graphics.texto;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import math.vector2D;

public class Message {
    private float alpha; 
    private String text; 
    private vector2D position; 
    private Color color; 
    private boolean center; 
    private boolean fade; 
    private Font font;
    private final float deltaAlpha = 0.01f;
    private boolean dead; 
    
    public Message(vector2D position, boolean fade, String text, Color color, boolean center, Font font){
        this.font = font; 
        this.text = text; 
        this.position = new vector2D(position);
        this.fade = fade; 
        this.color = color; 
        this.center = center; 
        this.dead = false; 

        if(fade){
            alpha = 1; 
        }
        else {
            alpha = 0;
        }
        
    }
    public void draw(Graphics2D g2d){

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        texto.dibujartexto(g2d, text, position, center, color, font);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        position.setY(position.getY() - 1 );

        if(fade){
            alpha -= deltaAlpha;
        }
        else {
            alpha += deltaAlpha;
        }
        if(fade && alpha < 0 ){
            dead = true;
        }
        if (!fade && alpha > 1){
            fade = true;
            alpha = 1;
        }

    }

    public boolean isDead(){
        return dead;
    }
}

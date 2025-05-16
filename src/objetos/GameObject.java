package objetos;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.vector2D;

public abstract class GameObject {
    protected BufferedImage texture; 
    protected vector2D position; 

    public GameObject(vector2D position, BufferedImage texture){
        this.position = position; 
        this.texture = texture; 
    }

    public abstract void update(float dt);
    public abstract void draw(Graphics g);

    public vector2D getPosition(){
        return position;
    }
    public void setPosition(vector2D position){
        this.position = position; 
    }
}

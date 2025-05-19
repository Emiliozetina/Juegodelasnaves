package objetos;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO


import graphics.Assets;
import java.awt.image.BufferedImage;


public enum Size{

    BIG(2, Assets.grande), MED(2, Assets.mediano), SMALL(2, Assets.pequenos), TINY(0, null);
    
    public int quantity; 
    
    public BufferedImage[] textures; 

    private Size(int quantity, BufferedImage[] textures){
        this.quantity = quantity; 
        this.textures = textures; 
    }
}

package objetos;

import graphics.Assets;
import java.awt.image.BufferedImage;


public enum PowerUpTypes{
    SHIELD("SHIELD", Assets.escudo),
	LIFE("+1 VIDA", Assets.vidas),
	SCORE_X2("SCORE x2", Assets.doblepuntos),
	FASTER_FIRE("FAST FIRE", Assets.fastFire),
	SCORE_STACK("+1000 SCORE", Assets.estrella),
	DOUBLE_GUN("DOUBLE GUN", Assets.dobleArma);

    public String text; 
    public BufferedImage texture; 

    private PowerUpTypes(String text, BufferedImage texture){
        this.text = text; 
        this.texture = texture;
    }
}
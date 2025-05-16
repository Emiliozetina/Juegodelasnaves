package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import math.vector2D;

public class texto {
    public static void dibujartexto(Graphics g, String text, vector2D pos, boolean centro, Color color, Font fuente){
        g.setColor(color);
        g.setFont(fuente);
        vector2D posicion = new vector2D(pos.getX(), pos.getY());

        if(centro){
            FontMetrics fm = g.getFontMetrics();
            posicion.setX(posicion.getX() - fm.stringWidth(text) / 2);
            posicion.setY(posicion.getY() - fm.getHeight() / 2);


        }

        g.drawString(text, (int)posicion.getX(), (int)posicion.getY());
    }
}

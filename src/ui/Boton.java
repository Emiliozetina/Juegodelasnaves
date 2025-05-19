package ui;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

import entradas.Mouse;
import graphics.Assets;
import graphics.texto;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import math.vector2D;

public class Boton {
    
    private BufferedImage mouseOutImg;
	private BufferedImage mouseInImg;
	private boolean mouseIn;
	private Rectangle boundingBox;
	private Action action;
	private String text;
	
	public Boton(BufferedImage mouseOutImg, BufferedImage mouseInImg, int x, int y, String text, Action action) {
		this.mouseInImg = mouseInImg;
		this.mouseOutImg = mouseOutImg;
		this.text = text;
		boundingBox = new Rectangle(x, y, mouseInImg.getWidth(), mouseInImg.getHeight());
		this.action = action;
	}
	
	public void update() {
		
		if(boundingBox.contains(Mouse.X, Mouse.Y)) {
			mouseIn = true;
		}else {
			mouseIn = false;
		}
		
		if(mouseIn && Mouse.CIZQ) {
			action.doAction();
		}
	}
	
	public void draw(Graphics g) {
		
		if(mouseIn) {
			g.drawImage(mouseInImg, boundingBox.x, boundingBox.y, null);
		}else {
			g.drawImage(mouseOutImg, boundingBox.x, boundingBox.y, null);
		}
		

		//Esto es un constructor pero son tantas cosas en una linea que se decidio dividar cada cosa en una 
		//linea para evitar confusiones 
		texto.dibujartexto(
				g,
				text,
				new vector2D(
						boundingBox.getX() + boundingBox.getWidth() / 2,
						boundingBox.getY() + boundingBox.getHeight()),
				true,
				Color.BLACK,

				
				Assets.fontMed);
		
		
	}
	
}

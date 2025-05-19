package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

public class Assets {
	
	public static boolean cargado = false;
	public static float cuenta = 0;
	public static float MAX_COUNT = 57;

	public static BufferedImage player; 
	public static BufferedImage dobleArmaJugador;

	//Efectos
	public static BufferedImage velocidad; 
	public static BufferedImage[] efectoescudo = new BufferedImage[3];

	//Explosion

	public static BufferedImage[] exp = new BufferedImage[9];

	//Laseres

	public static BufferedImage laserAzul, laserVerde, laserRojo;

	//Meteoritos 

	public static BufferedImage[] grande = new BufferedImage[4];
	public static BufferedImage[] mediano = new BufferedImage[2];
	public static BufferedImage[] pequenos = new BufferedImage[2];
	public static BufferedImage[] xs = new BufferedImage[2];

	//Nave enemiga

	public static BufferedImage ufo; 

	//Numeros de puntos y vida 

	public static BufferedImage[] numeros = new BufferedImage[11];

	public static BufferedImage vidas; 

	public static Clip bgMusic, explosion, perder, disparar, enemigoDisparar, powerUp;

	//Interfaz / UI 

	public static BufferedImage blueBtn;
	public static BufferedImage greyBtn;

	//Fuente

	public static Font fontBig;
	public static Font fontMed; 
	
	//Power ups 

	public static BufferedImage orb, doblepuntos, dobleArma, fastFire, escudo, estrella;


	//En este punto terminan las definciones y se empiezan a obtener las imagen de la carpeta res


	//Esta funcion carga los assets

	public static void init()
	{
		player = loadImage("/ships/player.png");
		dobleArmaJugador = loadImage("/ships/doubleGunPlayer.png");

		velocidad = loadImage("/effects/fire08.png");
		
		laserAzul = loadImage("/lasers/laserBlue01.png");
		
		laserVerde = loadImage("/lasers/laserGreen11.png");
		
		laserRojo = loadImage("/lasers/laserRed01.png");
		
		ufo = loadImage("/ships/ufo.png");
		
		vidas = loadImage("/others/life.png");
		
		fontBig = loadFont("/fonts/futureFont.ttf", 42);
		
		fontMed = loadFont("/fonts/futureFont.ttf", 20);
		
		for(int i = 0; i < 3; i++)
			efectoescudo[i] = loadImage("/effects/shield" + (i + 1) +".png"); 
		
		for(int i = 0; i < grande.length; i++)
			grande[i] = loadImage("/meteors/big"+(i+1)+".png");
		
		for(int i = 0; i < mediano.length; i++)
			mediano[i] = loadImage("/meteors/med"+(i+1)+".png");
		
		for(int i = 0; i < pequenos.length; i++)
			pequenos[i] = loadImage("/meteors/small"+(i+1)+".png");
		
		for(int i = 0; i < xs.length; i++)
			xs[i] = loadImage("/meteors/tiny"+(i+1)+".png");
		
		for(int i = 0; i < exp.length; i++)
			exp[i] = loadImage("/explosion/"+i+".png");
		
		for(int i = 0; i < numeros.length; i++)
			numeros[i] = loadImage("/numbers/"+i+".png");
		
			
			
		bgMusic = loadSound("/sounds/backgroundMusic.wav");
		explosion = loadSound("/sounds/explosion.wav");
		perder = loadSound("/sounds/playerLoose.wav");
		disparar = loadSound("/sounds/playerShoot.wav");
		enemigoDisparar = loadSound("/sounds/ufoShoot.wav");
		powerUp = loadSound("/sounds/powerUp.wav");
		
		greyBtn = loadImage("/ui/grey_button.png");
		blueBtn = loadImage("/ui/blue_button.png");
		
		orb = loadImage("/powers/orb.png");
		doblepuntos = loadImage("/powers/doubleScore.png");
		dobleArma = loadImage("/powers/doubleGun.png");
		fastFire = loadImage("/powers/fastFire.png");
		estrella = loadImage("/powers/star.png");
		escudo = loadImage("/powers/shield.png");

		cargado = true;
		
	}
	
	public static BufferedImage loadImage(String path){
		cuenta ++; 
		return loader.ImageLoader(path);
	}
	public static Font loadFont(String path, int size){
		cuenta ++; 
		return loader.loadFont(path, size);
	}
	public static Clip loadSound(String path){
		cuenta  ++; 
		Clip clip = loader.loadSound(path);
		if (clip == null){
			System.out.println("No se encontro el sonido: " + path);
		}
		return loader.loadSound(path);
	}

	//Antes hay que acabar la clase loader, después esta y despues boton 
}

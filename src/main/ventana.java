package main;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import objetos.Constantes;
import graphics.Assets;
import entradas.teclado;
import entradas.Mouse;
import estados.LoadingState;
import estados.estado;

public class ventana extends JFrame implements Runnable{
	
	public static final int ancho = 800, alto = 600;
	private Canvas canvas;
	private Thread thread;
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//Operaciones que se usaran para que el framerate este bloqueado a 60
	private final int FPS = 60;
	private double TARGETTIME = 1000000000/FPS;
	private double delta = 0;
	private int AVERAGEFPS = FPS;

	private teclado keyBoard; 
	private Mouse mouseInput;
	
	public ventana()
	{
		setTitle("Space Invaders");
		setSize(Constantes.ancho , Constantes.alto);
		//La ventana se cerrara al interactuar con la x
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//La ventana no se puede cambiar de tam
		setResizable(false);
		//La ventana se abre en el centro
		setLocationRelativeTo(null);
		//La ventana es visible
		setVisible(true);
		
		canvas = new Canvas();
		keyBoard = new teclado();
		mouseInput = new Mouse();
		
		canvas.setPreferredSize(new Dimension(Constantes.ancho, Constantes.alto));
		canvas.setMaximumSize(new Dimension(Constantes.ancho, Constantes.alto));
		canvas.setMinimumSize(new Dimension(Constantes.ancho, Constantes.alto));
		//Para que pueda recibir interacciones como la ventana principal
		canvas.setFocusable(true);
		
		add(canvas);
		canvas.addKeyListener(keyBoard);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		setVisible(true);
		
	}
	
	

	public static void main(String[] args) {
		new ventana().inicio();
	}
	
	
	private void update(float dt){
		keyBoard.actualizar();
		estado.getCurrentState().update(dt);
		
	}

	private void dibujar(){
		bs = canvas.getBufferStrategy();
		
		if(bs == null)
		{
			canvas.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		//-----------------------
		//Como tal dibujo empieza aqui
		
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, Constantes.ancho, Constantes.alto);

		estado.getCurrentState().draw(g);
		
		g.setColor(Color.WHITE);
		
		g.drawString(""+AVERAGEFPS, 10, 20);
		
		//Aqui termina
		//---------------------
		g.dispose();
		bs.show();
	}
	
	private void init()
	{

		Thread loadingThread = new Thread(new Runnable() {
			
			@Override
			public void run(){
				Assets.init();
			}
		});
		
		estado.cambiarEstado(new LoadingState(loadingThread));
	}
	
	
	@Override
	public void run() {
		
		long now = 0;
		long lastTime = System.nanoTime();
		int frames = 0;
		long time = 0;
		
		init();
		
		while(running)
		{
			now = System.nanoTime();
			//Se calculara el tiempo entre frame y frame para restringir los FPS a un máximo de 60
			delta += (now - lastTime)/TARGETTIME;
			time += (now - lastTime);
			lastTime = now;
			
			
			
			if(delta >= 1)
			{		
				update((float) (delta * TARGETTIME * 0.000001f));
				dibujar();
				delta --;
				frames ++;
			}
			if(time >= 1000000000)
			{
				AVERAGEFPS = frames;
				frames = 0;
				time = 0;
				
			}
			
			
		}
		
		finalizar();
	}
	
	private void inicio(){
		
		thread = new Thread(this);
		thread.start();
		running = true;
		
		
	}
	private void finalizar(){
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
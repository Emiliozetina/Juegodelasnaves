package estados;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

import graphics.Animacion;
import graphics.Assets;
import graphics.Sonido;
import io.PuntajeData;
import io.XMLParser;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import math.vector2D;
import objetos.Constantes;
import objetos.Message;
import objetos.Meteor;
import objetos.MovObjeto;
import objetos.PowerUp;
import objetos.PowerUpTypes;
import objetos.Size;
import objetos.Ufo;
import objetos.jugador;
import ui.Action;


public class GameState extends estado { 
    public static final vector2D PLAYER_START_POSITION = new vector2D(Constantes.ancho /2 - Assets.player.getWidth()/2, Constantes.alto/2 - Assets.player.getHeight()/2);

    private jugador player; 
    private ArrayList<MovObjeto> MovObjetos = new ArrayList<MovObjeto>();
	private ArrayList<Animacion> explosions = new ArrayList<Animacion>();
	private ArrayList<Message> messages = new ArrayList<Message>();

	private int score = 0; 
	private int lives = 3;

	private int meteors; 
	private int waves = 1; 

	private Sonido bgMusic;
	private long gameOverTimer;
	private boolean gameOver; 

	private long ufoSpawner; 
	private long powerUpSpawner;

	public GameState(){
		player = new jugador(PLAYER_START_POSITION, new vector2D(), Constantes.player_max_vel, Assets.player, this);

		gameOver = false; 
		MovObjetos.add(player);

		meteors = 1; 
		startWave();
		bgMusic = new Sonido(Assets.bgMusic);
		bgMusic.ciclo();
		bgMusic.changeVolume(-10.0f);

		gameOverTimer = 0; 
		ufoSpawner = 0; 
		powerUpSpawner = 0; 

		gameOver = false;

	}
    
    public ArrayList<MovObjeto> getMovObjeto(){
        return MovObjetos;
    }

    public jugador getPlayer(){
        return player; 
    }

	public void addScore(int value, vector2D position) {
		
		Color c = Color.WHITE;
		String text = "+" + value + " score";
		if(player.isDoubleScoreOn()) {
			c = Color.YELLOW;
			value = value * 2;
			text = "+" + value + " score" + " (X2)";
		}
		
		score += value;
		messages.add(new Message(position, true, text, c, false, Assets.fontMed));
	}  

	public void playExplosion(vector2D position){
		explosions.add(new Animacion(
				Assets.exp,
				50,
				position.resta(new vector2D(Assets.exp[0].getWidth()/2, Assets.exp[0].getHeight()/2))
				));
	}    
    
	public void divideMeteor(Meteor meteor){

		//Este se tiene que reworkear completamente
		Size size = meteor.getSize();

		BufferedImage[] textures = size.textures;

		Size newSize = null;

		switch(size){
		case BIG:
			newSize = Size.MED;
			break;
		case MED: 
			newSize = Size.SMALL;
			break;
		case SMALL: 
			newSize = Size.TINY;
			break;
		default:
			return;
		}

		for(int i= 0; i < size.quantity; i++){
			MovObjetos.add(new Meteor(
				meteor.getPosition(),
				new vector2D(0, 1).setDireccion(Math.random()*Math.PI*2),
				Constantes.MetorInitVel* Math.random() +1,
				textures[(int)(Math.random()* textures.length)],
				this,
				newSize
				));
		}
		

	}

	private void startWave(){

		messages.add(new Message(new vector2D(Constantes.ancho/2, Constantes.alto/2), false, "Ronda " + waves, Color.WHITE, true,  Assets.fontBig));

		double x, y;

		for(int i = 0; i < meteors; i++){

			x = i % 2 == 0 ? Math.random()*Constantes.ancho : 0;
			y = i % 2 == 0 ? 0 : Math.random()*Constantes.alto;

			BufferedImage texture = Assets.grande[(int)(Math.random()*Assets.grande.length)];

			MovObjetos.add(new Meteor(
				new vector2D(x , y),
				new vector2D(0 ,1).setDireccion(Math.random()* Math.PI *2),
				Constantes.MetorInitVel * Math.random() + 1,
				texture,
				this,
				Size.BIG
				));
		}
		waves++;
		meteors++;
		
	}

	private void spawnUfo(){
		int rand = (int) (Math.random()*2);

		double x = rand == 0 ? (Math.random()* Constantes.ancho):  Constantes.ancho;
		double y = rand == 0 ? Constantes.alto : (Math.random()* Constantes.alto);

		ArrayList<vector2D> path = new ArrayList<vector2D>();

		double posX, posY;

		posX = Math.random()*Constantes.ancho/2;
		posY = Math.random()*Constantes.alto/2;
		path.add(new vector2D(posX, posY));

		posX = Math.random()*(Constantes.ancho/2) + Constantes.ancho /2; 
		posY = Math.random()*Constantes.alto/2;
		path.add(new vector2D(posX, posY));

		posX = Math.random()* Constantes.ancho/2;
		posY = Math.random()* (Constantes.alto/2) + Constantes.alto/2; 
		path.add(new vector2D(posX, posY));

		posX = Math.random()* (Constantes.ancho/2) + Constantes.ancho/2;
		posY = Math.random()* (Constantes.alto/2) + Constantes.alto/2;
		path.add(new vector2D(posX, posY));

		MovObjetos.add(new Ufo(
			new vector2D(x, y),
			new vector2D(),
			Constantes.UfoMaxVel,
			Assets.ufo,
			path,
			this
			));
	}

	private void spawnPowerUp(){

		final int x = (int) ((Constantes.ancho - Assets.orb.getWidth()) * Math.random());
		final int y = (int) ((Constantes.alto - Assets.orb.getHeight()) * Math.random());

		int index = (int) (Math.random() * (PowerUpTypes.values().length));

		PowerUpTypes p = PowerUpTypes.values()[index];

		final String text = p.text; 
		Action action = null; 
		vector2D position = new vector2D(x, y);

		switch(p){
			case LIFE:
				action = new Action() {
					@Override
					public void doAction(){
						lives ++;
						messages.add(new Message(position, false, text, Color.GREEN, false, Assets.fontMed));
					}
				};
				break; 
			case SHIELD:
				action = new Action() {
					@Override
					public void doAction(){
						player.setShield();
						messages.add(new Message(position, false, text, Color.DARK_GRAY, false, Assets.fontMed));
					}
				};
				break; 
			case SCORE_X2:
				action = new Action(){
					@Override
					public void doAction(){
						player.setDoubleScore();
						messages.add(new Message(position, false, text, Color.YELLOW, false, Assets.fontMed));
					}
				};
				break;
			case FASTER_FIRE:
				action = new Action() {
					@Override
					public void doAction(){
						player.setFastFire();
						messages.add(new Message(position, false, text, Color.BLUE , false, Assets.fontMed));

					}
				};
				break;
			case SCORE_STACK:
				action = new Action() {
					@Override
					public void doAction(){
						score += 1000; 
						messages.add(new Message(position, false, text, Color.MAGENTA, false, Assets.fontMed));
					}
				};
				break;
			case DOUBLE_GUN:
				action = new Action() {
					@Override
					public void doAction(){
						player.setDoubleGun();
						messages.add(new Message(position, false, text, Color.ORANGE, false, Assets.fontMed));
					}
				};
				break;
			default:
				break;
		}

		this.MovObjetos.add(new PowerUp(position, p.texture, action, this));

	}

	public void update(float dt){

		if(gameOver){
			gameOverTimer += dt;
		}
		powerUpSpawner += dt;
		ufoSpawner += dt;
		
		for (int i = 0; i < MovObjetos.size(); i++){

			MovObjeto mo = MovObjetos.get(i);

			mo.update(dt);
			if(mo.isMuerto()){
				MovObjetos.remove(i);
				i--;
			}

			
		}

		for (int i= 0; i < explosions.size(); i++){
			Animacion anim = explosions.get(i);
			anim.actualizar(dt);
			if(!anim.isRunning()){
				explosions.remove(i);
			}
		}

		if(gameOverTimer > Constantes.gameovertime){

			try {
				ArrayList<PuntajeData> dataList = XMLParser.readFile();
				dataList.add(new PuntajeData(score));
				XMLParser.writeFile(dataList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			bgMusic.stop();

			estado.cambiarEstado(new MenuState());
		}

		if(powerUpSpawner > Constantes.PowerUpSpawnTime){
			spawnPowerUp();
			powerUpSpawner = 0;
		}

		if(ufoSpawner > Constantes.UFOSpawnRate){
			spawnUfo();
			ufoSpawner = 0;
		}

		for (int i = 0; i< MovObjetos.size(); i++){
			if (MovObjetos.get(i) instanceof Meteor){
				return;
			}
		}
		startWave();
	}

	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for(int i = 0; i < messages.size(); i++){
			messages.get(i).draw(g2d);
			if(messages.get(i).isDead()){
				messages.remove(i);
			}
		}

		for(int i = 0; i < MovObjetos.size(); i++){
			MovObjetos.get(i).draw(g);
		}

		for(int i = 0; i < explosions.size(); i++){
			Animacion anim = explosions.get(i);
			g2d.drawImage(anim.getCurrentFrame(), (int)anim.getPosicion().getX(), (int)anim.getPosicion().getY(), null);
		}
		drawScore(g);
		drawLives(g);
	}

	private void drawScore(Graphics g){

		vector2D pos = new vector2D(850, 25);
		String scoreToString = Integer.toString(score);

		for(int i = 0; i < scoreToString.length(); i++){

			g.drawImage(Assets.numeros[Integer.parseInt(scoreToString.substring(i, i + 1))], (int)pos.getX() , (int)pos.getY(), null);
			pos.setX(pos.getX() + 20);
		}
	}

	private void drawLives(Graphics g){

		if(lives < 1){
			return; 
		}

		vector2D livePosition = new vector2D(25, 25);

		g.drawImage(Assets.vidas, (int)livePosition.getX(), (int)livePosition.getY(), null);
		
		g.drawImage(Assets.numeros[10], (int)livePosition.getX() +  40, (int)livePosition.getY() + 5, null);

		String livesToString = Integer.toString(lives);

		vector2D pos = new vector2D(livePosition.getX(), livePosition.getY());

		for(int i = 0; i < livesToString.length(); i++){

			int number = Integer.parseInt(livesToString.substring(i, i +1));

			if(number <= 0){
				break; 
				
			}
			g.drawImage(Assets.numeros[number], (int)pos.getX() + 60, (int)pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
	}

	public ArrayList<MovObjeto> getMovObjetos(){
		return MovObjetos;
	}

	public ArrayList<Message> getMessages(){
		return messages;
	}

	public jugador getJugador(){
		return player; 
	}


	public boolean substractLife(vector2D position){
		lives --; 

		Message lifeLostMesg = new Message(position, false, "-1 vida", Color.RED, false, Assets.fontMed);
		messages.add(lifeLostMesg);

		return lives > 0;
	}

	public void gameOver(){
		Message gameOverMSG = new Message(PLAYER_START_POSITION, true, "Fin de la partida", Color.WHITE, true, Assets.fontBig);
		this.messages.add(gameOverMSG);
		gameOver = true; 
	}

}

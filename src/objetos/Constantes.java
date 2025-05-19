package objetos;

import javax.swing.filechooser.FileSystemView;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO


public class Constantes {
    
    //Dimensiones de la pestaña 

    public static final int ancho = 1000; 
    public static final int alto = 600; 

    //Propiedades del jugador 

    public static final int firerate = 300; 
    public static final double DELTAANGLE = 0.1; 
    public static final double acc = 0.2; 
    public static final double player_max_vel = 7.0; 
    public static final long flickertime = 200; 
    public static final long spawningtime = 3000; 
    public static final long gameovertime = 3000; 

    //Propiedades de laser

    public static final double LaserVel = 15.0; 

    //Propiedades del meteorito 

    public static final double MetorInitVel = 2.0; 
    public static final int MeteorScore = 20; 
    public static final double MeteorMaxVel = 6; 
    public static final int ShieldDistance = 150; 

    //Propiedades UFO 

    public static final int NodeRadius = 160; 
    public static final double UfoMass = 60; 
    public static final int UfoMaxVel = 3; 
    public static long UFoFireRate = 1000; 
    public static double UFOAngleRange = Math.PI / 2; 
    public static final int UfoScore = 40; 
    
    public static final long UFOSpawnRate = 10000;

    public static final String Play = "Play";

    public static final String Exit = "Exit";

    public static final int LoadingBarWidht = 500; 
    public static final int LoadingBarHeight = 50; 

    public static final String Return = "Return";
    public static final String HighScore = "Highest Score";

    public static final String Score = "Score";
    public static final String DATE = "Date";

    public static final String SCORE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() +
		"\\Juegodelasnaves\\data.xml"; // data.xml if you use XMLParser

  //Usadas en XML parse 
    public static final String Player = "Player";
    public static final String Players = "Players";

    //-----------Power ups-----------------------------

    public static final long PowerUpDuration = 10000; 
    public static final long PowerUpSpawnTime = 8000; 

    public static final long ShieldTime = 12000; 
    public static final long DoubleScoreTime = 10000; 
    public static final long FastFireTime = 14000; 
    public static final long DoubleGunTime = 12000;
    
    public static final int ScoreStack = 1000; 


}

package graphics;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

public class Sonido {
    private Clip clip; 
    private FloatControl volumen;

    public Sonido(Clip clip){
        this.clip = clip; 
        volumen = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }

    public void ciclo(){
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    public int getFramePosition(){
        return clip.getFramePosition();
    }

    public void changeVolume(float value){
        volumen.setValue(value);
    }
}

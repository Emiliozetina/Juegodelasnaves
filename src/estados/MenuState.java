package estados;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

import java.awt.Graphics;
import java.util.ArrayList;

import objetos.Constantes;
import graphics.Assets;
import ui.Action;
import ui.Boton;

public class MenuState extends estado {
    
    private ArrayList<Boton> buttons; 

    public MenuState(){
        buttons = new ArrayList<Boton>();

        buttons.add(new Boton(Assets.greyBtn, Assets.blueBtn,
         Constantes.ancho /2 - Assets.greyBtn.getWidth()/2, Constantes.alto / 2 - Assets.greyBtn.getHeight() * 2, Constantes.Play,
         new Action() {
            @Override
            public void doAction(){
                estado.cambiarEstado(new GameState());
            }
         }
         ));
        
        buttons.add(new Boton(
            Assets.greyBtn, Assets.blueBtn, Constantes.ancho /2 - Assets.greyBtn.getWidth()/2, Constantes.alto /2 + Assets.greyBtn.getHeight() * 2,
             Constantes.Exit, new Action(){
                @Override
                public void doAction(){
                    System.exit(0);
                }

             }
             ));
        buttons.add(new Boton(Assets.greyBtn, Assets.blueBtn, Constantes.ancho / 2 - Assets.greyBtn.getWidth()/2, Constantes.alto/2, Constantes.HighScore,
         new Action(){
            @Override
            public void doAction(){
                estado.cambiarEstado(new ScoreState());
            }
         }
         ));
    }

    @Override
    public void update(float dt){
        for (Boton b: buttons){
            b.update();
        }
    }

    @Override
    public void draw(Graphics g){
        for(Boton b: buttons){
            b.draw(g);
        }
    }
}

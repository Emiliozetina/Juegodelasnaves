package estados;

import graphics.Assets;
import graphics.texto;
import io.PuntajeData;
import io.XMLParser;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import math.vector2D;
import objetos.Constantes;
import ui.Action;
import ui.Boton;

public class ScoreState extends estado{

    private Boton returnButton; 

    private PriorityQueue<PuntajeData> highScores;
    private Comparator<PuntajeData> scoreComparator;

    private PuntajeData[] auxArray;

    public ScoreState(){
        returnButton = new Boton(Assets.greyBtn, Assets.blueBtn, Assets.greyBtn.getHeight(), 
            Constantes.alto - Assets.greyBtn.getHeight() * 2,
            Constantes.Return, new Action() {
                @Override
                public void doAction(){
                    estado.cambiarEstado(new MenuState());
                }
            }
        );

        scoreComparator = new Comparator<PuntajeData>(){
            @Override
            public int compare(PuntajeData e1, PuntajeData e2){
                return e1.getPuntaje() < e2.getPuntaje() ? -1: e1.getPuntaje() > e2.getPuntaje() ? 1: 0;
            }
        };

        highScores = new PriorityQueue<PuntajeData>(10, scoreComparator);

        try {
            ArrayList<PuntajeData> dataList = XMLParser.readFile();

            for (PuntajeData d: dataList){
                highScores.add(d);
            }
            while(highScores.size() > 10){
                highScores.poll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(float dt){
        returnButton.update();
    }

    @Override
    public void draw(Graphics g){
        returnButton.draw(g);

        auxArray = highScores.toArray(new PuntajeData[highScores.size()]);

        Arrays.sort(auxArray, scoreComparator);

        vector2D scorePos = new vector2D(
            Constantes.ancho / 2 - 200, 
            100
        );
        vector2D datePos = new vector2D(
            Constantes.ancho /2 + 200, 
            100
        );

        texto.dibujartexto(g, Constantes.Score, scorePos, true, Color.BLUE, Assets.fontBig);
        texto.dibujartexto(g, Constantes.DATE, datePos, true, Color.BLUE, Assets.fontBig);

        scorePos.setY(scorePos.getY() + 40);
        datePos.setY(datePos.getY() + 40);

        for (int i = auxArray.length -1; i > -1; i--){

            PuntajeData d = auxArray[i];
            
            texto.dibujartexto(g, Integer.toString(d.getPuntaje()), scorePos, true, Color.WHITE, Assets.fontMed);
            texto.dibujartexto(g, d.getDia(), datePos, true, Color.WHITE, Assets.fontMed);

            scorePos.setY(scorePos.getY() + 40);
            datePos.setY(datePos.getY() + 40);
        }
    }
    
}

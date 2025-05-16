package estados;

import graphics.Assets;
import graphics.loader;
import graphics.texto;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import math.vector2D;
import objetos.Constantes;

public class LoadingState extends estado{
    
    private Thread loadingThread;

    private Font font; 

    public LoadingState(Thread loadingThread){
        this.loadingThread = loadingThread;
        this.loadingThread.start();
        font = loader.loadFont("/fonts/futureFont.ttf", 38);
    }
    
    @Override
    public void update(float dt){
        if(Assets.cargado) {
            estado.cambiarEstado(new MenuState());
            try { 
                loadingThread.join();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Graphics g){
        GradientPaint gp = new GradientPaint(Constantes.ancho / 2 - Constantes.LoadingBarWidht/2,
         Constantes.alto / 2 - Constantes.LoadingBarHeight / 2, Color.WHITE, Constantes.ancho / 2 + Constantes.LoadingBarWidht /2, 
         Constantes.alto / 2 + Constantes.LoadingBarHeight /2, Color.BLUE);
        
        Graphics2D g2d = (Graphics2D)g;

        g2d.setPaint(gp);

        float percentage = (Assets.cuenta / Assets.MAX_COUNT);

        g2d.fillRect(Constantes.ancho / 2 - Constantes.LoadingBarWidht /2 ,
         Constantes.alto /2 - Constantes.LoadingBarHeight /2, 
         (int)(Constantes.LoadingBarWidht * percentage), Constantes.LoadingBarHeight);
        
        g2d.drawRect(Constantes.ancho / 2 - Constantes.LoadingBarWidht /2 , 
        Constantes.alto /2 - Constantes.LoadingBarHeight /2 , Constantes.LoadingBarWidht, Constantes.LoadingBarHeight);

        texto.dibujartexto(g2d, "SPACE INAVDERS", new vector2D(Constantes.ancho /2, Constantes.alto /2 - 50 ), true, Color.WHITE, font);

        texto.dibujartexto(g2d, "Cargando", new vector2D(Constantes.ancho /2 , Constantes.alto / 2 + 40), true, Color.WHITE, font);
    }
}

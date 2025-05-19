package io;

//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

import java.text.SimpleDateFormat;
import java.util.Date;

public class PuntajeData {

    private String dia; 
    private int puntaje; 

    public PuntajeData(int puntaje){
        this.puntaje = puntaje; 

        Date hoy  = new Date(System.currentTimeMillis());
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        dia = formato.format(hoy);
    }

    public PuntajeData(){

    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    
}



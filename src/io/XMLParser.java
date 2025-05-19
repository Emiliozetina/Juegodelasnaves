package io;


//Eauipo numero 4 
//Emilio Zetina, Valeri Skirlathze, Alfredo Vieto, Ricardo Restrepo 
//Proyecto final de semestre para la materia de POO

import io.PuntajeData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import java.util.ArrayList;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import objetos.Constantes;

public class XMLParser {

    //Hacerlo con XML parser y no con el Json
    
    public static ArrayList<PuntajeData> readFile(){

        ArrayList<PuntajeData> dataList = new ArrayList<PuntajeData>();

        try {
            
            File file = new File(Constantes.SCORE_PATH);

            if(!file.exists() || file.length() == 0){
                return dataList;
            }

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(new FileInputStream(file));
            PuntajeData data = null; 

            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()){
                    StartElement start = event.asStartElement();

                    if(start.getName().getLocalPart().equals(Constantes.Player)){
                        data = new PuntajeData();
                    }
                    else if(start.getName().getLocalPart().equals(Constantes.DATE)){
                        event = eventReader.nextEvent();
                        data.setDia(event.asCharacters().getData());
                    }
                    else if(start.getName().getLocalPart().equals(Constantes.Score)){
                        event = eventReader.nextEvent();
                        data.setPuntaje(Integer.parseInt(event.asCharacters().getData()));
                    }

                }

                if(event.isEndElement()){
                    EndElement end = event.asEndElement();
                    if(end.getName().getLocalPart().equals(Constantes.Player)){
                        dataList.add(data);
                    }
                }
            }
            return dataList;
            


        } 
        catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    public static void writeFile(ArrayList<PuntajeData> dataList) throws XMLStreamException, IOException {

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        File outputFile = new File(Constantes.SCORE_PATH);
        outputFile.getParentFile().mkdir();

        outputFile.createNewFile();

        XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileOutputStream(outputFile), "UTF-8");

        writer.writeStartDocument();
        writer.writeStartElement(Constantes.Players);

        for (PuntajeData data: dataList){
            writer.writeStartElement(Constantes.Player);
            writer.writeStartElement(Constantes.DATE);
            writer.writeCharacters(data.getDia());
            writer.writeEndElement();
            writer.writeStartElement(Constantes.Score);
            writer.writeCharacters(Integer.toString(data.getPuntaje()));
            writer.writeEndElement();
            writer.writeEndElement();
        }

        writer.writeEndElement();
        writer.flush();
        writer.close();

    }
}
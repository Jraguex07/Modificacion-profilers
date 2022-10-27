/** ****************************************************************************
 * Siete y Media
 * Raguex, Ordoñez y Quinilla
 *
 * Es un juego en donde se sumara siete y media para ganar.
 ***************************************************************************** */
package sietemedio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

//*********************************************************************************
// Esta clase  contiene una lista privada de las cartas y los jugadores, tambien el turno de cada uno.
public class Juego {

    private final ArrayList<Carta> cartas;
    private final ArrayList<Jugador> jugadores;
    private int jugadorEnTurno;

    //******************************************************************************
    public Juego() {

        // Este constructor guarda las cartas y jugadores en un Arraylist 
        cartas = new ArrayList<>();
        jugadores = new ArrayList<>();
        iniciarJuego();

    }
    

    
    
    //******************************************************************************
    // Este método obtiene las cartas que estan guardadas en un Arraylist.
    public ArrayList<Carta> getCartas() {
        return cartas;
    }// fin getCartas

    //******************************************************************************
    // Este método inicia el juego, con una carpeta que contiene las cartas con diferentes imagenes.
    private void iniciarJuego() 
    {
        // Carpetas con las imagenes de las diferentes cartas
        File[] carpetas = {new File("cartas/corazon"), new File("cartas/picas"), new File("cartas/diamante"), new File("cartas/trebol")};

        for (File carpeta : carpetas) {

            File[] ficherosImagen = carpeta.listFiles();

            //Por cada fichero de imagen agregar a la lista de cartas la carta con su valor
            for (File file : ficherosImagen) {
                if (file.isFile()) {
                    String nombreImagen = file.getName();
                    String ruta = carpeta + "/" + nombreImagen;
                    cartas.add(new Carta(ruta, getValorCarta(nombreImagen)));
                }
            }
        }

        jugadorEnTurno = 0;

    } // fin de iniciar juego

    //**************************************************************
    // Este metodo el jugador obtiene otra carta.
    public Carta getProximaCarta() 
    {
        while (cartas.size() > 0) {
            //tomar una carta de manera aleatoria
            Carta carta = cartas.get(ThreadLocalRandom.current().nextInt(0, cartas.size()));
            cartas.remove(carta);
            return carta;
        }
        return null;
    } // fin de getProximaCarta

    //**************************************************************
    // Con este metodo el jugador obtiene su turno para jugar.
    public Jugador getJugadorEnTurno() {
        return jugadores.get(jugadorEnTurno);
    }// fin de getJugadorEnTurno

    //****************************************************************
    // Este método le dara el turno al proximo jugador, brinda un orden para jugar.
    public Jugador getProximoJugador() {
        while (jugadorEnTurno < jugadores.size() - 1) {
            jugadorEnTurno++;
        } 
            jugadorEnTurno = 0;
        

        return jugadores.get(jugadorEnTurno);
    }// fin de getJugadorEnTurno

    //*****************************************************************
    // Este método busca en la lista a los jugadores que han terminado de elegir carta
    // y ya no pueden elegir y la retorna.
    public boolean hanTerminadoTodosLosJugadores() {
        return jugadores.stream().filter(jugador -> jugador.haTerminado()).count() == jugadores.size();
    }

    //******************************************************************
    // Este método nos dice que el jugador en turno ya eligio carta y termino su turno.
    public boolean haTerminadoJugador() {
        return jugadores.get(jugadorEnTurno).haTerminado();
    }

    //***********************************************************************************************
    
    public Jugador getGanador() {
         
        List<Jugador> lista = jugadores.stream().filter(jugador-> jugador.getValorTotalDeCartas()<= 7.5).collect(Collectors.toList());
        
        while(!lista.isEmpty()){
            Jugador ganador = lista.get(0);
            
            for(int i = 1; i < lista.size(); i++){
                //buscar el que este mas cerca de 7.5 y en caso que tengan los mismos puntos que tenga menos cartas
                if(lista.get(i).getValorTotalDeCartas()-7.5 < ganador.getValorTotalDeCartas()-7.5 && lista.get(i).getCartas().size() < ganador.getCartas().size()){
                    ganador = lista.get(i);
                }
                
            }
            return ganador;
        }
        
        return null;
       
    }

    // Este método nos retornara la lista de jugadores de dato generico la clases jugador 
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    //*******************************************************************************
    // Este método privado guarda el nombre de la carta y determina el valor  a cada diferente carta.
    private double getValorCarta(String nombreImagen) {
        //split separa u objeto de tipo string 
        //toma las imagenes y la asignas depediendo 
        String nombre = nombreImagen.split(".png")[0];
        return switch (nombre) {
            case "A" -> 1;
            case "J" -> 0.5;
            case "Q" -> 0.5;
            case "K" -> 0.5;
            default -> Double.parseDouble(nombre);
        }; //caso 1
        //caso 2
        //caso 3
        //caso 4
        //caso 5

    }// fin de getValorCarta

}// fin Class Juego

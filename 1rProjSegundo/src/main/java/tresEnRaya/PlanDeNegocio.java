/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tresEnRaya;

import java.util.ArrayList;

/**
 *
 * @author irlor
 */
public class PlanDeNegocio {
    
    private Integer[][]tablero;
    private ArrayList<String> nombreJugadores;
    private Persistencia persistencia;
    
    private int rondaActual;
    private int turnoActual;
    private int jugadorActual;
    private int cantidadJugadores;
    
    private String jugadorAnterior;
    
    public PlanDeNegocio() {
        
        this.jugadorActual = 1;
        this.turnoActual = 1;
        this.rondaActual = 1;
        this.nombreJugadores = new ArrayList<>();
        this.persistencia = new Persistencia();
        
    }

    public String getJugadorAnterior() {
        return jugadorAnterior;
    }

    public ArrayList getNombreJugadores() {
        return nombreJugadores;
    }

    public void setNombreJugadores(String nombreJugador) {
        
        nombreJugador = nombreJugador.trim();
        
        if (nombreJugador.isBlank()){
            throw new IllegalArgumentException("Debes escribir algo");
        }
        
        nombreJugador = Character.toUpperCase(nombreJugador.charAt(0)) + nombreJugador.substring(1).toLowerCase();
        
        if (this.nombreJugadores.contains(nombreJugador)) {
            throw new IllegalArgumentException("Este nombre ya esta siendo usado esta partida");
        } else {
            this.nombreJugadores.add(nombreJugador);
        }
    }
    
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(int cantidadJugadores) throws Exception{
        
        if (cantidadJugadores >= 2 && cantidadJugadores <= 10){
            this.cantidadJugadores = cantidadJugadores;
        } else {
            throw new IllegalArgumentException("La cantidad de jugadores debe ser entre 2 y 10 incluidos");
        }
        
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public int getJugadorActual() {
        return jugadorActual;
    }
    
    //Comprobamos si la casilla esta libre y la marcamos en el tablero interno
    public void marcarCasilla(int fila, int columna) throws Exception{
        
        if (this.tablero[fila][columna] == null){
            
            this.tablero[fila][columna] = this.jugadorActual;
            this.turnoActual++;
            this.jugadorAnterior = this.nombreJugadores.get(this.jugadorActual-1);
            
            //Si la casilla es correcta, después de marcarla actualizamos el jugador actual para poder iniciar el siguiente turno
            if(this.jugadorActual == this.cantidadJugadores){
                this.jugadorActual = 1;
                this.rondaActual++;
            } else {
                this.jugadorActual++;
            }
        } else {
            throw new IllegalArgumentException("Esta casilla ya esta marcada");
        }
    }
    
    /***
     * Iniciamos el tablero interno con el tamaño indicado
     * @param i
     * @throws Exception 
     */
    public void inicializarTablero(int i) throws Exception{
        
        if (i >= 3 && i <= 10) {
            this.tablero = new Integer[i][i];
        } else {
            throw new IllegalArgumentException("El tamaño del tablero debe ser entre 3 y 10 incluidos");
        }
    }
    
    /***
     * Comprobamos si alguno de los jugadores ha ganado la partida y pasamos un booleano segun la respuesta 
     * @return 
     */
    public boolean hayGanador(){
    
        //Comprobamos si algun jugador ha ganado contando filas
        for (int i = 0; i < this.tablero.length; i++) {
            
            for (int j = 0; j < this.tablero[i].length; j++) {
               
                if (this.tablero[i][j] != null) {
                    
                    //Comprobamos si algun jugador ha ganado contando filas
                    if ((j < this.tablero[i].length-2) && this.tablero[i][j].equals(this.tablero[i][j+1]) && this.tablero[i][j].equals(this.tablero [i][j+2])) {
                        this.persistencia.registrarVictoria(this.jugadorAnterior);
                        return true;
                    }
                    //Comprobamos si algun jugador ha ganado contando columnas
                    if ((i < this.tablero[i].length-2) && this.tablero[i][j].equals(this.tablero[i+1][j]) && this.tablero[i][j].equals(this.tablero [i+2][j])) {
                        this.persistencia.registrarVictoria(this.jugadorAnterior);
                        return true;
                    }
                    //Comprobamos si algun jugador ha ganado contando diagonal hacia la izquierda
                    if ((i < this.tablero[i].length-2 && j >= 2) && this.tablero[i][j].equals(this.tablero[i+1][j-1]) && this.tablero[i][j].equals(this.tablero [i+2][j-2])) {
                        this.persistencia.registrarVictoria(this.jugadorAnterior);
                        return true;
                    }
                    //Comprobamos si algun jugador ha ganado contando diagonal hacia la derecha
                    if ((i < this.tablero.length-2 && j < this.tablero[i].length-2) && this.tablero[i][j].equals(this.tablero[i+1][j+1]) && this.tablero[i][j].equals(this.tablero [i+2][j+2])) {
                        this.persistencia.registrarVictoria(this.jugadorAnterior);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /***
     * Comprobamos si hay empate y pasamos un booleano segun la respuesta 
     * @return 
     */
    public boolean hayEmpate(int numCasillas){
        
        /*//Primero comprobamos si el turno actual es mas grande que el numero de casillas, asi ahorramos hacer el bucle
        if (this.turnoActual > numCasillas){
        return true;
        }
        */
        /*Para comprobar el empate pasamos por todo el tablero hasta que encuentre el simbolo inicial,
        lo que indica que hay al menos una posicion vacia*/
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                
                if (tablero[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public ArrayList<String> mostrarHistorial(){
        return this.persistencia.obtenerRanking();
    }
    
    public void finalizarPartida() {
        this.jugadorActual = 1;
        this.turnoActual = 1;
        this.rondaActual = 1;
        this.nombreJugadores.clear();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ejercicio3.rprojsegundo;

import java.util.Scanner;

/**
 *
 * @author irlor
 */
public class App {
    //tamaño del tablero, decir la cantidad, y cuantos jugadores hay
    
    static String tablero[][] = new String[3][3];

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        String jugador1 = "J1";
        String jugador2 = "J2";
        
        String simboloJug = "";
        
        int posicion;

        String jugadorActual = "";

        boolean partidaFinalizada = false;
        
        boolean todoOkey = true;
        
        
        for (int i = 0; i < tablero.length; i++) {
            
            for (int j = 0; j < tablero[i].length; j++) {
                
                tablero[i][j] = "-";
            }
        }
        
        do {
            
            if(jugadorActual == jugador2 || jugadorActual == ""){
                jugadorActual = jugador1;
                simboloJug = "x";
            } else {
                jugadorActual = jugador2;
                simboloJug = "o";
            }
            
            mostrarTablero();
            
            do{
                
                System.out.println("Elige una posicion (1-9): ");
                posicion = sc.nextInt();
                
                todoOkey = comprobarYMarcarCasillas(posicion,simboloJug);
                
                System.out.println(todoOkey);
                
            }while(!todoOkey);
            
            if (comprobarVictoria(simboloJug)) {
                partidaFinalizada = true;
                System.out.println("El jugador " + jugadorActual + " ha ganado.");
            } else if (comprobarTableroLleno()){
                partidaFinalizada = true;
                System.out.println("Empate");
            }
            
            
        } while (!partidaFinalizada);
        
        
        mostrarTablero();
        
        System.out.println("Fin de la partida");
        
    }
    
    
    public static boolean comprobarYMarcarCasillas(int posicionElegida, String simbolo){
        
        int contador = 1;
        
        if (posicionElegida <= 0 || posicionElegida > 9){
            return false;
        }
    
        
        for (int i = 0; i < tablero.length; i++) {
            
            for (int j = 0; j < tablero[i].length; j++) {
                
                if (contador == posicionElegida && tablero[i][j] == "-"){
                    
                    tablero[i][j] = simbolo;
                    return true;
                }
                ++contador;
            }
        }
        return false;
        
    }
    
    public static boolean comprobarVictoria(String simbolo){
        
        //Comprobacion de lineas
        for (int i = 0; i < 3; i++) {
            
            if (tablero[i][0] != "-" && tablero[i][0] == tablero[i][1] &&  tablero[i][1] == tablero[i][2]) {
                return true;
            }
        }
        
        //Comprobacion de columnas
        for (int i = 0; i < 3; i++) {
            
            if (tablero[0][i] != "-" && tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i]) {
                return true;
            }
        }
        
        //Comprobacion de diagonales
        if (tablero[1][1] != "-" && (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2] || tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0])) {
            return true;
        }
        
        return false;
    
    }
    
    public static boolean comprobarTableroLleno(){
        
        for (int i = 0; i < tablero.length; i++) {
            
            for (int j = 0; j < tablero[i].length; j++) {
                
                if(tablero[i][j]=="-"){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public static void mostrarTablero(){
        
        for (int i = 0; i < tablero.length; i++) {
            
            for (int j = 0; j < tablero[i].length; j++) {
                
                System.out.print(tablero[i][j]);
            }
            System.out.println();
        }
            
    }
}



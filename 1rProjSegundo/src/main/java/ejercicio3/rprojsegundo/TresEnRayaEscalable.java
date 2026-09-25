/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio3.rprojsegundo;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author irlor
 */
public class TresEnRayaEscalable {
    
    static char tablero[][];
    
    static final char SIGNOINICIAL = '-';
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        boolean numCorrecto = false;
        boolean simboloCorrecto;
        boolean gameOver = false;
        
        int tamañoTablero = 0;
        int numJugadores = 0;
        char simbolo = '_';
        
        char simboloJugador[];
        
        //Pedimos al usuario el tamaño de tablero que quiera, y comprobamos si es correcto
        do {
            
            try {
                
                System.out.println("De que tamaño quieres el tablero?");
            
                tamañoTablero = sc.nextInt();
                
                numCorrecto = (tamañoTablero >= 3 && tamañoTablero<=10);
                
                if (!numCorrecto) {
                    System.out.println("El numero debe de estar entre 2 y 10 (incluidos)");
                }
            
            } catch (Exception e){
            
                System.out.println("Debes escribir un numero");
                
                numCorrecto = false;
                sc.nextLine();
            }
            
        } while (!numCorrecto);
        
        //inicializamos el tablero con el tamaño escrito y lo llenamos con -
        tablero = new char[tamañoTablero][tamañoTablero];
        
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = SIGNOINICIAL;
            }
        }
        
        //Pedimos al usuario la cantidad de jugadores, y comprobamos si es correcto
        do {
            
            try {
                
                System.out.println("Cuantos jugadores habrá? (2-5)");
            
                numJugadores = sc.nextInt();
                
                numCorrecto = (numJugadores >= 2 && numJugadores<=5);
                
                if (!numCorrecto) {
                    System.out.println("El numero debe de estar entre 2 y 5 (incluidos)");
                }
            
            } catch (Exception e){
            
                System.out.println("Debes escribir un numero");
                
                numCorrecto = false;
                sc.nextLine();
            }
            
        } while (!numCorrecto);
        
        //Necesario para limpiar el búfer
        sc.nextLine();
        
        //Inicializamos la array con la cantidad de jugadores
        simboloJugador = new char[numJugadores];
        
        
        //For para preguntar y añadir el simbolo que querrá usar cada jugador
        for (int i = 0; i < numJugadores; i++) {
            
            do {
            
                simboloCorrecto = true;
                
                try {

                    System.out.println("Jugador " + (i+1) + ", Elige un simbolo para jugar (solo se usará el primer caracter): ");

                    simbolo = sc.nextLine().charAt(0);

                    for (char e : simboloJugador) {
                        if (e == simbolo || simbolo == SIGNOINICIAL) {
                            simboloCorrecto = false;
                            System.out.println("Ese simbolo ya esta siendo usado");
                            break;
                        }
                    }

                } catch (Exception e){

                    System.out.println("Debes escribir un carácter");

                    simboloCorrecto = false;
                }
                
                if (simboloCorrecto){
                    simboloJugador[i] = simbolo;
                }

            } while (!simboloCorrecto);
        }
        
        int rondaActual = 1;
        int jugadorActual = 0;
        
        int fila;
        int columna;
        
        //Iniciamos el juego y contamos al jugador
        do {
            
            jugadorActual++;
            
            //Iniciamos la ronda del jugador actual
            do {

                numCorrecto = true;

                System.out.println("Ronda " + rondaActual + ". Turno del jugador " + (jugadorActual));

                mostrarTablero();

                try{
                    
                    //Preguntamos la fila y la columna y comprobamos si es correcto segun los parametros
                    System.out.println("Elige una fila: ");

                    fila = sc.nextInt();

                    System.out.println("Elige una columna: ");

                    columna = sc.nextInt();

                    //Hacemos las comprobaciones que toque mirando que los numeros esten en el rango correcto
                    if (fila <= 0 || fila > tamañoTablero) {
                        numCorrecto = false;
                        System.out.println("La fila debe de estar entre 1 y " + tamañoTablero);
                    }else if (columna <= 0 || columna > tamañoTablero) {
                        numCorrecto = false;
                        System.out.println("La columna debe de estar entre 1 y " + tamañoTablero);
                    }
                    if (numCorrecto) {
                        marcarTablero(fila, columna, simboloJugador[jugadorActual-1]);
                    }

                }catch (InputMismatchException e) {
                    //en caso de que escriban algo que no sea un numero, necesitaremos limpiar el bufer para que no entre en bucle
                    System.out.println("Debes escribir un numero.");
                    sc.nextLine(); 
                    numCorrecto = false;
                }catch(Exception e){

                    System.out.println("La casilla esta marcada. Vuelve a marcar");
                    numCorrecto = false;
                }

            } while (!numCorrecto);
            
            //Despues de cada turno, comprobamos si hay algun ganador o si hay empate
            if (hayGanador()) {

                    System.out.println("El jugador " + jugadorActual + " ha ganado!!");
                    gameOver = true;

                } else if (hayEmpate()) {
                    System.out.println("Empate");
                    gameOver = true;
                }
            
            //Si el jugador actual es igual al del ultimo jugador, reiniciamos el contador
            if (jugadorActual>=numJugadores){
                jugadorActual=0;
                rondaActual++;
            }
            
        } while (!gameOver);
        
        mostrarTablero();
        
        System.out.println("Partida finalizada");
    }
    
    //Metodo para comprobar si algun jugador ha ganado
    public static boolean hayGanador(){
        
        //Comprobamos si algun jugador ha ganado contando filas
        for (int i = 0; i < tablero.length; i++) {
            
            for (int j = 0; j < tablero[i].length; j++) {
               
                if (tablero[i][j] != SIGNOINICIAL) {
                    
                    //Comprobamos si algun jugador ha ganado contando filas
                    if ((j < tablero[i].length-2) && tablero[i][j] == tablero[i][j+1] && tablero[i][j+1] == tablero [i][j+2]) {
                        return true;
                    }
                    //Comprobamos si algun jugador ha ganado contando columnas
                    if ((i < tablero[i].length-2) && tablero[i][j] == tablero[i+1][j] && tablero[i+1][j] == tablero [i+2][j]) {
                        return true;
                    }
                    //Comprobamos si algun jugador ha ganado contando diagonal hacia la izquierda
                    if ((i < tablero[i].length-2 && j >= 2) && tablero[i][j] == tablero[i+1][j-1] && tablero[i+1][j-1] == tablero [i+2][j-2]) {
                        return true;
                    }
                    //Comprobamos si algun jugador ha ganado contando diagonal hacia la derecha
                    if ((i < tablero.length-2 && j < tablero[i].length-2) && tablero[i][j] == tablero[i+1][j+1] && tablero[i+1][j+1] == tablero [i+2][j+2]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //Metodo para comprobar si hay alguna casilla libre en el tablero
    public static boolean hayEmpate(){
        
        /*Para comprobar el empate pasamos por todo el tablero hasta que encuentre el simbolo inicial, 
        lo que indica que hay al menos una posicion vacia */
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                
                if (tablero[i][j] == SIGNOINICIAL) {
                    return false;
                }
            }
        }
        return true;
    }
    
    //Enseñamos el tablero actual
    public static void mostrarTablero(){
        
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
        
    }
    
    //Comprueba si la posicion esta vacía y pone el signo del jugador actual
    public static void marcarTablero(int fila, int columna, char signo){
        
        if (tablero[fila-1][columna-1] == SIGNOINICIAL) {
            tablero[fila-1][columna-1] = signo;
        } else {
            throw new IllegalArgumentException();
        }
    }
}

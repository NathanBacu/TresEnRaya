/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tresEnRaya;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author irlor
 */
public class Presentacion {
    
    private final PlanDeNegocio PLANDENEGOCIO;
    private final String[] SIMBOLOSJUGADORES;
    
    private String[][] tableroVisible;
    private String idioma[];
    
    private Scanner sc;
    private boolean error;
    private int numCasillas;
    private int idiomaSeleccionado;
    
    private final String[][] TXT = {
        {"Main Menu: \n1-Play \n2-View Highscores\n3-Exit game", "Menu principal: \n1-Jugar \n2-Ver Highscores\n3-Salir del juego"},  // 0
        {"What board size do you want?", "De que tamaño quieres el tablero?"},                                                        // 1
        {"How many players will there be?", "Cuantos jugadores quieres que haya?"},                                                   // 2
        {"Player name ", "Nombre del jugador "},                                                                                      // 3
        {"Player ", "Jugador "},                                                                                                      // 4
        {"Which square do you want to mark?", "Que casilla quieres marcar?"},                                                         // 5
        {"Current round: ", "Ronda actual: "},                                                                                        // 6
        {". Current turn: ", ". Turno actual: "},                                                                                     // 7
        {"You must write a number!", "Debes escribir un numero!"},                                                                    // 8
        {" has won!", " ha ganado!"},                                                                                                 // 9
        {"No more available squares. Draw!", "No hay mas casillas disponibles. Empate!"},                                             // 10
        {"Closing game...", "Cerrando el juego..."}                                                                                   // 11
    };

    public Presentacion() {
        
        this.SIMBOLOSJUGADORES = new String[]{"X","O","-","/","%","$","#","@","!","?"};
        this.idioma = new String[]{"inglés", "español"};
        this.error = false;
        PLANDENEGOCIO = new PlanDeNegocio();
        sc = new Scanner(System.in);
        
    }
    
    /***
     * Preguntamos el tamaño del tablero y la cantidad de jugadores para empezar la partida
    */
    public void iniciar(){
        
        boolean cerrarJuego = false;
        
        seleccionarIdioma();        
        
        do {
            
            int eleccion = menuPrincipal();
            
            if (eleccion == 1){
                tamañoTablero();
                cantidadJugadores();

                //Si esta todo correcto iniciamos el juego
                iniciarJuego();
            } else if (eleccion == 2){
                ArrayList<String> historial = new ArrayList<>();
                historial = this.PLANDENEGOCIO.mostrarHistorial();
                imprimirHistorial(historial);
            } else {
                cerrarJuego = true;
            }
            
        } while (!cerrarJuego);
        
        System.out.println("Cerrando el juego...");
        sc.close();
    }
    
    /***
     * Iniciamos el juego preguntando uno a uno a los jugadores las casillas que quieren marcar
     */
    public void iniciarJuego(){
        
        boolean finalizar = false;
        
        do {
            
            mostrarTablero();
            
            int jugadorActual = PLANDENEGOCIO.getJugadorActual();
                        
            System.out.println(TXT[4][idiomaSeleccionado] + jugadorActual + " (" + 
                    (this.SIMBOLOSJUGADORES[jugadorActual-1]) + ") " + TXT[5][idiomaSeleccionado]);
            
            System.out.println(TXT[6][idiomaSeleccionado] + this.PLANDENEGOCIO.getRondaActual() + 
                    TXT[7][idiomaSeleccionado] + this.PLANDENEGOCIO.getTurnoActual());
            
            try{
                
                int casillaMarcada = sc.nextInt();
                
                marcarCasilla(casillaMarcada);
                this.error = false;
                
            }catch (InputMismatchException e){
                System.out.println(TXT[8][this.idiomaSeleccionado]);
                sc.nextLine();
                this.error = true;
            }catch(Exception e){
                System.out.println(e.getMessage());
                this.error = true;
            }
            
            //Comprobamos con sus respectivos metodos si alguien ha ganado o si hay empate
            if (this.PLANDENEGOCIO.hayGanador()){
                System.out.println(TXT[4][idiomaSeleccionado] + this.PLANDENEGOCIO.getJugadorAnterior() + TXT[9][idiomaSeleccionado]);
                finalizar = true;
            } else if (this.PLANDENEGOCIO.hayEmpate(this.numCasillas)) {
                System.out.println(TXT[10][idiomaSeleccionado]);
                finalizar = true;
            }
            
        } while (!finalizar);
        
        mostrarTablero();
        sc.nextLine();
        this.PLANDENEGOCIO.finalizarPartida();
        
    }
    
    /***
     * Bucle para mostrar todo el tablero por consola
     */
    public void mostrarTablero(){
        
        for (int i = 0; i < tableroVisible.length; i++) {
            for (int j = 0; j < tableroVisible[i].length; j++) {
                System.out.print(tableroVisible[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /***
     * Pasamos el tamaño que queremos del tablero y lo inicializamos con un contador para enumerar las posiciones
     * @param n 
     */
    public void inicializarTableroVisible(int n){
        
        int contador = 1;
        this.tableroVisible = new String[n][n];
        
        for (int i = 0; i < this.tableroVisible.length; i++) {
            for (int j = 0; j < this.tableroVisible.length; j++) {
                
                if (contador < 10) {
                    this.tableroVisible[i][j] = "0" + contador;
                } else {
                    this.tableroVisible[i][j] = contador + "";
                }
                contador++;
            }
        }
        this.numCasillas = contador-1;
    }

    /***
     * Marcamos la casilla con el simbolo del jugador actual si la casilla no ha estado previamente marcada
     * @param casilla
     * @throws Exception 
     */
    public void marcarCasilla(int casilla)throws Exception{
        
        int contador = 1;
        int jugadorActual = this.PLANDENEGOCIO.getJugadorActual();
        
        //Primero comprobamos si esta dentro de los parametros del tablero, para ahorrarnos tener que hacer el bucle
        if (casilla < 1 || casilla > this.numCasillas){
            throw new IllegalArgumentException("La casilla debe estar entre 1 y " + this.numCasillas + " incluidos");
        } else {
            
            for (int i = 0; i < this.tableroVisible.length; i++) {
                for (int j = 0; j < this.tableroVisible.length; j++) {

                    if (contador == casilla) {
                        this.PLANDENEGOCIO.marcarCasilla(i, j);
                        
                        this.tableroVisible[i][j] = this.SIMBOLOSJUGADORES[jugadorActual-1] + " ";
                        return;
                    }
                    contador++;
                }
            }
        }
    }
    
    /***
     * Pregunta y envia el tamaño de tablero al plan de negocio para que cree el tablero
     */
    public void tamañoTablero(){
        
        //Preguntamos el tamaño del tablero y comprobamos
        do {
            System.out.println(TXT[1][this.idiomaSeleccionado]);
            
            try{
                int tamaño = sc.nextInt();
                
                PLANDENEGOCIO.inicializarTablero(tamaño);
                inicializarTableroVisible(tamaño);
                this.error = false;
                
            }catch (InputMismatchException e){
                System.out.println(TXT[8][this.idiomaSeleccionado]);
                sc.nextLine();
                this.error = true;
                
            }catch(Exception e){
                System.out.println(e.getMessage());
                this.error = true;
            }
            
        } while (this.error);
                
    } 
    
    /***
     * Pregunta y envia la cantidad de jugadores al plan de negocio para que el plan de negocio lo apunte
     */
    public void cantidadJugadores(){
        
        int numJugadores = 0;
        
        //Preguntamos la cantidad de jugadores y comprobamos
        do {
            System.out.println(TXT[2][this.idiomaSeleccionado]);
            
            try{
                numJugadores = sc.nextInt();
                
                PLANDENEGOCIO.setCantidadJugadores(numJugadores);
                this.error = false;
                
            }catch (InputMismatchException e){
                System.out.println(TXT[8][this.idiomaSeleccionado]);
                sc.nextLine();
                this.error = true;
            }catch(Exception e){
                System.out.println(e.getMessage());
                this.error = true;
            }
            
        } while (this.error);
        
        //limpiamos el bufer
        sc.nextLine();
        
        //Preguntamos el nombre de cada jugador
        for (int i = 0; i < numJugadores; i++) {
            
            do {
                System.out.println(TXT[3][this.idiomaSeleccionado]);

                try{
                    String nombreJugador = sc.nextLine();
                    PLANDENEGOCIO.setNombreJugadores(nombreJugador);
                    this.error = false;                

                }catch(Exception e){
                    System.out.println(e.getMessage());
                    this.error = true;
                }
            
            } while (this.error);
            
        }
        
    }
    
    /***
     * Muestra el menu principal y pide que opcion quiere hacer el usuario
     * @return 
     */
    public int menuPrincipal(){
        
        int eleccion = 0;
        
        do {
            
            try {
            
                System.out.println(TXT[0][this.idiomaSeleccionado]);

                eleccion = sc.nextInt();
                if(eleccion < 1 || eleccion > 3){
                    this.error = true;
                } else {
                    this.error = false;
                }

            } catch (InputMismatchException e){
                System.out.println(TXT[8][this.idiomaSeleccionado]);
                sc.nextLine();
                this.error = true;
            }
            
        } while (this.error);
        
        return eleccion;
        
    }
    
    /***
     * Pide en la base de datos el historial de victorias y lo imprime
     * @param historial 
     */
    public void imprimirHistorial(ArrayList<String> historial){
        for (int i = 0; i < historial.size(); i++) {
            System.out.println(historial.get(i));
        }
        System.out.println();
    }
    
    /***
     * Pregunta al usuario el idioma deseado basándose en el array idioma.
     */
    public void seleccionarIdioma() {
        int opcion = 0;
        boolean entradaValida = false;

        do {
            System.out.println("Select language / Selecciona el idioma:");
            for (int i = 0; i < this.idioma.length; i++) {
                System.out.println((i + 1) + " - " + this.idioma[i]);
            }

            try {
                opcion = sc.nextInt();
                if (opcion >= 1 && opcion <= this.idioma.length) {
                    this.idiomaSeleccionado = opcion - 1;
                    entradaValida = true;
                } else {
                    System.out.println("Opción no válida / Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes escribir un número / You must enter a number.");
                sc.nextLine();
            }
        } while (!entradaValida);

        sc.nextLine(); // Limpieza de buffer
    }
    
    private int pedirNumero(int mensaje, int min, int max) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            if (sc.hasNextInt()) {
                valor = sc.nextInt();
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.printf("(!) El valor ha d'estar entre %d i %d.\n", min, max);
            } else {
                System.out.println(TXT[8][this.idiomaSeleccionado]);
                sc.next();
            }
        }
    }
}

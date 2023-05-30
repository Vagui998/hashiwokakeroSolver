package com.javeriana;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App 
{
    public static void main(String[] args) 
    {
        TableroHashiwokakero tablero = null;
        
        try 
        {
            tablero = new TableroHashiwokakero("tablero.txt");
            System.out.println("\nTablero: ");
            tablero.printBoard();
            
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Archivo no encontrado!");
        }
        
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (option != 3) 
        {
            System.out.println("Seleccione una opcion del menu:\n   1) Agregar un puente\n   2) Remover un puente\n   3) Salir");
            option = scanner.nextInt();
            switch(option)
            {
                case 1: 
                    System.out.println("Ingrese la Fila desde donde desea construir un puente (Se comienza con la posicion 0): ");
                    int filaOrigen = scanner.nextInt();
                    System.out.println("Ingrese la Columna desde donde desea construir un puente (Se comienza con la posicion 0): ");
                    int columnaOrigen = scanner.nextInt();
                    System.out.println("Ingrese la Fila hacia donde desea construir un puente (Se comienza con la posicion 0): ");
                    int filaDestino = scanner.nextInt();
                    System.out.println("Ingrese la Columna hacia donde desea construir un puente (Se comienza con la posicion 0): ");
                    int columnaDestino = scanner.nextInt();
                    if(filaOrigen >= 0 && filaOrigen < tablero.getCantidadFilas() && filaDestino >= 0 &&  filaDestino < tablero.getCantidadFilas() && columnaOrigen >= 0 && columnaOrigen < tablero.getCantidadColumnas() && columnaDestino >= 0 &&  columnaDestino < tablero.getCantidadColumnas())
                    {
                        if(!tablero.agregarPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino))
                        {
                            System.out.println("No se logro agregar el puente ingresado");
                        }
                        else
                        {
                            System.out.println("Se agrego el puente exitosamente!");
                        }
                        System.out.println("\nTablero: ");
                        tablero.printBoard();
                    }
                    else
                    {
                        System.out.println("Uno de los puntos ingresados se sale del tablero!");
                    }
                    break;
                case 2:
                    System.out.println("Ingrese la Fila desde donde desea eliminar un puente (Se comienza con la posicion 0): ");
                    filaOrigen = scanner.nextInt();
                    System.out.println("Ingrese la Columna desde donde desea eliminar un puente (Se comienza con la posicion 0): ");
                    columnaOrigen = scanner.nextInt();
                    System.out.println("Ingrese la Fila hacia donde desea eliminar un puente (Se comienza con la posicion 0): ");
                    filaDestino = scanner.nextInt();
                    System.out.println("Ingrese la Columna hacia donde desea eliminar un puente (Se comienza con la posicion 0): ");
                    columnaDestino = scanner.nextInt();
                    if(filaOrigen >= 0 && filaOrigen < tablero.getCantidadFilas() && filaDestino >= 0 &&  filaDestino < tablero.getCantidadFilas() && columnaOrigen >= 0 && columnaOrigen < tablero.getCantidadColumnas() && columnaDestino >= 0 &&  columnaDestino < tablero.getCantidadColumnas())
                    {
                        if(!tablero.removerPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino))
                        {
                            System.out.println("No se logro remover el puente ingresado");
                        }
                        else
                        {
                            System.out.println("Se removio el puente exitosamente!");
                        }
                        System.out.println("\nTablero: ");
                        tablero.printBoard();
                    }
                    else
                    {
                        System.out.println("Uno de los puntos ingresados se sale del tablero!");
                    }
                    break;
                case 3:
                    System.out.println("Has cerrado el juego.");
                    break;
                case 4: 
                    System.out.println("Ingrese la Fila desde donde desea construir un puente (Se comienza con la posicion 0): ");
                    filaOrigen = scanner.nextInt();
                    System.out.println("Ingrese la Columna desde donde desea construir un puente (Se comienza con la posicion 0): ");
                    columnaOrigen = scanner.nextInt();
                    System.out.println("Ingrese la Fila hacia donde desea construir un puente (Se comienza con la posicion 0): ");
                    filaDestino = scanner.nextInt();
                    System.out.println("Ingrese la Columna hacia donde desea construir un puente (Se comienza con la posicion 0): ");
                    columnaDestino = scanner.nextInt();
                    if(tablero.jugadaValida(columnaOrigen, filaOrigen, columnaDestino, filaDestino)) 
                    {
                        System.out.println("Se puede realizar la jugada");
                    } 
                    else
                    {
                        System.out.println("No se puede realizar la jugada");
                    }             
                    break;
                case 5:
                    ArrayList<int[]> jugadas = obtenerJugadasValidas(tablero);
                    if(!jugadas.isEmpty())
                    {
                        columnaOrigen = jugadas.get(0)[0];
                        filaOrigen = jugadas.get(0)[1];
                        columnaDestino = jugadas.get(0)[2];
                        filaDestino = jugadas.get(0)[3];
                        tablero.agregarPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
                        System.out.println("\nTablero: ");
                        tablero.printBoard();
                    }
                    break;
                case 6:
                    resolverTablero(tablero);
                    tablero.printBoard();
                    break;
                default:
                    System.out.println("Seleccione una opcion valida!");
            }
            if(tablero.finDeJuego())
            {
                System.out.println("Felicidades Ganaste!");
                break;
            }
        }
        scanner.close();
    }

    public static ArrayList<int[]> obtenerJugadasValidas(TableroHashiwokakero pTablero) 
    {
        ArrayList<int[]> jugadasValidas = new ArrayList<>();
        int filas = pTablero.getCantidadFilas();
        int columnas = pTablero.getCantidadColumnas();

        for (int filaOrigen = 0; filaOrigen < filas; filaOrigen++) 
        {
            for (int columnaOrigen = 0; columnaOrigen < columnas; columnaOrigen++) 
            {
                for (int filaDestino = 0; filaDestino < filas; filaDestino++) 
                {
                    for (int columnaDestino = 0; columnaDestino < columnas; columnaDestino++) 
                    {
                        if (pTablero.jugadaValida(columnaOrigen, filaOrigen, columnaDestino, filaDestino)) 
                        {
                            int[] jugada = {columnaOrigen, filaOrigen, columnaDestino, filaDestino};
                            jugadasValidas.add(jugada);
                        }
                    }
                }
            }
        }

        return jugadasValidas;
    }

    public static void printJugadasValidas(ArrayList<int[]> pJugadas)
    {
        for(int i = 0; i < pJugadas.size() ; i++)
        {
            System.out.println("Jugada: Fila Og: " + pJugadas.get(i)[1] + ", Columna Og: "  + pJugadas.get(i)[0] + ", Fila Dest: " + pJugadas.get(i)[3] + ", Columna Dest: "  + pJugadas.get(i)[2]);
        }
    }


    public static void resolverTablero(TableroHashiwokakero tablero) {
        if (tablero.finDeJuego()) {
            System.out.println("¡Felicidades, has resuelto el tablero!");
            return;
        }
    
        ArrayList<int[]> jugadasValidas = obtenerJugadasValidas(tablero);
        printJugadasValidas(jugadasValidas);
        boolean solucionEncontrada = false;
        boolean jugadaOptimaEncontrada = false;
    
        for (int[] jugada : jugadasValidas) {
            int columnaOrigen = jugada[0];
            int filaOrigen = jugada[1];
            int columnaDestino = jugada[2];
            int filaDestino = jugada[3];
    
            TableroHashiwokakero nuevoTablero = (TableroHashiwokakero) tablero.clone();
            if (nuevoTablero.jugadaOptima(columnaOrigen, filaOrigen, columnaDestino, filaDestino)) {
                nuevoTablero.agregarPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
                nuevoTablero.printBoard();
                jugadaOptimaEncontrada = true;
    
                if (esTableroValido(nuevoTablero)) {
                    resolverTablero(nuevoTablero);  // Llamada recursiva para continuar resolviendo el tablero
    
                    if (nuevoTablero.finDeJuego()) {
                        solucionEncontrada = true;
                        return;  // Se encontró una solución, se detiene la recursión
                    }
    
                    // Si la llamada recursiva no llevó a una solución, se deshace la jugada
                    nuevoTablero.removerPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
                }
            }
        }
    
        // If no optimal move was found, choose any valid move
        if (!jugadaOptimaEncontrada) {
            for (int[] jugada : jugadasValidas) {
                int columnaOrigen = jugada[0];
                int filaOrigen = jugada[1];
                int columnaDestino = jugada[2];
                int filaDestino = jugada[3];
    
                TableroHashiwokakero nuevoTablero = (TableroHashiwokakero) tablero.clone();
                nuevoTablero.agregarPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
                nuevoTablero.printBoard();
    
                if (esTableroValido(nuevoTablero)) {
                    resolverTablero(nuevoTablero);  // Llamada recursiva para continuar resolviendo el tablero
    
                    if (nuevoTablero.finDeJuego()) {
                        solucionEncontrada = true;
                        return;  // Se encontró una solución, se detiene la recursión
                    }
    
                    // Si la llamada recursiva no llevó a una solución, se deshace la jugada
                    nuevoTablero.removerPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
                }
            }
        }
    
        if (!solucionEncontrada) {
            System.out.println("No se encontró una solución para el tablero");
        }
    }
    
    

    public static boolean esTableroValido(TableroHashiwokakero pTablero)
    {
        return pTablero.esValido();
    }

}

package com.javeriana;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class App 
{
    public static void main(String[] args) 
    {
        TableroHashiwokakero tablero = null;
        boolean finDeJuego = false;
        
        try 
        {
            tablero = new TableroHashiwokakero("tablero3.txt");
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
                    if(resolverTablero(tablero))
                    {
                        finDeJuego = true;
                    }
                    break;
                default:
                    System.out.println("Seleccione una opcion valida!");
            }
            if(tablero.finDeJuego() || finDeJuego)
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
                            if (columnaOrigen == columnaDestino && filaOrigen > filaDestino) {
                                int aux = filaOrigen;
                                int nuevaFilaOrigen = filaDestino;
                                int nuevaFilaDestino = aux;
                                int[] jugada = {columnaOrigen, nuevaFilaOrigen, columnaDestino, nuevaFilaDestino};
                                if (!jugadaRepetida(jugadasValidas, jugada)) {
                                    jugadasValidas.add(jugada);
                                }
                            } else if (filaOrigen == filaDestino && columnaOrigen > columnaDestino) {
                                int aux = columnaOrigen;
                                int nuevaColumnaOrigen = columnaDestino;
                                int nuevaColumnaDestino = aux;
                                int[] jugada = {nuevaColumnaOrigen, filaOrigen, nuevaColumnaDestino, filaDestino};
                                if (!jugadaRepetida(jugadasValidas, jugada)) {
                                    jugadasValidas.add(jugada);
                                }
                            }
                                                     
                        }
                    }
                }
            }
        }

        return jugadasValidas;
    }

    private static boolean jugadaRepetida(ArrayList<int[]> pJugadasValidas, int[] pJugada)
    {
        for(int[] comparada : pJugadasValidas)
        {
            if(Arrays.equals(comparada, pJugada))
            {
                return true;
            }
        }
        return false;
    }

    public static void printJugadasValidas(ArrayList<int[]> pJugadas)
    {
        for(int i = 0; i < pJugadas.size() ; i++)
        {
            System.out.println("Jugada: Fila Og: " + pJugadas.get(i)[1] + ", Columna Og: "  + pJugadas.get(i)[0] + ", Fila Dest: " + pJugadas.get(i)[3] + ", Columna Dest: "  + pJugadas.get(i)[2]);
        }
    }

    public static boolean resolverTablero(TableroHashiwokakero tablero) {
        if (tablero.finDeJuego()) {
            System.out.println("¡Felicidades, has resuelto el tablero!");
            return true;
        }
    
        ArrayList<int[]> jugadasValidas = obtenerJugadasValidas(tablero);
    
        // Ordenar jugadas validas en orden descendiente por Heuristica
        Collections.sort(jugadasValidas, new ComparadorHeuristica(tablero));
    
        for (int[] jugada : jugadasValidas) {
            int columnaOrigen = jugada[0];
            int filaOrigen = jugada[1];
            int columnaDestino = jugada[2];
            int filaDestino = jugada[3];
    
            TableroHashiwokakero nuevoTablero = (TableroHashiwokakero) tablero.clone();
            nuevoTablero.agregarPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
            nuevoTablero.printBoard();
    
            if (nuevoTablero.esValido()) {
                boolean solucionEncontrada = resolverTablero(nuevoTablero); // Llamada recursiva para continuar resolviendo el tablero
    
                // Actualizar jugadasValidas despues de la llamada recursiva
                jugadasValidas = obtenerJugadasValidas(nuevoTablero);
    
                if (solucionEncontrada) {
                    return true; // Propagate the solution found status and stop the recursion.
                }
    
                // Regresion de la movida si no se encuentra una solucion
                nuevoTablero.removerPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
            }
        }
    
        return false; // No solution found for the current tablero.
    }
    

}

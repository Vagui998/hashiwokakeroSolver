package com.javeriana;

import java.util.Comparator;

public class ComparadorHeuristica implements Comparator<int[]> {
    private TableroHashiwokakero tablero;

    public ComparadorHeuristica(TableroHashiwokakero tablero) 
    {
        this.tablero = tablero;
    }

    @Override
    public int compare(int[] jugada1, int[] jugada2) {
        // Evaluar Heuristica de Todas las Jugadas
        int score1 = calcularHeuristica(jugada1);
        int score2 = calcularHeuristica(jugada2);

        // Comparar resultados y retornar respuesta
        return Integer.compare(score2, score1); // Orden descendiente
    }

    private int calcularHeuristica(int[] jugada) {
        int columnaOrigen = jugada[0];
        int filaOrigen = jugada[1];
        int columnaDestino = jugada[2];
        int filaDestino = jugada[3];
    
        int heuristica = 0;
    
        // Utilizar "importancia" de nodo 
        int importanciaOrigen = tablero.getImportanciaNodo(columnaOrigen, filaOrigen);
        int importanciaDestino = tablero.getImportanciaNodo(columnaDestino, filaDestino);
    
        // combinar importancia de los nodos en la jugada
        heuristica += importanciaOrigen + importanciaDestino;
    
        // Evaluar potencial de puentes
        int potencialPuentes = calcularPotencialPuente(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
        heuristica += potencialPuentes;
    
        // Evaluar conectividad de nodos
        int conectividad = calcularConectividad(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
        heuristica += conectividad;
    
        // Evaluar proximidad de los nodos
        int proximidad = calcularProximidadNodos(columnaOrigen, filaOrigen, columnaDestino, filaDestino);

        heuristica += proximidad;
    
        return heuristica;
    }
    
    private int calcularPotencialPuente(int columnaOrigen, int filaOrigen, int columnaDestino, int filaDestino) {
        int potencial = 0;
        if(tablero.getNodo(filaOrigen, columnaOrigen).getNumeroConexionesRestantes() == 1 && tablero.getNodo(filaOrigen, columnaOrigen).getValorDeseado() > 2)
        {
            potencial += 40;
        }
        if(tablero.getNodo(filaDestino, columnaDestino).getNumeroConexionesRestantes() == 1 && tablero.getNodo(filaDestino, columnaDestino).getValorDeseado() > 2)
        {
            potencial += 40;
        }
        if(tablero.getNodo(filaOrigen, columnaOrigen).getNumeroConexionesRestantes() == 0)
        {
            potencial -= 1000;
        }
        if(tablero.getNodo(filaDestino, columnaDestino).getNumeroConexionesRestantes() == 0)
        {
            potencial -= 1000;
        }
        
        return potencial;
    }
    
    private int calcularConectividad(int columnaOrigen, int filaOrigen, int columnaDestino, int filaDestino) {
        int conectividad = 0;
        if(tablero.getNodo(filaOrigen, columnaOrigen).getCantidadVecinos() == 1)
        {
            conectividad += 40;
        }
        if(tablero.getNodo(filaDestino, columnaDestino).getCantidadVecinos() == 1)
        {
            conectividad += 40;
        }
        return conectividad;
    }
    
    private int calcularProximidadNodos(int columnaOrigen, int filaOrigen, int columnaDestino, int filaDestino) {
        int proximidad = 0;
        proximidad = (Math.abs(columnaOrigen-columnaDestino) + Math.abs(filaOrigen-filaDestino))*8 ;
        return proximidad;
    }
    
}
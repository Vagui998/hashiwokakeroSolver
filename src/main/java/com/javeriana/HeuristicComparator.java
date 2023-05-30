package com.javeriana;

import java.util.Comparator;

public class HeuristicComparator implements Comparator<int[]> {
    private TableroHashiwokakero tablero;

    public HeuristicComparator(TableroHashiwokakero tablero) 
    {
        this.tablero = tablero;
    }

    @Override
    public int compare(int[] jugada1, int[] jugada2) {
        // Evaluate the heuristic score for each jugada
        int score1 = evaluateHeuristic(jugada1);
        int score2 = evaluateHeuristic(jugada2);

        // Compare the scores and return the result
        return Integer.compare(score2, score1); // Inverted order for descending order
    }

    private int evaluateHeuristic(int[] jugada) {
        int columnaOrigen = jugada[0];
        int filaOrigen = jugada[1];
        int columnaDestino = jugada[2];
        int filaDestino = jugada[3];
    
        int score = 0;
    
        // Evaluate the importance of the islands involved in the move
        int importanceOrigen = tablero.getIslandImportance(columnaOrigen, filaOrigen);
        int importanceDestino = tablero.getIslandImportance(columnaDestino, filaDestino);
    
        // Assign higher scores to moves involving important islands
        score += importanceOrigen + importanceDestino;
    
        // Evaluate bridge potential
        int bridgePotential = calculateBridgePotential(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
        score += bridgePotential;
    
        // Evaluate island connectivity
        int connectivity = calculateIslandConnectivity(columnaOrigen, filaOrigen, columnaDestino, filaDestino);
        score += connectivity;
    
        // Incorporate island proximity and distance metrics
        int proximity = calculateIslandProximity(columnaOrigen, filaOrigen, columnaDestino, filaDestino);

        score += proximity;
    
        return score;
    }
    
    private int calculateBridgePotential(int columnaOrigen, int filaOrigen, int columnaDestino, int filaDestino) {
        // Calculate the potential of the move to create bridges
        // and return a score based on the potential
        // Implement your logic here
        int score = 0;
        if(tablero.getNodo(filaOrigen, columnaOrigen).getNumeroConexionesRestantes() == 1)
        {
            score += 5;
        }
        if(tablero.getNodo(filaDestino, columnaDestino).getNumeroConexionesRestantes() == 1)
        {
            score += 5;
        }
        if(tablero.getNodo(filaOrigen, columnaOrigen).getNumeroConexionesRestantes() == 0)
        {
            score -= 40;
        }
        if(tablero.getNodo(filaDestino, columnaDestino).getNumeroConexionesRestantes() == 0)
        {
            score -= 40;
        }
        
        return score;
    }
    
    private int calculateIslandConnectivity(int columnaOrigen, int filaOrigen, int columnaDestino, int filaDestino) {
        // Evaluate the connectivity of the islands involved in the move
        // and return a score based on the connectivity
        // Implement your logic here
        int score = 0;
        if(tablero.getNodo(filaOrigen, columnaOrigen).getCantidadVecinos() == 1)
        {
            score += 20;
        }
        if(tablero.getNodo(filaDestino, columnaDestino).getCantidadVecinos() == 1)
        {
            score += 20;
        }
        return score;
    }
    
    private int calculateIslandProximity(int columnaOrigen, int filaOrigen, int columnaDestino, int filaDestino) {
        // Evaluate the proximity of the islands involved in the move
        // and return a score based on the proximity
        // Implement your logic here
        int score = 0;
        score = 10 - Math.abs(columnaOrigen-columnaDestino)  - Math.abs(filaOrigen-filaDestino) ;
        return score;
    }
    
}
package com.javeriana;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import lombok.Getter;

public class TableroHashiwokakero implements Cloneable
{
    private int[][] tablero;
    @Getter
    private int cantidadFilas;
    @Getter
    private int cantidadColumnas;
    private ArrayList<Nodo> nodos;
    
    public TableroHashiwokakero(String pRutaArchivo) throws FileNotFoundException 
    {
        File archivo = new File(pRutaArchivo);
        Scanner scanner = new Scanner(archivo);
        
        String[] dimensiones = scanner.nextLine().split(",");
        cantidadFilas = Integer.parseInt(dimensiones[0].trim());
        cantidadColumnas = Integer.parseInt(dimensiones[1].trim());
        
        tablero = new int[cantidadFilas*2][cantidadColumnas*2];
        nodos = new ArrayList<Nodo>();
        
        for (int i = 0; i < cantidadFilas*2; i++) 
        {
            String fila = scanner.nextLine();
            for (int j = 0; j < cantidadColumnas; j++) 
            {
                int indiceColumna = j * 2;
                tablero[i][indiceColumna] = Character.getNumericValue(fila.charAt(j));
                if(tablero[i][indiceColumna]>0)
                {
                    nodos.add(new Nodo(i/2, j, tablero[i][indiceColumna]));
                }
                tablero[i][indiceColumna+1] = 0;
            }
            i++;
            for (int j = 0; j < cantidadColumnas*2; j++) 
            {
                tablero[i][j] = 0;
            }
        } 
        scanner.close();
        actualizarVecinos();
    }
    
    public void printBoard() 
    {
        for (int i = 0; i < cantidadFilas*2; i++) 
        {
            for (int j = 0; j < cantidadColumnas*2; j++) 
            {
                if(tablero[i][j] > 0)
                {
                    System.out.print(tablero[i][j] + " ");
                }
                else if(tablero[i][j] == 0) 
                {
                    System.out.print( "  ");
                }
                else if(tablero[i][j] == -1) 
                {
                    System.out.print( "| ");
                }
                else if(tablero[i][j] == -2) 
                {
                    System.out.print( "||");
                }
                else if(tablero[i][j] == -3) 
                {
                    System.out.print( "- ");
                }
                else if(tablero[i][j] == -4) 
                {
                    System.out.print( "= ");
                }
            }
            System.out.println();
        }
    }

    public boolean agregarPuente(int pColumnaOrigen, int pFilaOrigen, int pColumnaDestino, int pFilaDestino)
    {
        boolean agrego = false;

        if(!existeNodo(pFilaDestino, pColumnaDestino) || !existeNodo(pFilaOrigen, pColumnaOrigen))
        {
            System.out.println("En uno de los puntos ingresados no existe un nodo.");
        }
        else if(pColumnaOrigen == pColumnaDestino || pFilaOrigen == pFilaDestino)
        {
            if(pColumnaOrigen == pColumnaDestino && pFilaOrigen == pFilaDestino)
            {
                System.out.println("El punto de origen debe ser diferente al destino.");
            }
            else
            {
                if(pColumnaDestino == pColumnaOrigen)
                {
                    if(pFilaDestino < pFilaOrigen)
                    {
                        int temp = pFilaOrigen;
                        pFilaOrigen = pFilaDestino;
                        pFilaDestino = temp;
                    }
                    if(tablero[pFilaOrigen*2+1][pColumnaDestino*2]==-2)
                    {
                        System.out.println("El maximo numero de puentes entre nodos es dos!");
                    }
                    else
                    {
                        for(int i = pFilaOrigen*2+1 ; i <= pFilaDestino*2 ; i++)
                        {
                            if(tablero[i][pColumnaOrigen*2] < -2)
                            {
                                System.out.println("Los nodos a conectar no pueden estar interceptados por otros puentes");
                                break;
                            }
                            else if(tablero[i][pColumnaOrigen*2] > 0)
                            {
                                if(i == pFilaDestino*2)
                                {
                                    for(int j = pFilaOrigen*2+1 ; j < pFilaDestino*2 ; j++)
                                    {
                                        tablero[j][pColumnaOrigen*2] --;
                                        
                                    }
                                    agregarPuenteNodo(pFilaDestino, pColumnaDestino);
                                    agregarPuenteNodo(pFilaOrigen, pColumnaOrigen);

                                    agrego = true;
                                    actualizarVecinos();
                                }
                                else
                                {
                                    System.out.println("Los nodos a conectar no pueden estar interceptados por otros nodos");
                                    break;
                                }
                            }
                            
                        }
                    }

                }
                else if(pFilaOrigen == pFilaDestino)
                {
                    if(pColumnaDestino < pColumnaOrigen)
                    {
                        int temp = pColumnaOrigen;
                        pColumnaOrigen = pColumnaDestino;
                        pColumnaDestino = temp;
                    }
                    if(tablero[pFilaOrigen*2][pColumnaOrigen*2+1]==-4)
                    {
                        System.out.println("El maximo numero de puentes entre nodos es dos!");
                    }
                    else
                    {
                        for(int j = pColumnaOrigen*2+1 ; j <= pColumnaDestino*2 ; j++)
                        {
                            if(tablero[pFilaOrigen*2][j] > -3 && tablero[pFilaOrigen*2][j] < 0)
                            {
                                System.out.println("Los nodos a conectar no pueden estar interceptados por otros puentes");
                                break;
                            }
                            if(tablero[pFilaOrigen*2][j] > 0)
                            {
                                if(j == pColumnaDestino*2)
                                {
                                    for(int k = pColumnaOrigen*2+1 ; k < pColumnaDestino*2 ; k++)
                                    {
                                        if(tablero[pFilaOrigen*2][k] == -3)
                                        {
                                            tablero[pFilaOrigen*2][k] --;
                                        }
                                        else
                                        {
                                            tablero[pFilaOrigen*2][k] = -3;
                                        }                                       
                                    }
                                    agregarPuenteNodo(pFilaDestino, pColumnaDestino);
                                    agregarPuenteNodo(pFilaOrigen, pColumnaOrigen);
                                    agrego = true;
                                    actualizarVecinos();
                                }
                                else
                                {
                                    System.out.println("Los nodos a conectar no pueden estar interceptados por otros nodos");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("Los puentes deben ir en linea recta, no se pueden construir diagonalmente.");
        }
        return agrego;
    }

    public boolean removerPuente(int pColumnaOrigen, int pFilaOrigen, int pColumnaDestino, int pFilaDestino)
    {
        boolean removido = false;
        if(!existeNodo(pFilaDestino, pColumnaDestino) || !existeNodo(pFilaOrigen, pColumnaOrigen))
        {
            System.out.println("En uno de los puntos ingresados no existe un nodo.");
        }
        else if(pColumnaOrigen == pColumnaDestino || pFilaOrigen == pFilaDestino)
        {
            if(pColumnaOrigen == pColumnaDestino && pFilaOrigen == pFilaDestino)
            {
                System.out.println("El punto de origen debe ser diferente al destino.");
            }
            else
            {
                if(pColumnaDestino == pColumnaOrigen)
                {
                    if(pFilaDestino < pFilaOrigen)
                    {
                        int temp = pFilaDestino;
                        pFilaDestino = pFilaOrigen;
                        pFilaOrigen = temp;
                    }
                    if(tablero[pFilaOrigen*2+1][pColumnaDestino*2] == 0)
                    {
                        System.out.println("No hay un puente en esa posición");
                    }
                    else
                    {
                        for(int i = pFilaOrigen*2+1 ; i <= pFilaDestino*2 ; i++)
                        {
                            if(tablero[i][pColumnaOrigen*2] < -2)
                            {
                                break;
                            }
                            else if(tablero[i][pColumnaOrigen*2] > 0)
                            {
                                if(i == pFilaDestino*2)
                                {
                                    for(int j = pFilaOrigen*2+1 ; j < pFilaDestino*2 ; j++)
                                    {
                                        tablero[j][pColumnaOrigen*2] ++;
                                    }
                                    removerPuenteNodo(pFilaDestino, pColumnaDestino);
                                    removerPuenteNodo(pFilaOrigen, pColumnaOrigen);
                                    removido = true;
                                    actualizarVecinos();
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
                else if(pFilaOrigen == pFilaDestino)
                {
                    if(pColumnaDestino < pColumnaOrigen)
                    {
                        int temp = pColumnaDestino; 
                        pColumnaDestino = pColumnaOrigen;
                        pColumnaOrigen = temp;
                    }
                    if(tablero[pFilaOrigen*2][pColumnaOrigen*2+1] == 0)
                    {
                        System.out.println("No hay un puente en esa posición");
                    }
                    else
                    {
                        for(int j = pColumnaOrigen*2+1 ; j <= pColumnaDestino*2 ; j++)
                        {
                            if(tablero[pFilaOrigen*2][j] > -3 && tablero[pFilaOrigen*2][j] < 0)
                            {
                                break;
                            }
                            else if(tablero[pFilaOrigen*2][j] > 0)
                            {
                                if(j == pColumnaDestino*2)
                                {
                                    for(int k = pColumnaOrigen*2+1 ; k < pColumnaDestino*2 ; k++)
                                    {
                                        if(tablero[pFilaOrigen*2][k] == -3)
                                        {
                                            tablero[pFilaOrigen*2][k] = 0;
                                        }
                                        else
                                        {
                                            tablero[pFilaOrigen*2][k] ++;
                                        }
                                        removerPuenteNodo(pFilaDestino, pColumnaDestino);
                                        removerPuenteNodo(pFilaOrigen, pColumnaOrigen);
                                        removido = true;
                                        actualizarVecinos();
                                    }
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("Los nodos deben estar en la misma fila o en la misma columna.");
        }
        return removido;
    }

    public boolean existeNodo(int pFila, int pColumna)
    {
        boolean existe = false;
        if(tablero[pFila*2][pColumna*2]>0)
        {
            existe = true;
        }
        return existe;
    }

    public Nodo getNodo(int pFila, int pColumna)
    {
        if(existeNodo(pFila, pColumna))
        {
            for(int i = 0; i < nodos.size(); i++)
            {
                if(nodos.get(i).getFila() == pFila && nodos.get(i).getColumna() == pColumna)
                {
                    return nodos.get(i);
                }
            }
        }
        return null;
    }

    public boolean agregarPuenteNodo(int pFila, int pColumna)
    {
        boolean respuesta = false;
        for(int i = 0; i < nodos.size() && respuesta==false; i++)
        {
            if(nodos.get(i).getFila() == pFila && nodos.get(i).getColumna() == pColumna)
            {
                respuesta = nodos.get(i).agregarPuenteConectado();
            }
        }
        return respuesta;
    }

    public boolean removerPuenteNodo(int pFila, int pColumna)
    {
        boolean respuesta = false;
        for(int i = 0; i < nodos.size() && respuesta==false; i++)
        {
            if(nodos.get(i).getFila() == pFila && nodos.get(i).getColumna() == pColumna)
            {
                respuesta = nodos.get(i).removerPuenteConectado();
            }
        }
        return respuesta;
    }

    public boolean finDeJuego()
    {
        boolean respuesta = true;
        for(int i = 0; i < nodos.size() && respuesta == true; i++)
        {
            Nodo revisado = nodos.get(i);
            if(revisado.getPuentesConectados() != revisado.getValorDeseado())
            {
                respuesta = false;
            }
        }
        return respuesta;
    }


    public boolean jugadaValida(int pColumnaOrigen, int pFilaOrigen, int pColumnaDestino, int pFilaDestino) 
    {
        boolean agrego = false;

        if(!existeNodo(pFilaDestino, pColumnaDestino) || !existeNodo(pFilaOrigen, pColumnaOrigen))
        {
            return false;
        }
        else if(pColumnaOrigen == pColumnaDestino || pFilaOrigen == pFilaDestino)
        {
            if(pColumnaOrigen == pColumnaDestino && pFilaOrigen == pFilaDestino)
            {
                return false;
            }
            else
            {
                if(pColumnaDestino == pColumnaOrigen)
                {
                    if(pFilaDestino < pFilaOrigen)
                    {
                        int temp = pFilaOrigen;
                        pFilaOrigen = pFilaDestino;
                        pFilaDestino = temp;
                    }
                    if(tablero[pFilaOrigen*2+1][pColumnaDestino*2]==-2)
                    {
                        return false;
                    }
                    else
                    {
                        for(int i = pFilaOrigen*2+1 ; i <= pFilaDestino*2 ; i++)
                        {
                            if(tablero[i][pColumnaOrigen*2] < -2)
                            {
                                break;
                            }
                            else if(tablero[i][pColumnaOrigen*2] > 0)
                            {
                                if(i == pFilaDestino*2)
                                {
                                    agrego = true;
                                }
                                else
                                {
                                    break;
                                }
                            }
                            
                        }
                    }

                }
                else if(pFilaOrigen == pFilaDestino)
                {
                    if(pColumnaDestino < pColumnaOrigen)
                    {
                        int temp = pColumnaOrigen;
                        pColumnaOrigen = pColumnaDestino;
                        pColumnaDestino = temp;
                    }
                    if(tablero[pFilaOrigen*2][pColumnaOrigen*2+1]==-4)
                    {
                        return false;
                    }
                    else
                    {
                        for(int j = pColumnaOrigen*2+1 ; j <= pColumnaDestino*2 ; j++)
                        {
                            if(tablero[pFilaOrigen*2][j] > -3 && tablero[pFilaOrigen*2][j] < 0)
                            {
                                break;
                            }
                            if(tablero[pFilaOrigen*2][j] > 0)
                            {
                                if(j == pColumnaDestino*2)
                                {
                                    agrego = true;
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return agrego;
    }

    public boolean esValido()
    {
        boolean valido = true;
        for(int i = 0 ; i < nodos.size() ; i++)
        {
            if(nodos.get(i).getValorDeseado() < nodos.get(i).getPuentesConectados())
            {
                return false;
            }
        }
        return valido;
    }

    public void actualizarVecinos()
    {
        for(int i = 0; i < nodos.size() ; i++)
        {
            int fila = nodos.get(i).getFila();
            int columna = nodos.get(i).getColumna();
            if((fila * 2 + 1) <  cantidadFilas*2)
            {
                for(int j = (fila * 2) + 1 ; j < cantidadFilas*2 ; j++)
                {
                    if(tablero[j][columna*2]>0)
                    {
                        nodos.get(i).setCantidadVecinos(nodos.get(i).getCantidadVecinos()+1);
                        break;
                    }
                    else if(tablero[j][columna*2]<-2)
                    {
                        break;
                    }
                }
            }
           
            if((fila * 2) - 1 >= 0)
            {
                for(int j = (fila * 2) - 1 ; j >= 0 ; j--)
                {
                    if(tablero[j][columna*2]>0)
                    {
                        nodos.get(i).setCantidadVecinos(nodos.get(i).getCantidadVecinos()+1);
                        break;
                    }
                    else if(tablero[j][columna*2]<-2)
                    {
                        break;
                    }
                }
            }

            if((columna * 2 + 1) <  cantidadColumnas*2)
            {
                for(int j = (columna * 2) + 1 ; j < cantidadColumnas*2 ; j++)
                {
                    if(tablero[fila*2][j]>0)
                    {
                        nodos.get(i).setCantidadVecinos(nodos.get(i).getCantidadVecinos()+1);
                        break;
                    }
                    else if(tablero[fila*2][j] < 0 && tablero[fila*2][j] > -3)
                    {
                        break;
                    }
                }
            }
           
            if((columna * 2) - 1 >= 0)
            {
                for(int j = (columna * 2) - 1 ; j >= 0 ; j--)
                {
                    if(tablero[fila*2][j]>0)
                    {
                        nodos.get(i).setCantidadVecinos(nodos.get(i).getCantidadVecinos()+1);
                        break;
                    }
                    else if(tablero[fila*2][j]< 0 && tablero[fila*2][j] > -3)
                    {
                        break;
                    }
                }
            }
        }
    }
 
    @Override
    public TableroHashiwokakero clone() {
        try {
            TableroHashiwokakero cloned = (TableroHashiwokakero) super.clone();
            cloned.tablero = new int[cantidadFilas*2][cantidadColumnas*2];
            for (int i = 0; i < cantidadFilas*2; i++) {
                System.arraycopy(tablero[i], 0, cloned.tablero[i], 0, cantidadColumnas*2);
            }
            cloned.nodos = new ArrayList<>();
            for (Nodo nodo : nodos) {
                cloned.nodos.add((Nodo)nodo.clone());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e); 
        }
    }

    public int getImportanciaNodo(int pColumna, int pFila) {
        boolean encontro = false;
        for (Nodo nodo : nodos) 
        {
            if(!encontro)
            {
                if (nodo.getColumna() == pColumna && nodo.getFila() == pFila) {
                    int valorDeseadoNodo = nodo.getValorDeseado();
                    encontro = true;
                    
                    if(valorDeseadoNodo == 8)
                    {
                        return 40;
                    }
                    if((pColumna == 0 && pFila == 0 ) || (pColumna == 0 && pFila == cantidadFilas-1 ) || (pColumna == cantidadColumnas-1 && pFila == 0 ) || (pColumna == cantidadColumnas-1 && pFila == cantidadFilas-1 ) )
                    {
                        if(valorDeseadoNodo == 4 || valorDeseadoNodo == 3)
                        return 40;
                    }
                    else if(pColumna == 0 || pFila == 0 || pColumna == cantidadColumnas-1 || pFila == cantidadFilas-1)
                    {
                        if(valorDeseadoNodo == 5 || valorDeseadoNodo == 6)
                        return 40;
                    }
                    if(nodo.getNumeroConexionesRestantes() > 0 && nodo.getCantidadVecinos() == 1)
                    {
                        return 40;
                    }
                    int importance = valorDeseadoNodo*3;
                    
                    return importance;
                }
            }

        }
        
        return 0;
    }
    
  
}

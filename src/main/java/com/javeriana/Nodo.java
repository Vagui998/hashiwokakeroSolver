package com.javeriana;

import lombok.Getter;

public class Nodo implements Cloneable {
    @Getter
    private int fila;

    @Getter
    private int columna;

    @Getter
    private int puentesConectados;

    @Getter
    private int valorDeseado;

    public Nodo(int pFila, int pColumna, int pValorDeseado) {
        puentesConectados = 0;
        fila = pFila;
        columna = pColumna;
        valorDeseado = pValorDeseado;
    }

    public boolean agregarPuenteConectado() {
        boolean respuesta = false;

        if (puentesConectados < 8) {
            puentesConectados++;
            respuesta = true;
        }

        return respuesta;
    }

    public boolean removerPuenteConectado() {
        boolean respuesta = false;

        if (puentesConectados > 0) {
            puentesConectados--;
            respuesta = true;
        }

        return respuesta;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Nodo cloned = (Nodo) super.clone();
        return cloned;
    }
}

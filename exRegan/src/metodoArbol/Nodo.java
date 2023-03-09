/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/
package metodoArbol;

import java.util.ArrayList;

public class Nodo {
    public String token;
    public Nodo hijoIzq;
    public Nodo hijoDer;
    public boolean hoja = false;
    public int num;
    public boolean anulable;
    public ArrayList<Integer> primeros = new ArrayList<>();
    public ArrayList<Integer> ultimos = new ArrayList<>();

    public boolean isAnulable() {
        return anulable;
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public Nodo(String token) {
        this.token = token;
    }

    public Nodo getHijoIzq() {
        return hijoIzq;
    }

    public void setHijoIzq(Nodo hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public Nodo getHijoDer() {
        return hijoDer;
    }

    public void setHijoDer(Nodo hijoDer) {
        this.hijoDer = hijoDer;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }

    public boolean isHoja() {
        return hoja;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<Integer> getPrimeros() {
        return primeros;
    }

    public ArrayList<Integer> getUltimos() {
        return ultimos;
    }

}

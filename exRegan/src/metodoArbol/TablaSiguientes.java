/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/
package metodoArbol;
import java.util.ArrayList;

public class TablaSiguientes {
    public int hoja;
    public String lexema;
    public ArrayList<Integer> listaSiguientes;
    
    public TablaSiguientes(int hoja, String lexema, ArrayList<Integer> listaSiguientes){
        this.hoja = hoja;
        this.lexema = lexema;
        this.listaSiguientes = listaSiguientes;
    }

    public void setHoja(int hoja) {
        this.hoja = hoja;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setListaSiguientes(ArrayList<Integer> listaSiguientes) {
        this.listaSiguientes = listaSiguientes;
    }
    
    
}

/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/
package metodoArbol;
import java.util.ArrayList;
        

public class tablaSiguiente {
    public void append(int numNodo, String lexema, ArrayList listaSiguiente, ArrayList <ArrayList> tabla){
        for(ArrayList item : tabla){
            if( (int) item.get(0)  == numNodo && item.get(1) == lexema){
                for(Object itemSiguiente : listaSiguiente){
                    if( !((ArrayList)item.get(2)).contains((int)itemSiguiente)){
                        ((ArrayList)item.get(2)).add(itemSiguiente);
                    }
                }
                return;
            }
        }
        ArrayList dato = new ArrayList();
        dato.add(numNodo);
        dato.add(lexema);
        dato.add(listaSiguiente);
        
        tabla.add(dato);
    }
    
    public ArrayList siguiente(int numNodo, ArrayList<ArrayList> tabla){
        ArrayList result = new ArrayList();
        for(ArrayList item : tabla){
            if((int) item.get(0) == numNodo){
                result.add(item.get(1));
                result.add(((ArrayList)item.get(2)).clone());
                return result;
            }
        }
        result.add("");
        result.add(new ArrayList());
        return result;
    }
    
    public void printTabla(ArrayList<ArrayList> tabla){
        for(ArrayList item : tabla){
            System.out.println(item.get(0) + " - "+item.get(1) + " - "+item.get(2));
        }
    }
}

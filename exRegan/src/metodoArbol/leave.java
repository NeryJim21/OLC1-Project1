/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/
package metodoArbol;
import java.util.ArrayList;

public class leave {
    
    public void addLeave(node nodo, ArrayList<node> leaves){
        leaves.add(nodo);
    }
    
    public node getLeave(int numLeave, ArrayList<node> leaves){
        for(node item : leaves){
            if(item.num == numLeave){
                return item;
            }
        }
        return null;
    }
    
    public boolean isAccept(int numLeave, ArrayList<node> leaves){
        for(node item : leaves){
            if(item.num == numLeave){
                return item.acept;
            }
        }
        return false;
    }
}

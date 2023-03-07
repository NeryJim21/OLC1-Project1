/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/
package metodoArbol;

import java.util.ArrayList;
import metodoArbol.Tipo.Types;
public class node {
    ArrayList<Integer> primero;
    ArrayList<Integer> ultimo;
    boolean anulable;
    String lexema;
    Types tipo;
    int num;
    boolean acept;
   
    Object izq;
    Object der;
    
    ArrayList<node> hojas;
    ArrayList<ArrayList> tabla;

    public node(String lexema, Types tipo, int num, Object izq, Object der, ArrayList<node> hojas, ArrayList<ArrayList> tabla) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.num = num;
        this.izq = izq;
        this.der = der;
        this.hojas = hojas;
        this.tabla = tabla;
        primero = new ArrayList();
        ultimo = new ArrayList();
        anulable = true;
        acept = "#".equals(this.lexema);
    }
    
    public node getNode(){
        Object nodoIzq = this.izq instanceof node ? ((node) this.izq).getNode():null;
        Object nodoDer = this.der instanceof node ? ((node) this.der).getNode():null;
        
        if(null != this.tipo){
            switch(this.tipo){
                case HOJA:
                    this.anulable = false;
                    this.primero.add(this.num);
                    this.ultimo.add(this.num);
                    break;
                case AND:
                    if(nodoIzq instanceof node && nodoDer instanceof node){
                        this.anulable = ((node)nodoIzq).anulable && ((node) nodoDer).anulable;
                        
                        this.primero.addAll(((node)nodoIzq).primero);
                        if(((node)nodoIzq).anulable){
                            this.primero.addAll(((node)nodoDer).primero);
                        }
                        
                        if(((node)nodoDer).anulable){
                            this.ultimo.addAll(((node)nodoIzq).ultimo);
                        }
                        this.ultimo.addAll(((node)nodoDer).ultimo);
                    }
                    break;
                case OR:
                    if(nodoIzq instanceof node && nodoDer instanceof node){
                        this.anulable = ((node)nodoIzq).anulable || ((node)nodoDer).anulable;
                        
                        this.primero.addAll(((node)nodoIzq).primero);
                        this.primero.addAll(((node)nodoDer).primero);
                        
                        this.ultimo.addAll(((node)nodoIzq).ultimo);
                        this.ultimo.addAll(((node)nodoDer).ultimo);
                    }
                    break;
                case KLEENE:
                    if(nodoIzq instanceof node){
                        this.anulable = true;
                        this.primero.addAll(((node)nodoIzq).primero);
                        this.ultimo.addAll(((node)nodoIzq).ultimo);
                    }
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    public Object siguiente(){
        Object siguienteIzq = this.izq instanceof node ? ((node)this.izq).siguiente() : null;
        Object siguienteDer = this.der instanceof node ? ((node)this.der).siguiente() : null;
        
        if(null != this.tipo){
            switch(this.tipo){
                case AND:
                    for(int item : ((node)siguienteIzq).ultimo){
                       leave hoja = new leave();
                       node nodo = hoja.getLeave(item, hojas);
                       tablaSiguiente table = new tablaSiguiente();
                       table.append(nodo.num, nodo.lexema,((node)siguienteDer).primero, tabla);
                    }
                    break;
                case KLEENE:
                    for(int item : ((node)siguienteIzq).ultimo){
                        leave hoja = new leave();
                        node nodo = hoja.getLeave(item, hojas);
                        tablaSiguiente table = new tablaSiguiente();
                        table.append(nodo.num, nodo.lexema, ((node) siguienteIzq).primero,tabla);
                    }
                    break;
                default:
                    break;
            }
        }   
        return this;
    }
}

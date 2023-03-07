/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/

package metodoArbol;
import java.util.ArrayList;

public class MetodoArbol {
    public static void main(String[] args) {
        String er = "...ab*b*|ba";
        ArrayList<node> leaves = new ArrayList();
        ArrayList<ArrayList> table = new ArrayList();

        er = "." + er + "#";

        Tree arbol = new Tree(er, leaves, table); //Crea el arbol
        node raiz = arbol.getRoot();
        
        raiz.getNode();
        raiz.siguiente();
        
        System.out.println("---------------------------------------------- T A B L A   D E   S I G U I E N T E S   ----------------------------------------------");
        tablaSiguiente ft = new tablaSiguiente();
        ft.printTabla(table);
        transitionTable tran = new transitionTable(raiz, table, leaves); //bug
        System.out.println("----------------------------------------------   T A B L A   D E   T R A N S I C I O N E S   ----------------------------------------------");
        tran.impTable();
        System.out.println("----------------------------------------------   D O T   ----------------------------------------------");
        tran.impGraph();
    }
    
    
}

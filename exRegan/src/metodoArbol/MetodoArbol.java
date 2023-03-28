/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/

package metodoArbol;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MetodoArbol {
    public Nodo arbolExpresiones;
    public int numNodo = 1;
    public int numDot = 1;
    //public ArrayList tablaSiguientes;
    ArrayList <ArrayList> tablaSiguientes = new ArrayList<>();
     ArrayList <ArrayList> tablaOrdenada = new ArrayList<>();
     ArrayList <ArrayList> tablaTransiciones = new ArrayList<>();
    
    public MetodoArbol(Nodo arbolExpresiones) {
        numDot = numRand();
        Nodo raiz = new Nodo(".");
        Nodo acepta = new Nodo("#");
        acepta.setHoja(true);
        acepta.setAnulable(false);
        raiz.setHijoDer(acepta);
        raiz.setHijoIzq(arbolExpresiones);
        this.arbolExpresiones = raiz;
        asignarIndice(this.arbolExpresiones);
        numNodo = 0;
        anulables(this.arbolExpresiones);
        imprimirSiguientes(Integer.toString(numDot));
        ArrayList<Integer> primeros = raiz.getPrimeros();
        tablaTransicion(primeros,Integer.toString(numDot));
        generarDot(crearArbol(this.arbolExpresiones,numNodo),Integer.toString(numDot));
        numDot++;
    }
    
    public int numRand(){
        int min = 1;
        int max = 100;
        Random random = new Random();
        int numR = random.nextInt(max - min + 1) + min;
        return numR;
    }
    
    public void asignarIndice(Nodo actual){
        if(actual == null){
            return;
        }
        if(actual.isHoja()){
            actual.setNum(numNodo);
            numNodo++;
            return;
        }
        
        asignarIndice(actual.getHijoIzq());
        asignarIndice(actual.getHijoDer());
        
    }
    
    public void anulables(Nodo actual){
        if(actual == null){
            return;
        }
        
        if(actual.isHoja()){
            actual.getPrimeros().add(actual.getNum());
            actual.getUltimos().add(actual.getNum()); 
            return;
        }
        
        anulables(actual.getHijoIzq());
        anulables(actual.getHijoDer());
        
        switch (actual.getToken()) {
            case ".":
                actual.setAnulable(actual.getHijoIzq().isAnulable() && actual.getHijoDer().isAnulable());
                if(actual.getHijoIzq().isAnulable()){
                    actual.getPrimeros().addAll(actual.getHijoIzq().getPrimeros());
                    actual.getPrimeros().addAll(actual.getHijoDer().getPrimeros());
                }else{
                    actual.getPrimeros().addAll(actual.getHijoIzq().getPrimeros());
                }
                if(actual.getHijoDer().isAnulable()){
                    actual.getUltimos().addAll(actual.getHijoIzq().getUltimos());
                    actual.getUltimos().addAll(actual.getHijoDer().getUltimos());
                }else{
                    actual.getUltimos().addAll(actual.getHijoDer().getUltimos()); //Validar que esté correcto
                }
                //Creando tabla de siguientes
                ArrayList<Integer> follow = new ArrayList<>();
                follow = actual.getHijoIzq().getUltimos();
                ArrayList<Integer> listaFollow = new ArrayList<>();
                listaFollow = actual.getHijoDer().getPrimeros();
                boolean alerta = false;
                for(int item : follow){
                    if(tablaSiguientes != null){
                        for (ArrayList ts : tablaSiguientes) {
                            if(ts.get(1).equals(item)){
                                ArrayList<Integer> pivL = (ArrayList<Integer>) ts.get(2);
                                for(int pivItem : listaFollow){
                                    if(!pivL.contains(pivItem)){
                                        pivL.add(pivItem);
                                    }
                                }
                                ts.set(2, pivL);
                                alerta = true;
                            }
                        }
                    }
                    if(alerta != true){
                        ArrayList tabla = new ArrayList();
                        String token = recorridoArbol(actual,item);
                        tabla.add(token);
                        tabla.add(item);
                        tabla.add(listaFollow);
                        tablaSiguientes.add(tabla);
                    }
                    alerta = false;
                }
               if("#".equals(actual.getHijoDer().getToken())){
                    //Creando tabla de siguientes
                    ArrayList tabla = new ArrayList();
                    tabla.add("#");
                    tabla.add(actual.getHijoDer().getUltimos().get(0));
                    tabla.add("--");
                    tablaSiguientes.add(tabla);
                }
                break;
            case "*":
                actual.setAnulable(true);
                actual.getPrimeros().addAll(actual.getHijoIzq().getPrimeros());
                actual.getUltimos().addAll(actual.getHijoIzq().getUltimos());
                //Creando tabla de siguientes
                 ArrayList<Integer> follow1 = new ArrayList<>();
                 follow1 = actual.getHijoIzq().getUltimos();
                 ArrayList<Integer> listaFollow1 = new ArrayList<>();
                 listaFollow1 = actual.getHijoIzq().getPrimeros();
                 boolean alerta1 = false;
                 for(int item : follow1){
                     if(tablaSiguientes != null){
                        for (ArrayList ts : tablaSiguientes) {
                            if(ts.get(1).equals(item)){
                                ArrayList<Integer> pivL = (ArrayList<Integer>) ts.get(2);
                                for(int pivItem : listaFollow1){
                                    if(!pivL.contains(pivItem)){
                                        pivL.add(pivItem);
                                    }
                                }
                                ts.set(2, pivL);
                                alerta1 = true;
                            }
                        }
                    }
                        if(alerta1 != true){
                           ArrayList tabla = new ArrayList();
                           String token1 = recorridoArbol(actual,item);
                           tabla.add(token1);
                           tabla.add(item);
                           tabla.add(listaFollow1);
                           tablaSiguientes.add(tabla);    
                        }
                        alerta1 = false;
                    }
                break;
            case "+":
                actual.setAnulable(actual.getHijoIzq().isAnulable());
                actual.getPrimeros().addAll(actual.getHijoIzq().getPrimeros());
                actual.getUltimos().addAll(actual.getHijoIzq().getUltimos());
                //Creando tabla de siguientes
                 ArrayList<Integer> follow2 = new ArrayList<>();
                 follow2 = actual.getHijoIzq().getUltimos();
                 ArrayList<Integer> listaFollow2 = new ArrayList<>();
                 listaFollow2 = actual.getHijoIzq().getPrimeros();
                 boolean alerta2 = false;
                 for(int item : follow2){
                     if(tablaSiguientes != null){
                        for (ArrayList ts : tablaSiguientes) {
                            if(ts.get(1).equals(item)){
                                ArrayList<Integer> pivL = (ArrayList<Integer>) ts.get(2);
                                for(int pivItem : listaFollow2){
                                    if(!pivL.contains(pivItem)){
                                        pivL.add(pivItem);
                                    }
                                }
                                ts.set(2, pivL);
                                alerta2 = true;
                            }
                        }
                    }
                        if(alerta2 != true){
                           ArrayList tabla = new ArrayList();
                           String token2 = recorridoArbol(actual,item);
                           tabla.add(token2);
                           tabla.add(item);
                           tabla.add(listaFollow2);
                           tablaSiguientes.add(tabla);    
                        }
                        alerta2 = false;
                    }
                  
                break;
            case "|":
                actual.setAnulable(actual.getHijoIzq().isAnulable() || actual.getHijoDer().isAnulable());
                actual.getPrimeros().addAll(actual.getHijoIzq().getPrimeros());
                actual.getPrimeros().addAll(actual.getHijoDer().getPrimeros());
                actual.getUltimos().addAll(actual.getHijoIzq().getUltimos());
                actual.getUltimos().addAll(actual.getHijoDer().getUltimos());
                break;
            case "?":
                actual.setAnulable(true);
                actual.getPrimeros().addAll(actual.getHijoIzq().getPrimeros());
                actual.getUltimos().addAll(actual.getHijoIzq().getUltimos());
                break;
            default:
                break;
        }
        
        
    }
    
    public String crearArbol(Nodo node, int padre){
        String txt = "";
        numNodo += 1;
        int actual = numNodo;
        
        if(node == null){
            numNodo -= 1;
            return txt;
        }
        
        if(node.isHoja()){
            txt += "N" + actual + "[shape = none label=<\n"+
                    " <TABLE bgcolor = \"aquamarine1\" border= \"1\" cellspacing=\"2\" cellpadding=\"10\" >\n"+
                    " <TR>\n"+
                    " <TD colspan=\"3\" bgcolor=\"lightsalmon\">"+node.isAnulable()+"</TD>\n"+
                    " </TR>\n"+
                    " <TR>\n"+
                    "<TD bgcolor=\"palegreen1\">"+node.getPrimeros()+"</TD>\n"+
                    "<TD>"+node.getToken()+"</TD>\n"+
                    "<TD bgcolor = \"hotpink1\">"+node.getUltimos()+"</TD>\n"+
                    "</TR>\n"+
                    " <TR>\n"+
                    "<TD colspan=\"3\" bgcolor=\"lemonchiffon1\"> "+node.getNum()+"</TD>\n"+
                    "</TR>\n"+
                    "</TABLE>>];";
        }else{
            txt += "N" + actual + "[shape = none label=<\n"+
                    " <TABLE bgcolor = \"aquamarine1\" border= \"1\" cellspacing=\"2\" cellpadding=\"10\" >\n"+
                    " <TR>\n"+
                    " <TD colspan=\"3\" bgcolor=\"lightsalmon\">"+node.isAnulable()+"</TD>\n"+
                    " </TR>\n"+
                    " <TR>\n"+
                    "<TD bgcolor=\"palegreen1\">"+node.getPrimeros()+"</TD>\n"+
                    "<TD>"+node.getToken()+"</TD>\n"+
                    "<TD bgcolor = \"hotpink1\">"+node.getUltimos()+"</TD>\n"+
                    "</TR>\n"+
                    "</TABLE>>];";
        }
        
        if(padre != 0){
            txt += "N"+padre +" -> N" + actual + ";\n";
        }
        
        txt += crearArbol(node.getHijoIzq(),actual);
        txt += crearArbol(node.getHijoDer(), actual);
        
        return txt;
    }
    
    public void generarDot(String dot,String i){
        FileWriter fichero = null;
        PrintWriter escritor = null;
        String path = "/home/n21/Documentos/USAC/OLC1/LAB/PROYECTOS/PROYECTO1/repo/OLC1-Project1/ARBOLES_201700381/";
        String name = "";
        String nameJPG = "";
        try{
            
            name +="Tree"+i+".dot";
            nameJPG ="Tree"+i+".jpg";
            path += name;
            fichero = new FileWriter(path);
            escritor = new PrintWriter(fichero);
            String pivDot = "digraph G { \n";
            pivDot += dot + "\n }";
            escritor.println(pivDot);
            escritor.close();
            fichero.close();
        } catch(Exception e){
            System.out.println("Error al generar .dot");
        }
        
        try{
            Runtime rt = Runtime.getRuntime();
            String imprimir = " dot -Tjpg  "+name + " -o "+nameJPG; 
            rt.exec(imprimir);
            System.out.println(imprimir);
            Thread.sleep(500);
            System.out.println("JPG Arbol Generado");
        }catch(Exception ex){
            System.out.println("Error al generar la imagen JPG");
        }
        
    }
    
    public String recorridoArbol(Nodo actual,int numHoja){
        if(actual == null){
            return "";
        }
        
        if(actual.getHijoIzq() == null && actual.getHijoDer() == null && actual.getNum() == numHoja){
            return String.valueOf(actual.getToken());
        }
        
        String resultadoIzquierdo = recorridoArbol(actual.getHijoIzq(),numHoja);
        
        if(!resultadoIzquierdo.isEmpty()){
            return /*String.valueOf(actual.getToken()) + */resultadoIzquierdo;
        }
        
        String resultadoDerecho = recorridoArbol(actual.getHijoDer(),numHoja);
        
        if(!resultadoDerecho.isEmpty()){
            return /*String.valueOf(actual.getToken()) + */resultadoDerecho;
        }
         return "";
    }
    
    public void imprimirSiguientes(String i){
        
        //Ordenar ascendente
        for(int j = 1; j<=tablaSiguientes.size();j++){
            for(ArrayList ts2 : tablaSiguientes){
                if(ts2.get(1).equals(j)){
                    tablaOrdenada.add(ts2);
                }
            }
        }
      
        String txt = "";
        txt += "digraph { \n tbl1 [\n"+
                "shape=plaintext\n" +
                " label=<\n" +
                "<table border='0' cellborder='1' cellspacing='0'>\n"+
                "<tr><td colspan=\"3\">TABLA DE SIGUIENTES</td></tr>\n" +
                "<th><td>Simbolo</td><td>Hoja</td><td>Siguientes</td></th>"+
                "";       
        for(ArrayList ts : tablaOrdenada){
            txt += "<tr><td bgcolor=\"#fcc8c8\">"+ts.get(0)+"</td><td bgcolor=\"#fcc8c8\">"+ts.get(1)+"</td><td bgcolor=\"#fcc8c8\">"+ts.get(2)+"</td></tr>\n";
        }
        
        txt += "</table>\n" +
                  ">];\n" +
                  "}";
    
        //Generando DOT
        FileWriter fichero = null;
        PrintWriter escritor = null;
        String path = "/home/n21/Documentos/USAC/OLC1/LAB/PROYECTOS/PROYECTO1/repo/OLC1-Project1/SIGUIENTES_201700381/";
        String name = "";
        String nameJPG = "";
        try{
            
            name +="Siguientes"+i+".dot";
            nameJPG ="Siguientes"+i+".jpg";
            path += name;
            fichero = new FileWriter(path);
            escritor = new PrintWriter(fichero);
            escritor.println(txt);
            escritor.close();
            fichero.close();
        } catch(Exception e){
            System.out.println("Error al generar .dot");
        }
        
        try{
            Runtime rt = Runtime.getRuntime();
            String imprimir = " dot -Tjpg  "+name + " -o "+nameJPG; 
            rt.exec(imprimir);
            System.out.println(imprimir);
            Thread.sleep(500);
            System.out.println("JPG Tabla Siguientes Generado");
        }catch(Exception ex){
            System.out.println("Error al generar la imagen JPG");
        }
    }
    
    public void tablaTransicion(ArrayList<Integer> primeros, String i){
        ArrayList <String> items = new ArrayList<>();
        items.add("S0");
        items.add(String.valueOf(primeros));
        items.add("S1");
        tablaTransiciones.add(items);
        int contador = 0;
        for(ArrayList to : tablaOrdenada){
            if(!to.get(0).equals("#")){
                ArrayList<Integer> pivote = (ArrayList<Integer>) to.get(2);
                contador = armandoTransicion((int) to.get(1),contador);
            } else {
                break;
            }
            //contador++;
        }
        System.out.println("Tabla Transiciones: "+tablaTransiciones);
        
        String txt = "";
        txt += "Digraph {\n" +
                "tbl [\n" +
                "shape=plaintext\n" +
                " label=<\n" +
                "<table border='0' cellborder='1' cellspacing='0'>\n" +
                "<tr><td colspan=\""+tablaOrdenada.size()+"\">TABLA TRANSICIONES</td></tr>\n" +
                "<th><td>Estado</td>";
        for(ArrayList next : tablaOrdenada){
            if(!next.get(0).equals("#")){
                txt += "<td> "+next.get(0)+"</td>";
            } 
            
        }
        txt += "</th\n>";
        txt += "<tr><td bgcolor=\"lightblue\">"+tablaTransiciones.get(0).get(0)+tablaTransiciones.get(0).get(1)+"</td>";
        ArrayList<ArrayList> tt = new ArrayList<>();
        for(int j = 1; j<tablaTransiciones.size();j++){
            tt = tablaTransiciones.get(j);
            for(int k = 0; k<tablaOrdenada.size()-1;k++){
                System.out.println("1: "+String.valueOf(tablaOrdenada.get(k).get(2)));
                System.out.println("2: "+tt.get(1));
                if(String.valueOf(tablaOrdenada.get(k).get(2)).equals(tt.get(1))){
                    txt += "<td bgcolor=\"lightblue\">"+tt.get(0)+"</td>";
                }else{
                    txt += "<td bgcolor=\"lightblue\">--</td>";
                }
                
            }
            txt += "</tr>\n";
            
            txt += "<tr><td bgcolor=\"lightblue\">"+tt.get(0)+tt.get(1)+"</td>";
            System.out.println("j: "+j);
            
        }
        for(int k =0; k<tablaOrdenada.size()-1;k++){
                 if(String.valueOf(tablaOrdenada.get(k).get(2)).equals(tt.get(1))){
                    txt += "<td bgcolor=\"lightblue\">"+tt.get(0)+"</td>";
                }else{
                    txt += "<td bgcolor=\"lightblue\">--</td>";
                }
            }
            txt += "</tr>\n";
    
        txt += "</table>\n" +
                ">];\n" +
                "}";
        
         //Generando DOT
        FileWriter fichero = null;
        PrintWriter escritor = null;
        String path = "/home/n21/Documentos/USAC/OLC1/LAB/PROYECTOS/PROYECTO1/repo/OLC1-Project1/TRANSICIONES_201700381/";
        String name = "";
        String nameJPG = "";
        try{
            
            name +="Transiciones"+i+".dot";
            nameJPG ="Transiciones"+i+".jpg";
            path += name;
            fichero = new FileWriter(path);
            escritor = new PrintWriter(fichero);
            escritor.println(txt);
            escritor.close();
            fichero.close();
        } catch(Exception e){
            System.out.println("Error al generar .dot");
        }
        
        try{
            Runtime rt = Runtime.getRuntime();
            String imprimir = " dot -Tjpg  "+name + " -o "+nameJPG; 
            rt.exec(imprimir);
            System.out.println(imprimir);
            Thread.sleep(500);
            System.out.println("JPG Tabla Transiciones Generado");
        }catch(Exception ex){
            System.out.println("Error al generar la imagen JPG");
        }
    }
    
    public int armandoTransicion(int item,int contador){
        ArrayList<String> transiciones = new ArrayList<>();
        boolean alerta = false;
        for(ArrayList flw : tablaOrdenada){
            if(flw.get(1).equals(item)){
                System.out.println("flw"+flw);
                String pivS = String.valueOf(flw.get(2));
                for(ArrayList tt : tablaTransiciones){
                    if(tt.get(1).equals(pivS)){
                        tt.add(String.valueOf(flw.get(0)));
                        alerta = true;
                    }
                }
                if(!alerta){
                    contador++;
                    transiciones.add("S"+contador);
                    transiciones.add(String.valueOf(flw.get(2)));
                    transiciones.add(String.valueOf(flw.get(0)));
                    tablaTransiciones.add(transiciones);
                }
                alerta = false;
                return contador;
            }
        }
        return contador;
    }
}

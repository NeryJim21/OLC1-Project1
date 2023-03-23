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

public class MetodoArbol {
    public Nodo arbolExpresiones;
    public int numNodo = 1;
    public int numDot = 1;
    //public ArrayList tablaSiguientes;
    ArrayList <ArrayList> tablaSiguientes = new ArrayList<>();
    
    public MetodoArbol(Nodo arbolExpresiones) {
        Nodo raiz = new Nodo(".");
        Nodo acepta = new Nodo("#");
        acepta.setHoja(true);
        acepta.setAnulable(false);
        raiz.setHijoDer(acepta);
        raiz.setHijoIzq(arbolExpresiones);
        this.arbolExpresiones = raiz;
        asignarIndice(this.arbolExpresiones);
        numNodo = 0;
//        ArrayList <ArrayList> tablaSiguientes = new ArrayList<>();
        anulables(this.arbolExpresiones);
        System.out.println("Tabla siguientes:"+tablaSiguientes);
        //System.out.println(crearArbol(this.arbolExpresiones,numNodo));
        generarDot(crearArbol(this.arbolExpresiones,numNodo),Integer.toString(numDot));
        numDot++;
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
                    actual.getUltimos().addAll(actual.getHijoIzq().getUltimos());
                }
                
                if(!"#".equals(actual.getHijoDer().getToken())){
                    //Creando tabla de siguientes
                    ArrayList<Integer> follow = new ArrayList<>();
                    follow = actual.getHijoIzq().getUltimos();
                    ArrayList<Integer> listaFollow = new ArrayList<>();
                    listaFollow = actual.getHijoDer().getPrimeros();
                    String token = actual.getHijoIzq().getToken();
                    for(int item : follow){
                        ArrayList tabla = new ArrayList();
                        tabla.add(token);
                        tabla.add(item);
                        tabla.add(listaFollow);
                        tablaSiguientes.add(tabla);
                    }
                }else{
                    //Creando tabla de siguientes
                    ArrayList<Integer> follow = new ArrayList<>();
                    follow = actual.getHijoIzq().getUltimos();
                    ArrayList<Integer> listaFollow = new ArrayList<>();
                    listaFollow = actual.getHijoDer().getPrimeros();
                    for(int item : follow){
                        ArrayList tabla = new ArrayList();
                        tabla.add("#");
                        tabla.add(item);
                        tabla.add("--");
                        tablaSiguientes.add(tabla);
                    }
                }
                break;
            case "*":
                actual.setAnulable(true);
                actual.getPrimeros().addAll(actual.getHijoIzq().getPrimeros());
                actual.getUltimos().addAll(actual.getHijoIzq().getUltimos());
                //Creando tabla de siguientes
                 ArrayList<Integer> follow = new ArrayList<>();
                 follow = actual.getHijoIzq().getUltimos();
                 ArrayList<Integer> listaFollow = new ArrayList<>();
                 listaFollow = actual.getHijoIzq().getPrimeros();
                 String token = actual.getHijoIzq().getToken();
                 for(int item : follow){
                     ArrayList tabla = new ArrayList();
                     tabla.add(token);
                     tabla.add(item);
                     tabla.add(listaFollow);
                     tablaSiguientes.add(tabla);
                    }
                break;
            case "+":
                actual.setAnulable(actual.getHijoIzq().isAnulable());
                actual.getPrimeros().addAll(actual.getHijoIzq().getPrimeros());
                actual.getUltimos().addAll(actual.getHijoIzq().getUltimos());
                //Creando tabla de siguientes
                ArrayList<Integer> flw = new ArrayList<>();
                flw = actual.getHijoIzq().getUltimos();
                ArrayList<Integer> listFollow = new ArrayList<>();
                listFollow = actual.getHijoIzq().getPrimeros();
                String tokn = actual.getHijoIzq().getToken();
                for(int item : flw){
                    ArrayList tabla = new ArrayList();
                    tabla.add(tokn);
                    tabla.add(item);
                    tabla.add(listFollow);
                    tablaSiguientes.add(tabla);
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
            System.out.println("JPG Generado");
        }catch(Exception ex){
            System.out.println("Error al generar la imagen JPG");
        }
        
    }
    
}

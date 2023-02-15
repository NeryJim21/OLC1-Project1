/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/

package analizador;

public class Generador {
    public static void main(String[] args) {
        try{
            String ruta = "src/analizador/";
            String opcFlex[] = {ruta + "Lexer.flex", "-d", ruta};
            jflex.Main.generate(opcFlex);
            
            String opcCup[] = {"-destdir", ruta, "-parser", "Parser", ruta + "Parser.cup"};
            java_cup.Main.main(opcCup);
        }catch(Exception e){
        }
    }
}

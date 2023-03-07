/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/
package metodoArbol;

public final class numLeave {
    public int content;
    
    public numLeave(String content){
        this.content = clean(content)+1;
    }
    
    public int getNum(){
        content -= 1;
        return content;
    }
    
    public int clean(String content){
        return content.replace(".", "").replace("|","").replace("*","").replace("?","").replace("+","").length();
    }
}

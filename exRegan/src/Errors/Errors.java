/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/
package Errors;

public class Errors {
    public String type;
    public String description;
    public String line;
    public String column;

    public Errors(String type, String description, String line, String column) {
        this.type = type;
        this.description = description;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "Errors{" + "type=" +this.type + ", description=" + this.description + ", line=" + this.line + ", column=" + this.column + '}';
    }
    
    
}

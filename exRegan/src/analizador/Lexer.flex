/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/

package analizador;
import java_cup.runtime.Symbol;
import Errors.Errors;
import java.util.ArrayList;
%%

%{
    //Codigo en sintaxis java
    public ArrayList<Errors> Errores = new ArrayList();
%}

%class Lexer
%public
%cup
%char
%column
%line
%unicode

%init{
    yyline = 1;
    yychar = 1;
%init}

BLANCOS = [ \t\r\f\n]
ENTERO = [0-9]+
DECIMAL  = {ENTERO} ("."? [0-9]* )?
COMILLA = [\"]
LETRA = [a-zA-ZñÑ]+
ID = ({LETRA}|("_"{LETRA}))({LETRA}|{ENTERO}|"_")*
UNILINEA = ("//".*\r\n)|("//".*\n)|("//".*\r)
MULTILINEA = "<!""<"*([^*<]|[^*]"!"|"*"[^!])*"*"*"!>"
ASCII = [!-/]|[:-@]|[\[-`]|[{-~]

%%

"\n" {yychar=0;}
{BLANCOS} {}
{MULTILINEA}  {}
{UNILINEA} {}

":" { return new Symbol(sym.DOSPUNTOS, yyline, yychar, yytext());} 
";" { return new Symbol(sym.PTCOMA, yyline, yychar, yytext());}
"{" { return new Symbol(sym.LLAVABRE, yyline, yychar, yytext());} 
"}" { return new Symbol(sym.LLAVCIERRA, yyline, yychar, yytext());} 

"+" {return new Symbol(sym.MAS, yyline, yychar, yytext());} 
"*" {return new Symbol(sym.POR, yyline, yychar, yytext());} 
"->" {return new Symbol(sym.FLECHA, yyline, yychar, yytext());}
"~" {return new Symbol(sym.COLOCHO, yyline, yychar, yytext());}
"." {return new Symbol(sym.PUNTO, yyline, yychar, yytext());} 

"|" {return new Symbol(sym.OR, yyline, yychar, yytext());}
"%" {return new Symbol(sym.PORCENTAJE, yyline, yychar, yytext());}
"?" {return new Symbol(sym.INTERROG, yyline, yychar, yytext());}
"," {return new Symbol(sym.COMA, yyline, yychar, yytext());}
"\\n" {return new Symbol(sym.LN, yyline, yychar, yytext());}
"\\'" {return new Symbol(sym.COMSIMPLE, yyline, yychar, yytext());}
"\\\"" {return new Symbol(sym.COMDOBLE, yyline, yychar, yytext());}

"CONJ" {return new Symbol(sym.PR_CONJ, yyline, yychar, yytext());}
{COMILLA} {return new Symbol(sym.COMILLA, yyline, yychar, yytext());}
{ID} {return new Symbol(sym.ID, yyline, yychar, yytext());}
{ENTERO} {return new Symbol(sym.ID, yyline, yychar, yytext());}
{DECIMAL} {return new Symbol(sym.ID, yyline, yychar, yytext());}
{ASCII} {return new Symbol(sym.ASCII, yyline, yychar, yytext());}

. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.
    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
    Errores.add(new Errors("Lexico", "Caracter no valido detectado: " + yytext(), yyline + "", yychar + ""));
}
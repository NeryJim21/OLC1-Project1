/*
* ORGANIZACIÓN DE LENGUAJES Y COMPILADORES 1 SECCIÓN C
* PROYECTO 1 - EXREGAN
* PRIMER SEMESTRE 2023
* NERY JIMÉNEZ - 201700381
*/

package analizador;
import java_cup.runtime.Symbol;

%%

%{
    //Codigo en sintaxis java
%}

%class Lexer
%public
%cup
%char
%column
%full
%line
%unicode

%init{
    yyline = 1;
    yychar = 1;
%init}

BLANCOS = [ \r\t]+
ENTERO = [0-9]+
DECIMAL  = {ENTERO} ("."? [0-9]* )?
CADENA =[\"] [^\"\n]* [\"\n]
LETRA = [a-zA-ZñÑ]+
ID = ({LETRA}|("_"{LETRA}))({LETRA}|{ENTERO}|"_")*
UNILINEA = ("//".*\r\n)|("//".*\n)|("//".*\r)
MULTILINEA = "<!""<"*([^*<]|[^*]"!"|"*"[^!])*"*"*"!>"
ASCII = [ -&(-/:-@\[-`{-~]

%%

{BLANCOS} {}
{MULTILINEA}  {}
{UNILINEA} {}

":" { System.out.println("Reconocio "+yytext()+" dos puntos"); return new Symbol(sym.DOSPUNTOS, yyline, yychar, yytext());} 
";" { System.out.println("Reconocio "+yytext()+" punto y coma"); return new Symbol(sym.PTCOMA, yyline, yychar, yytext());} 
"(" { System.out.println("Reconocio "+yytext()+" parentesis abre"); return new Symbol(sym.PARABRE, yyline, yychar, yytext());} 
")" { System.out.println("Reconocio "+yytext()+" parentesis cierra"); return new Symbol(sym.PARCIERRA, yyline, yychar, yytext());} 
"[" { System.out.println("Reconocio "+yytext()+" corchete abre"); return new Symbol(sym.CORABRE, yyline, yychar, yytext());} 
"]" { System.out.println("Reconocio "+yytext()+" corchete cierra"); return new Symbol(sym.CORCIERRA, yyline, yychar, yytext());} 
"{" { System.out.println("Reconocio "+yytext()+" llave abre"); return new Symbol(sym.LLAVABRE, yyline, yychar, yytext());} 
"}" { System.out.println("Reconocio "+yytext()+" llave cierra"); return new Symbol(sym.LLAVCIERRA, yyline, yychar, yytext());} 

"+" {return new Symbol(sym.MAS, yyline, yychar, yytext());} 
"-" {return new Symbol(sym.MENOS, yyline, yychar, yytext());} 
"*" {return new Symbol(sym.POR, yyline, yychar, yytext());} 
"/" {return new Symbol(sym.DIV, yyline, yychar, yytext());} 
"->" {return new Symbol(sym.FLECHA, yyline, yychar, yytext());}
"~" {return new Symbol(sym.COLOCHO, yyline, yychar, yytext());}
"." {return new Symbol(sym.PUNTO, yyline, yychar, yytext());} 

"|" {return new Symbol(sym.OR, yyline, yychar, yytext());}
"%" {return new Symbol(sym.PORCENTAJE, yyline, yychar, yytext());}
"?" {return new Symbol(sym.INTERROG, yyline, yychar, yytext());}
"," {return new Symbol(sym.COMA, yyline, yychar, yytext());}
"\\n" {System.out.println("Reconocio "+yytext()+" salto de linea"); return new Symbol(sym.LN, yyline, yychar, yytext());}
"\\'" {System.out.println("Reconocio "+yytext()+" comilla simple"); return new Symbol(sym.COMSIMPLE, yyline, yychar, yytext());}
"\\\"" {System.out.println("Reconocio "+yytext()+" comilla doble"); return new Symbol(sym.COMDOBLE, yyline, yychar, yytext());}
\n {yychar=1;}

"CONJ" {return new Symbol(sym.PR_CONJ, yyline, yychar, yytext());}
{CADENA} {return new Symbol(sym.CADENA, yyline, yychar, yytext());}
{ID} {return new Symbol(sym.ID, yyline, yychar, yytext());}
{ENTERO} {return new Symbol(sym.ID, yyline, yychar, yytext());}
{DECIMAL} {return new Symbol(sym.ID, yyline, yychar, yytext());}
{ASCII} {return new Symbol(sym.ASCII, yyline, yychar, yytext());}

. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.
    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
}
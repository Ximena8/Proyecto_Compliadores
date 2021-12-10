package co.edu.uniquindio.compiladores.proyecto

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico

fun main(){
    // val lexico = AnalizadorLexico(codigoFuente = "8ap433. .9 . P973 \n 79 12.43 .|| \\n|| |||| ||| ||\\\\  \n|| ")
    val lexico = AnalizadorLexico(codigoFuente= "metodo ")
    lexico.analizar()
    var sintaxis= AnalizadorSintactico(lexico.ListaTokens)
    print( sintaxis.esUnidadDeCompilacion())
    print(lexico.ListaTokens)
}
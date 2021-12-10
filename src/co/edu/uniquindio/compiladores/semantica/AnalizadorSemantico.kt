package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.sintaxis.UnidadCompilacion

class AnalizadorSemantico (var unidadDeCompilacion :UnidadCompilacion) {

    var erroresSemanticos: ArrayList<Error> = ArrayList()
    var tablaSimbolos: TablaSimbolos = TablaSimbolos(erroresSemanticos)

    fun llenarTablaSimbolos() {
        unidadDeCompilacion.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos)
    }
    fun analizarSemantica() {
        unidadDeCompilacion.analizarSemantica(tablaSimbolos, erroresSemanticos)
    }

}
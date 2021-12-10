package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Expresion {

    open fun getArbolVisual():TreeItem<String>
    {
        return TreeItem("sentencia")
    }
    open fun obtenerTipo(tablaSimbolos:TablaSimbolos, ambito:String, erroresSemanticos:ArrayList<Error>):String?
    {
        return ""
    }
    open fun analizarSemantica(tablaSimbolos:TablaSimbolos, ambito:String,erroresSemanticos:ArrayList<Error>){

    }
    open  fun obtenerFilaColumna()
    {

    }
    open  fun getJavaCode():String
    {
        return ""
    }

}
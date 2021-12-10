package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Sentencia {

    open fun getArbolVisual():TreeItem<String>
    {
        return TreeItem("sentencia")
    }

    open   fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos:ArrayList<Error>,ambito: String,tipos: ArrayList<String>)
    {

    }
    open fun analizarSemantica(tablaSimbolos:TablaSimbolos, erroresSemanticos:ArrayList<Error>,ambito: String,tipos:ArrayList<String>)
    {

    }
    open  fun getJavaCode():String
    {
        return ""
    }

}
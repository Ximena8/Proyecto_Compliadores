package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Argumento(var exp:ExpresionCadena) {

    override fun toString(): String{
        return "Argumento(exp=$exp)"
    }
    fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Argumento")
        var raizExpre = TreeItem("Expresion")
        raizExpre.children.add(exp.getArbolVisual())
        raiz.children.add(raizExpre)
        return raiz
    }
    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        exp.analizarSemantica(tablaSimbolos, ambito,erroresSemanticos)
    }

    fun getJavaCode():String
    {
        var codigo=""+exp.getJavaCode()
        return codigo
    }

}
package co.edu.uniquindio.compiladores.sintaxis

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


}
package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

/**
 * <Retorno>::= retornar"."<esExpresion>  ";"
 */

class Retorno(var exp:Expresion) : Sentencia() {

    override fun toString(): String{
        return "Retorno(exp=$exp)"
    }

    override fun getArbolVisual():TreeItem<String>
    {
        var raiz =TreeItem("Retorno")

        var raizExpre =TreeItem("Expresion")
        raizExpre.children.add(exp.getArbolVisual())
        raiz.children.add(raizExpre)

        return raiz

    }

}
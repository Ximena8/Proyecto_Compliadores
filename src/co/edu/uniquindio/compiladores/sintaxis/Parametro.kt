package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem


class Parametro(var tipoDato:Token , var identificador:Token){

    override fun toString(): String {
        return "Parametro(tipoDato=$tipoDato, identificador=$identificador)"
    }

    fun getArbolVisual():TreeItem<String>
    {
        var raiz = TreeItem("${tipoDato.lexema} ${identificador.lexema}")
        return raiz
    }
    /**
     * aca falta analizar semantica
     */

    fun getJavaCode():String
    {
        return tipoDato.getJavaCode() +" "+ identificador.getJavaCode()

    }

}
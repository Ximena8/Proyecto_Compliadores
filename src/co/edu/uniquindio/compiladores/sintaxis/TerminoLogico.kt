package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class TerminoLogico {

    var booleano: Token? = null
    var expresionRelacional:ExpresionRelacional? = null

    constructor(booleano: Token)
    {
        this.booleano = booleano

    }

    constructor(expresionRelacional: ExpresionRelacional)
    {
        this.expresionRelacional = expresionRelacional

    }

    fun getArbolVisual():TreeItem<String>
    {
        if(booleano!=null)
        {
            var raiz=TreeItem("${booleano!!.lexema}")
            return raiz
        }
        else
        {

            var raiz = expresionRelacional!!.getArbolVisual()
            return raiz

        }
    }

}
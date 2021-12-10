package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.lexico.Error

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

    fun obtenerTipo (tablaSimbolos: TablaSimbolos, ambito:String, erroresSemanticos:ArrayList<Error>):String?
    {
        if(booleano!= null && expresionRelacional == null)
        {
            if(booleano!!.categoria== Categoria.PALABRA_RESERVADA && (booleano!!.lexema == "verdadero" || booleano!!.lexema =="falso"))
            {
                return "booleano"
            }
            else if(booleano!!.categoria == Categoria.IDENTIFICADOR)
            {
                var simbolo = tablaSimbolos.buscarSimboloValor(booleano!!.lexema,ambito , booleano!!.fila, booleano!!.columna)
                println(simbolo!= null)
                if(simbolo!= null)
                {
                    return simbolo.tipo
                }
                else
                {
                    erroresSemanticos.add(Error("La variable ${booleano!!.lexema} no esta definida en este ambito" , booleano!!.fila, booleano!!.columna))
                    return null
                }
            }

        }
        else if (expresionRelacional!= null && booleano== null)
        {
            var aux = expresionRelacional!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
            return aux
        }
        return ""
    }

    fun analizarSemantica (tablaSimbolos: TablaSimbolos, ambito: String, erroresSemanticos: ArrayList<Error>)
    {
        if(booleano!=null)
        {
            if(booleano!!.categoria== Categoria.IDENTIFICADOR)
            {
                val simbolo = tablaSimbolos.buscarSimboloValor(booleano!!.lexema , ambito, booleano!!.fila , booleano!!.columna)
                if(simbolo!=null)
                {
                    if(simbolo!!.tipo != "booleano" )
                    {
                        erroresSemanticos.add(Error("El tipo de dato de la variable ${simbolo.nombre} no representa un tipo booleano" , simbolo.fila, simbolo.columna))
                    }
                }
                else
                {
                    erroresSemanticos.add(Error("La variable ${booleano!!.lexema} no declaro con anterioridad" , booleano!!.fila, booleano!!.columna))
                }
            }
        }
        if(expresionRelacional!= null)
        {
            expresionRelacional!!.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
        }
    }


    fun getJavaCode():String {

        var codigo = ""
        if (booleano != null) {
            codigo = booleano!!.getJavaCode()
        }
        if (expresionRelacional != null)
        {
            codigo = expresionRelacional!!.getJavaCode()
        }
        return codigo
    }
}
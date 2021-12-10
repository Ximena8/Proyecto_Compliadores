package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionCadena(): Expresion() {

    var expresionCadena1: Token? = null
    var expresionCadena2:ExpresionCadena? = null


    constructor(expresionCadena1: Token, expresionCadena2: ExpresionCadena):this()
    {
        this.expresionCadena1 = expresionCadena1
        this.expresionCadena2 = expresionCadena2
    }

    constructor(expresionCadena1: Token):this()
    {
        this.expresionCadena1 = expresionCadena1

    }

    override fun toString(): String {
        return "ExpresionCadena(expresionCadena1=$expresionCadena1, expresionCadena2=$expresionCadena2)"
    }

    override fun getArbolVisual():TreeItem<String>
    {
        var raiz = TreeItem("Expresion Cadena")
        if(expresionCadena1!=null)
        {
            if(expresionCadena2!=null)
            {

                raiz.children.add(TreeItem("${expresionCadena1!!.lexema}"))
                raiz.children.add(expresionCadena2!!.getArbolVisual())
                return raiz
            }
            else
            {
                raiz.children.add(TreeItem("${expresionCadena1!!.lexema}"))
                return raiz
            }
        }
        return raiz

    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,erroresSemanticos:ArrayList<Error>):String
    {

        if(expresionCadena1!= null && expresionCadena2 == null)
        {
            return  obtenerTipoExpresionToken(tablaSimbolos, ambito, erroresSemanticos)
        }
        if(expresionCadena1!= null && expresionCadena2 != null)
        {
            val tipo1 = obtenerTipoExpresionToken(tablaSimbolos, ambito, erroresSemanticos)
            val tipo2 = expresionCadena2!!.obtenerTipoExpresionToken(tablaSimbolos, ambito, erroresSemanticos)
            if(tipo1 == "cadena" && tipo2 == "cadena")
            {
                return "cadena"
            }
            else
            {
                return ""
            }
        }
        return ""
    }

    fun obtenerTipoExpresionToken (tablaSimbolos: TablaSimbolos, ambito: String , erroresSemanticos: ArrayList<Error>):String
    {
        if(expresionCadena1!!.categoria == Categoria.CADENA_CARACTERES)
        {
            return "cadena"
        }
        else if(expresionCadena1!!.categoria == Categoria.IDENTIFICADOR)
        {

            val simbolo = tablaSimbolos.buscarSimboloValor(expresionCadena1!!.lexema,ambito, expresionCadena1!!.fila , expresionCadena1!!.columna)
            if(simbolo!= null)
            {
                if(simbolo!!.tipo == "booleano")
                {
                    return "booleano"

                }
                else if(simbolo!!.tipo == "entero")
                {
                    return "entero"
                }
                else if(simbolo!!.tipo == "decimal")
                {
                    return "decimal"
                }else if(simbolo!!.tipo == "cadena")
                {
                    return "cadena"
                }
            }

        }
        return ""
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, ambito: String, erroresSemanticos: ArrayList<Error>) {
        if(expresionCadena1!= null && expresionCadena2 == null)
        {
            if(expresionCadena1!!.categoria == Categoria.IDENTIFICADOR)
            {
                val simbolo = tablaSimbolos.buscarSimboloValor(expresionCadena1!!.lexema,ambito, expresionCadena1!!.fila , expresionCadena1!!.columna)

                if(simbolo== null){
                    erroresSemanticos.add(Error("la variable ${expresionCadena1!!.lexema} no existe en el ambito $ambito" , expresionCadena1!!.fila, expresionCadena1!!.columna))
                }
            }

        }
        if(expresionCadena1!= null && expresionCadena2 != null)
        {
            if(expresionCadena1!!.categoria == Categoria.IDENTIFICADOR)
            {
                val simbolo = tablaSimbolos.buscarSimboloValor(expresionCadena1!!.lexema,ambito, expresionCadena1!!.fila , expresionCadena1!!.columna)

                if(simbolo== null){
                    erroresSemanticos.add(Error("la variable ${expresionCadena1!!.lexema} no existe en el ambito $ambito" , expresionCadena1!!.fila, expresionCadena1!!.columna))
                }
            }

            expresionCadena2!!.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
        }
    }

    override fun getJavaCode(): String {
        if(expresionCadena1!= null && expresionCadena2 == null)
        {
            return "${expresionCadena1!!.getJavaCode()}"
        }
        else if (expresionCadena1 != null && expresionCadena2 != null)
        {
            return "${expresionCadena1!!.getJavaCode()} + ${expresionCadena2!!.getJavaCode()}"
        }
        return "Mal"
    }

}
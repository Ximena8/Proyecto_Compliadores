package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionAritmetica():Expresion() {

    var expresionAritmetica1:ExpresionAritmetica? = null
    var expresionAritmetica2:Expresion? = null
    var operador: Token? = null
    var valorNumerico:ValorNumerico? = null


    constructor(expresionAritmetica1:ExpresionAritmetica?, operador : Token?, expresionAritmetica2:ExpresionAritmetica?):this()
    {
        this.expresionAritmetica1 = expresionAritmetica1
        this.operador = operador
        this.expresionAritmetica2 = expresionAritmetica2


    }

    constructor(expresionAritmetica1: ExpresionAritmetica?):this()
    {
        this.expresionAritmetica1 = expresionAritmetica1
    }

    constructor(valorNumerico: ValorNumerico?, operador: Token?, expresionAritmetica2:Expresion?):this()
    {
        this.valorNumerico= valorNumerico
        this.operador = operador
        this.expresionAritmetica2 = expresionAritmetica2
    }

    constructor(valorNumerico:ValorNumerico?):this()
    {
        this.valorNumerico = valorNumerico


    }

    override fun toString(): String {

        var cadena:String = ""
        if(valorNumerico!=null)
        {
            cadena+= "valorNumerico=$valorNumerico"

            if(operador!=null)
            {
                cadena+= ", operador=$operador , expresionAritmetica2=$expresionAritmetica2 "
                return cadena

            }
            return cadena

        }

        if(expresionAritmetica1!= null) {
            cadena+= "expresionAritmetica1=$expresionAritmetica1"
            if(operador!=null)
            {
                cadena+= ", operador=$operador , expresionAritmetica2=$expresionAritmetica2 "
                return cadena
            }
            else
            {
                return cadena
            }
        }

        return cadena

        // return "ExpresionAritmetica(expresionAritmetica1=$expresionAritmetica1, expresionAritmetica2=$expresionAritmetica2, operador=$operador, valorNumerico=$valorNumerico)"
    }


    override  fun getArbolVisual():TreeItem<String>
    {
        var raiz = TreeItem("ExpresionAritmetica")

        if(valorNumerico!=null)
        {
            if(expresionAritmetica2!=null)
            {
                var raiz2 =TreeItem(operador!!.lexema)

                raiz2.children.add(valorNumerico!!.getArbolVisual())
                raiz2.children.add(expresionAritmetica2!!.getArbolVisual())
                raiz.children.add(raiz2)
                return raiz

            }
            else
            {
                raiz.children.add(valorNumerico!!.getArbolVisual())
                return raiz

            }


        }
        if(expresionAritmetica1!=null)
        {
            if(expresionAritmetica2!=null)
            {
                var raiz2 = TreeItem(operador!!.lexema)

                raiz2.children.add(expresionAritmetica1!!.getArbolVisual())
                raiz2.children.add(expresionAritmetica2!!.getArbolVisual())
                raiz.children.add(raiz2)
                return raiz
            }
            else
            {
                raiz.children.add(expresionAritmetica1!!.getArbolVisual())
                return raiz
            }
        }


        return raiz

    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,erroresSemanticos:ArrayList<Error>): String?
    {

        if (expresionAritmetica1!= null && expresionAritmetica2!= null && valorNumerico == null)
        {
            val tipo1 = expresionAritmetica1!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
            val tipo2 = expresionAritmetica2!!.obtenerTipo(tablaSimbolos,ambito, erroresSemanticos)

            if(tipo1 == "decimal" || tipo2 == "decimal")
            {
                return "decimal"
            }
            else
            {
                return "entero"
            }

        }else if(expresionAritmetica1!=null && expresionAritmetica2== null && valorNumerico == null)
        {
            val tipo1  = expresionAritmetica1!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
            return tipo1
        }else if (valorNumerico!= null && expresionAritmetica2!=null && expresionAritmetica1==null)
        {
            val tipo1 = obtenerTipoValorNumerico(tablaSimbolos, ambito)

            var tipo2 = expresionAritmetica2!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)

            if(tipo1 == "decimal" || tipo2 == "decimal")
            {
                return "decimal"
            }
            else if(tipo1 == "entero" && tipo2 == "entero")
            {
                return "entero"
            }
            else
            {

            }

        }
        else if(valorNumerico!= null && expresionAritmetica1 == null && expresionAritmetica2== null )
        {
            val valor = obtenerTipoValorNumerico(tablaSimbolos,ambito)
            return valor

        }
        return ""
    }

    fun obtenerTipoValorNumerico (tablaSimbolos: TablaSimbolos,ambito: String):String? {
        if (valorNumerico!!.valor.categoria == Categoria.ENTERO) {
            return "entero"
        } else if (valorNumerico!!.valor.categoria == Categoria.DECIMAL) {
            return "decimal"
        } else {
            var simbolo = tablaSimbolos.buscarSimboloValor(valorNumerico!!.valor.lexema, ambito,valorNumerico!!.valor.fila,valorNumerico!!.valor.columna)
            if (simbolo != null) {
                return simbolo.tipo;
            }
        }
        return null
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, ambito: String, erroresSemanticos: ArrayList<Error>)
    {
        if(valorNumerico!=null)
        {
            if(valorNumerico!!.valor.categoria==Categoria.IDENTIFICADOR)
            {
                val tipo = obtenerTipoValorNumerico(tablaSimbolos, ambito)
                if(tipo!= null)
                { if(!(tipo == "entero"||tipo == "decimal"))
                {
                    erroresSemanticos.add(Error("el tipo de dato de ${valorNumerico!!.valor.lexema} no representa un valor numerico" , valorNumerico!!.valor.fila,valorNumerico!!.valor.columna))
                }
                }
                else
                {
                    erroresSemanticos.add(Error("La variable ${valorNumerico!!.valor.lexema} no fue declarada" , valorNumerico!!.valor.fila,valorNumerico!!.valor.columna))
                }
            }
        }
        if(expresionAritmetica1!= null )
        {
            expresionAritmetica1!!.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
        }
        if(expresionAritmetica2!= null)
        {
            expresionAritmetica2!!.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
        }

    }

    override fun getJavaCode():String {
        var codigo = ""
        if(expresionAritmetica1!= null && expresionAritmetica2!= null)
        {
            codigo += expresionAritmetica1!!.getJavaCode() +" ${operador!!.getJavaCode()} " + expresionAritmetica2!!.getJavaCode()
            return codigo
        }else if(expresionAritmetica1!= null && expresionAritmetica2 == null && valorNumerico== null)
        {
            codigo+= "(" +  expresionAritmetica1!!.getJavaCode() +")"
            return codigo
        }
        else if(valorNumerico!= null && expresionAritmetica2!= null && expresionAritmetica1==null)
        {
            codigo+= valorNumerico!!.getJavaCode() + " ${operador!!.getJavaCode()} " + expresionAritmetica2!!.getJavaCode()
            return codigo
        }
        else if(valorNumerico!= null)
        {
            codigo += valorNumerico!!.getJavaCode()
            return codigo
        }
        return codigo
    }

}
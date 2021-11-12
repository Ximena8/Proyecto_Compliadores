package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionAritmetica():Expresion() {

    var expresionAritmetica1:ExpresionAritmetica? = null
    var expresionAritmetica2:ExpresionAritmetica? = null
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

    constructor(valorNumerico: ValorNumerico?, operador: Token?, expresionAritmetica2:ExpresionAritmetica?):this()
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

}
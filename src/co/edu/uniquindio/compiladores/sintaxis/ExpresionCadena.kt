package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
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

}
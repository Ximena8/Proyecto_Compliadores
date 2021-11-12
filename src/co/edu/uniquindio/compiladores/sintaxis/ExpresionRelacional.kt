package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional(val expresionAritmetica1:ExpresionAritmetica, val  operador: Token, val expresionAritmetica2: ExpresionAritmetica):Expresion() {

    override fun toString():String
    {
        // return "ExpresionRelacional(expresionRelacional1=$expresionRelacional1, expresionRelacional2=$expresionRelacional2, operador=$operador, expresionAritmetica=$expresionAritmetica)"
        return "ExpresionRelacional(expresionAritmetica1 =** $expresionAritmetica1,** operadorR = $operador , expresionAritmetica2 =**  $expresionAritmetica2**"
    }

    override fun getArbolVisual():TreeItem<String>
    {
        var raiz =TreeItem("ExpresionRelacional")
        var raiz2 =TreeItem(operador.lexema)
        raiz2.children.add(expresionAritmetica1.getArbolVisual())
        raiz2.children.add(expresionAritmetica2.getArbolVisual())
        raiz.children.add(raiz2)
        return raiz

    }

}
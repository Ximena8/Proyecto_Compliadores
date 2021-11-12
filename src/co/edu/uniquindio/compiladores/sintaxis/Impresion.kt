package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * llaves es {
 * <Impresion> ::= imprimir "{" <Expresion> "}" "#"
 */

class Impresion(var exp:Expresion?, var identificador:Token? ) : Sentencia() {

    override fun toString(): String{
        return "Impresion(exp=$exp,identificador=$identificador)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Impresion")

        raiz.children.add(exp?.getArbolVisual())
        if(identificador!=null) {
            raiz.children.add(TreeItem("Nombre:${identificador?.lexema}"))
        }
        if(exp != null)
        {
            raiz.children.add(exp!!.getArbolVisual())

        }
        return raiz
    }

}
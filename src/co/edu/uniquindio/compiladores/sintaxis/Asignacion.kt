package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 *   <Asignacion> ::= identificador operadorAsignaccion <Expresion> "#"
 */

class Asignacion(var identificador:Token, var asig: Expresion ) : Sentencia() {

    override fun toString(): String {
        return "Asignacion(identificador=$identificador, asig=$asig)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Asignacion")
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))
        var raizExpre = TreeItem("Expresion")
        raizExpre.children.add(asig.getArbolVisual())
        raiz.children.add(raizExpre)
        return raiz
    }

}
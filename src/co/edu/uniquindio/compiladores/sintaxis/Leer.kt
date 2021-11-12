package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * <Leer> ::= leer identificador "#"
 */

class LeerLeer(var identificador:Token):Sentencia( ) {

    override fun toString(): String{
        return "Leer(identificador=$identificador)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("leer")
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))
        return raiz
    }

}
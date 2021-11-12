package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Decremento(var tipo:Token):Sentencia() {

    override fun toString(): String{
        return "Decremento(decremento=$tipo)"
    }

    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Decremento")
        raiz.children.add(TreeItem("Nombre:${tipo.lexema}"))
        return raiz
    }

}
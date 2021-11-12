package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Castin(var tipo:Token,var dato:Token) : Sentencia() {

    override fun toString(): String{

        return "Castin variable(tipo=$tipo, dato=$dato)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Castin")
        raiz.children.add(TreeItem("tipo de dato:${tipo.lexema}"))
        raiz.children.add(TreeItem("Nombre:${dato.lexema}"))
        return raiz
    }

}
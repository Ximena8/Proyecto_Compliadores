package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Incremento(var idt:Token) :Sentencia() {

    override fun toString(): String{
        return "Incremento(incremento=$idt)"
    }

    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("incremento")
        raiz.children.add(TreeItem("Nombre:${idt.lexema}"))
        return raiz
    }

}
package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Mientras(var exp: Expresion,var list: ArrayList<Sentencia>): Sentencia() {

    override fun toString(): String
    {
        return "CicloMientras(exp=$exp, list=$list)"
    }

    override fun getArbolVisual():TreeItem<String>
    {
        var raiz = TreeItem("mientras")

        var raizExpre = TreeItem("Expresion")
        var raizDefault = TreeItem("Default")
        raizExpre.children.add(exp.getArbolVisual())
        raiz.children.add(raizExpre)
        var raizLista = TreeItem("ListaSentencias")

        for(i in list)
        {
            raizLista.children.add(i.getArbolVisual())
        }

        raiz.children.add(raizLista)
        return raiz
    }

}
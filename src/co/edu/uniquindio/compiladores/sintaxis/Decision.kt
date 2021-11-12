package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

/**
 * <<Desicion> ::= si "{" <ExpresionLogica> "}" "llaveIzquierda" [<ListaSentencias>] "Llave Derecha" siNo  "LLaveIzquierda" [<ListaSentencias>] "LlaveDerecho"

 */

class Decision (var expresionLogica:ExpresionLogica, var listaSentencias: ArrayList<Sentencia>) : Sentencia() {
    override fun toString(): String {
        return "Decision(expresionLogica=$expresionLogica, listaSentancias=$listaSentencias,)"
    }


    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Decision")

        var raizExpre = TreeItem("Expresion")
        raizExpre.children.add(expresionLogica.getArbolVisual())
        raiz.children.add(raizExpre)
        var raizLista = TreeItem("ListaSentencias")

        for(i in listaSentencias)
        {
            raizLista.children.add(i.getArbolVisual())
        }

        raiz.children.add(raizLista)
        return raiz
    }

}
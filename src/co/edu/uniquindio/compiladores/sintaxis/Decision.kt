package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * <<Desicion> ::= si "{" <ExpresionLogica> "}" "llaveIzquierda" [<ListaSentencias>] "Llave Derecha" siNo  "LLaveIzquierda" [<ListaSentencias>] "LlaveDerecho"

 */

class Decision (var expresionLogica:ExpresionLogica, var listaSentencias: ArrayList<Sentencia>) : Sentencia() {
    override fun toString(): String{
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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos: ArrayList<String>) {
        for (l in listaSentencias)
        {
            l.llenarTablaSimbolos(tablaSimbolos,erroresSemanticos,ambito+"Desicion",tipos)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        expresionLogica.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
        for (l in listaSentencias)
        {
            l.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito+"Desicion",tipos)
        }
    }
    override  fun getJavaCode():String
    {
        var codigo= "if(" + expresionLogica.getJavaCode()+"){"+ "\n"
        for (l in listaSentencias)
        {
            codigo+=l.getJavaCode()+ "\n"
        }
        codigo+="}"
        return codigo
    }

}
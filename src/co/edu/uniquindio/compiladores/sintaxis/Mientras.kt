package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


/**
 * Metodo que se encarga de verificar si una secuencia de tokens constituye un ciclo mientras
 *  <mientras> ::= mientras "(" <ExpresionRelacional> ")" hacer  <Sentencia> [<ListaSentencias>] terminar
 */class Mientras(var exp: ExpresionRelacional,var list: ArrayList<Sentencia>): Sentencia() {

    override fun toString(): String
    {
        return "CicloMientras(exp=$exp, list=$list)"
    }

    override fun getArbolVisual():TreeItem<String>
    {
        var raiz = TreeItem("mientras")

        var raizExpre = TreeItem("Expresion")
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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>,ambito: String,tipos: ArrayList<String>) {
        for (l in list)
        {
            l.llenarTablaSimbolos(tablaSimbolos,erroresSemanticos,ambito+"Mientras",tipos)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        exp.analizarSemantica(tablaSimbolos, ambito+"Mientras", erroresSemanticos)
        for (l in list)
        {
            l.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito,tipos)
        }
    }
    override  fun getJavaCode():String
    {
        var codigo= "while(" + exp.getJavaCode()+"){"+ "\n"
        for (l in list)
        {
            codigo+=l.getJavaCode()+ "\n"
        }
        codigo+="}"
        return codigo
    }
}

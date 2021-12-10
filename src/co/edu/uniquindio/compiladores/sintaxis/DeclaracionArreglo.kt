package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * <DeclaracionArreglos> ::= arreglo <TipoDato> " &" [<ListaIdentificadores>]"#"
 */

class DeclaracionArreglo(var tipo : Token , var identificador: Token):Sentencia() {

    override fun toString(): String{
        return "DeclararArreglo(tipo=$tipo,identificador=$identificador)"
    }

    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Declaracion Arreglo")
        raiz.children.add(TreeItem("Nombre:${tipo.lexema}"))
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))


        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos:ArrayList<Error>, ambito:String,lista:ArrayList<String>) {
        tablaSimbolos.guardarSimboloValor(identificador.lexema,tipo.lexema,true,ambito,identificador.fila,identificador.columna,lista)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {

    }
    override  fun getJavaCode():String
    {
        var codigo=tipo.getJavaCode()+"[] " + identificador.getJavaCode()+ " = null;"
        return codigo
    }


}
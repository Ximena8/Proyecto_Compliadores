package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
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

}
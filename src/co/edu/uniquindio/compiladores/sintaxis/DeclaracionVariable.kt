package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * <DeclaracionVariables> ::= Var <TipoDato> "&" [<ListaIdentificadores>] "#"
 */

class DeclaracionVariable(var tipo: Token, var identificador:Token):Sentencia() {

    override fun toString(): String{

        return "Declaracion de variable(tipo=$tipo, identificador=$identificador)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("DeclaracionVariable")
        raiz.children.add(TreeItem("Nombre:${tipo.lexema}"))
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))


        return raiz
    }

}
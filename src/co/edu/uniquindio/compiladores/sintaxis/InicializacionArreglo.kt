package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 *<InicializacionArreglo> ::=  identificador "{{" <Entero> "}} " operadorAsignaccion <Expresion> "#"
 */

class InicializacionArreglo(var identificador:Token, var exp:Expresion ) :Sentencia() {

    override fun toString(): String{

        return "inicializacion de arreglo(identificador=$identificador, expresion=$exp)"
    }

    override  fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Inicializacion Arreglo")
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))
        raiz.children.add(exp.getArbolVisual())
        return raiz
    }

}
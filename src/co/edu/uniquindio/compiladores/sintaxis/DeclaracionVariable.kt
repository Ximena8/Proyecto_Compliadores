package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String,tipos: ArrayList<String>) {
        tablaSimbolos.guardarSimboloValor(identificador.lexema,tipo.lexema,true,ambito,identificador.fila,identificador.columna,tipos)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
    }
    override  fun getJavaCode():String
    {
        var codigo= tipo.getJavaCode()+" "+identificador.getJavaCode() +" = "
        if(tipo.lexema== "decimal" ||tipo.lexema== "entero" )
        {
            codigo+= "0"
        }else if(tipo.lexema== "cadena")
        {
            codigo+= "\"\""
        }else if(tipo.lexema== "booleano")
        {
            codigo+= "true"
        }

        codigo+= " ;"
        return codigo
    }


}
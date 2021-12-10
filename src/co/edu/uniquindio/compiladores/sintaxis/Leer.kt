package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * <Leer> ::= leer identificador "#"
 */

class Leer(var identificador:Token):Sentencia( ) {

    private var simbolo:Simbolo?=null
    override fun toString(): String{
        return "Leer(identificador=$identificador)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("leer")
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))
        return raiz
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        simbolo=tablaSimbolos.buscarSimboloValor(identificador.lexema,ambito,tipos,identificador.fila,identificador.columna)
        if(simbolo==null)
        {
            erroresSemanticos.add(Error("El identificador (${identificador.lexema}) no existe en el ambito(${ambito})",identificador.fila,identificador.columna))
        }
    }
    override  fun getJavaCode():String {
        var codigo=""
        if(simbolo!!.tipo == "int")
        {
            codigo=identificador.getJavaCode()+" = Integer.parseInt(JOptionPane.showInputDialog(null, \"Escribir:\"));"
        }
        if(simbolo!!.tipo == "decimal")
        {
            codigo= identificador.getJavaCode()+" = Double.parseDouble(JOptionPane.showInputDialog(null, \"Escribir:\"));"
        }
        if(simbolo!!.tipo == "booleano")
        {
            codigo= identificador.getJavaCode()+" = Boolean.parseBoolean(JOptionPane.showInputDialog(null, \"Escribir:\"));"
        }
        else
        {
            codigo= identificador.getJavaCode()+" = JOptionPane.showInputDialog(null, \"Escribir:\"));"
        }
        return codigo
    }
}
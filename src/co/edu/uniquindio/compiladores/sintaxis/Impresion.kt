package co.edu.uniquindio.compiladores.sintaxis



import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * llaves es {
 * <Impresion> ::= imprimir "{" <Expresion> "}" "#"
 */

class Impresion(var exp:Expresion) : Sentencia() {

    override fun toString(): String{
        return "Impresion(exp=$exp)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Impresion")

        raiz.children.add(exp.getArbolVisual())

        raiz.children.add(exp.getArbolVisual())
        return raiz
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String ,tipos:ArrayList<String>) {
        exp.analizarSemantica(tablaSimbolos,ambito ,erroresSemanticos)
    }
    override  fun getJavaCode():String
    {

        return "JOptionPane.showMessageDialog(null, "+exp.getJavaCode()+");"
    }



}
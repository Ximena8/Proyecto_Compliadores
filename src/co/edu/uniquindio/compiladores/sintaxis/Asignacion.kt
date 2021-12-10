package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import kotlin.math.exp

/**
 *   <Asignacion> ::= identificador operadorAsignaccion <Expresion> "#"
 */

class Asignacion(var identificador:Token, var asig: Expresion ) : Sentencia() {

    override fun toString(): String {
        return "Asignacion(identificador=$identificador, asig=$asig)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Asignacion")
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))
        var raizExpre = TreeItem("Expresion")
        raizExpre.children.add(asig.getArbolVisual())
        raiz.children.add(raizExpre)
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        var s=tablaSimbolos.buscarSimboloValor(identificador.lexema,ambito,tipos,identificador.fila,identificador.columna)
        if(s==null)
        {
            erroresSemanticos.add(Error("El campo ${identificador.lexema} no existe dentro del ambito $ambito",identificador.fila,identificador.columna))
        }
        else
        {
            var tipo = s.tipo
            var expr=asig.analizarSemantica(tablaSimbolos, ambito,erroresSemanticos)
            var tipoExp= asig.obtenerTipo(tablaSimbolos,ambito,erroresSemanticos)

            if(tipoExp!= tipo)
            {
                erroresSemanticos.add(Error("El tipo de dato de la expresion ($tipoExp) no coincide con el tipo de dato del campo(${identificador.lexema}) que es $tipo",identificador.fila,identificador.columna))
            }
        }
    }
    override  fun getJavaCode():String
    {
        var codigo=identificador.getJavaCode()+" = "+asig.getJavaCode()+";"
        return codigo
    }

}
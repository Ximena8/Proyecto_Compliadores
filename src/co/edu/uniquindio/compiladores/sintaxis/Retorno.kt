package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * <Retorno>::= retornar"."<esExpresion>  ";"
 */

class Retorno(var exp:Expresion) : Sentencia() {

    override fun toString(): String{
        return "Retorno(exp=$exp)"
    }

    override fun getArbolVisual():TreeItem<String>
    {
        var raiz =TreeItem("Retorno")

        var raizExpre =TreeItem("Expresion")
        raizExpre.children.add(exp.getArbolVisual())
        raiz.children.add(raizExpre)

        return raiz

    }

    /**
     * falta la fila y columna
     */
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        //  exp.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        var tipoR= exp.obtenerTipo(tablaSimbolos,ambito,erroresSemanticos)
        var tipoMetodo=tablaSimbolos.buscarSimboloFuncion(ambito,tipos)
        var filCol=exp.obtenerFilaColumna()
        if (tipoMetodo != null) {
            if(tipoR!=tipoMetodo.tipo) {
                erroresSemanticos.add(Error("El tipo de retorno (${tipoR}) no es compatible con el tipo de retorno del metodo que es" +
                        "(${tipoMetodo.tipo})",1,3))
                //   filCol.fila,filCol.columna
            }
        }
    }

    override  fun getJavaCode():String
    {
        return "return "+ exp.getJavaCode()+ ";"
    }

}
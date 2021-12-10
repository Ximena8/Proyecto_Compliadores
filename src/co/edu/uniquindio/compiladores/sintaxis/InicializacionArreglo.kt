package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


/**
 *<InicializacionArreglo> ::=  "P"identificador "[" <Entero> "]" operadorAsignaccion <Expresion> "#"
 */

class InicializacionArreglo(var identificador:Token, var exp:Expresion,var numero:Token ) :Sentencia() {

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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        var s=tablaSimbolos.buscarSimboloValor(identificador.lexema,ambito,tipos,identificador.fila,identificador.columna)
        if(s==null)
        {
            erroresSemanticos.add(Error("El campo ${identificador.lexema} no existe dentro del ambito $ambito",identificador.fila,identificador.columna))
        }
        else
        {
            // exp.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            var tipo = s.tipo
            if(exp!=null) {
                var tipoExp = exp.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
                if (tipoExp != tipo) {
                    erroresSemanticos.add(Error("El tipo de dato de la expresion ($tipoExp) no coincide con el tipo de dato del campo(${identificador.lexema}) que es $tipo", identificador.fila, identificador.columna
                    )
                    )
                }
            }
        }

    }
    override  fun getJavaCode():String
    {
        var codigo= identificador.getJavaCode()+" ["+ numero.lexema +"] = "+ exp.getJavaCode()+";"
        return codigo
    }

}
package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class Decremento(var identificador:Token):Sentencia() {

    override fun toString(): String{
        return "Decremento(decremento=$identificador)"
    }

    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Decremento")
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))
        return raiz
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        var s = tablaSimbolos.buscarSimboloValor(identificador.lexema, ambito,tipos,identificador.fila,identificador.columna)
        if (s == null) {
            erroresSemanticos.add(Error("la variable  ${identificador.lexema} no existe dentro del ambito $ambito", identificador.fila, identificador.columna))
        }
        else if(s!!.tipo == "booleano" || s!!.tipo== "cadena")
        {
            erroresSemanticos.add(Error("la variable  ${identificador.lexema} no se puede decrementar solo se decrementa entero o real $ambito", identificador.fila, identificador.columna))

        }
    }
    override  fun getJavaCode():String
    {
        var codigo= identificador.getJavaCode()+"++;"
        return codigo
    }

}
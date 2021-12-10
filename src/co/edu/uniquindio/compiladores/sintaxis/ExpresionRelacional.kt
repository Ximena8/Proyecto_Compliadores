package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional(val expresionAritmetica1:ExpresionAritmetica, val  operador: Token, val expresionAritmetica2: ExpresionAritmetica):Expresion() {

    override fun toString():String
    {
        // return "ExpresionRelacional(expresionRelacional1=$expresionRelacional1, expresionRelacional2=$expresionRelacional2, operador=$operador, expresionAritmetica=$expresionAritmetica)"
        return "ExpresionRelacional(expresionAritmetica1 =** $expresionAritmetica1,** operadorR = $operador , expresionAritmetica2 =**  $expresionAritmetica2**"
    }

    override fun getArbolVisual():TreeItem<String>
    {
        var raiz =TreeItem("ExpresionRelacional")
        var raiz2 =TreeItem(operador.lexema)
        raiz2.children.add(expresionAritmetica1.getArbolVisual())
        raiz2.children.add(expresionAritmetica2.getArbolVisual())
        raiz.children.add(raiz2)
        return raiz

    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,erroresSemanticos:ArrayList<Error>):String
    {

        val tipo1 = expresionAritmetica1.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
        val tipo2 = expresionAritmetica2.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
        if((tipo1== "entero" || tipo1 == "real")&&(tipo2== "entero" || tipo2 == "real"))
        {
            return "booleano"
        }
        else
        {
            erroresSemanticos.add(Error("Las expresiones no representan valores numericos" ,  expresionAritmetica1!!.operador!!.fila, expresionAritmetica1!!.operador!!.columna))
        }
        return ""
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, ambito: String, erroresSemanticos: ArrayList<Error>) {

        if(expresionAritmetica1!= null && expresionAritmetica2!=null)
        {
            expresionAritmetica1.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
            expresionAritmetica2.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
        }

    }

    override fun getJavaCode(): String {

        if(expresionAritmetica1!= null && expresionAritmetica2!= null)
        {
            var codigo = ""
            codigo+= "(" + expresionAritmetica1.getJavaCode() + " ${operador.getJavaCode()} "+ expresionAritmetica2.getJavaCode()+ ")"
            return codigo
        }
        return ""
    }

}
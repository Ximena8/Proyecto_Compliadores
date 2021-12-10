package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem
import kotlin.math.sign

class ValorNumerico(val signo: Token?, val valor: Token) {

    override fun toString(): String {
        return "$signo$valor)"
    }

    fun getArbolVisual():TreeItem<String>
    {
        var raiz = TreeItem("Valor Numerico")
        if(signo!=null)
        {
            raiz.children.add(TreeItem("${signo.lexema} ${valor.lexema}"))
            return raiz
        }
        else
        {
            raiz.children.add(TreeItem("${valor.lexema}"))
            return raiz
        }

    }

    fun getJavaCode():String {
        var codigo = ""
        if(signo!=null)
        {
            codigo+= signo.getJavaCode()
        }
        codigo += valor.getJavaCode()
        return codigo
    }

}
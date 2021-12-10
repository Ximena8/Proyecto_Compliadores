package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import javax.swing.plaf.basic.BasicTabbedPaneUI

/**
 * <esInvocacion>::= invocar identificador "(" [<esListaArgumentos>] ")" "#"
 */

class Invocacion(var ident:Token,var list:ArrayList<Argumento>?):Sentencia() {


    private var simbolo: Simbolo?=null

    override fun toString(): String{
        return "Invocacion(identificador=$ident,lista=$list)"
    }
    override  fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Invocacion Metodo")
        raiz.children.add(TreeItem("Nombre:${ident.lexema}"))

        if(list != null)
        {
            var raizLista = TreeItem("ListaArgumentos")
            for(i in list!!)
            {
                raizLista.children.add(i.getArbolVisual())
            }
            raiz.children.add(raizLista)
        }

        return raiz
    }


    fun obtenerTiposArgumentos(tablaSimbolos: TablaSimbolos,ambito: String,erroresSemanticos: ArrayList<Error>):ArrayList<String>
    {
        var lista=ArrayList<String>()
        if(list != null)
        {
            for (l in list!!)
            {
                l.exp.analizarSemantica(tablaSimbolos,ambito,erroresSemanticos)
                lista.add(l.exp.obtenerTipo(tablaSimbolos,ambito,erroresSemanticos)!!)
            }
        }
        return lista
    }

    /**
     * falta expresion cadena 2
     */
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        var listaA=obtenerTiposArgumentos(tablaSimbolos,ambito,erroresSemanticos)
        simbolo =tablaSimbolos.buscarSimboloFuncion(ident.lexema,listaA)
        if(simbolo==null)
        {
            erroresSemanticos.add(Error("el metodo   ${ident.lexema} $listaA no existe en unidad de compilacion", ident.fila, ident.columna))
        }
        else
        {

            if(listaA!= simbolo!!.tiposParametros)
            {
                erroresSemanticos.add(Error("los argumentos de la invocacion ${ident.lexema}  ${listaA}  son diferentes a los  ${simbolo!!.tiposParametros}", ident.fila, ident.columna))
            }
        }


    }


    override fun getJavaCode(): String {

        var codigo = ""

        if(simbolo!!.tipo == "entero" )
        {
            codigo +=" int retorno = " + ident.getJavaCode() +"(" + getJavaCodeParametros()
            return codigo
        }
        else if(simbolo!!.tipo == "decimal")
        {
            codigo +=" double retorno = " + ident.getJavaCode() +"(" + getJavaCodeParametros()
            return codigo
        }
        else if(simbolo!!.tipo == "booleano")
        {
            codigo +=" boolean retorno = " + ident.getJavaCode() +"(" + getJavaCodeParametros()
            return codigo

        }else if (simbolo!!.tipo == "cadena")
        {
            codigo +=" String retorno = " + ident.getJavaCode() +"(" + getJavaCodeParametros()
            return codigo
        }
        else
        {
            codigo +=  ident.getJavaCode() +"(" + getJavaCodeParametros()
            return codigo
        }
    }


    fun getJavaCodeParametros ():String
    {
        var codigo = ""
        if(list!!.isNotEmpty())
        {
            for (i in list!!)
            {
                codigo+= i.exp.getJavaCode()+ ","
            }

            codigo = codigo.substring(0 , codigo.length-1)
            codigo+= ");"
            return codigo
        }
        else
        {
            codigo+= ");"
            return codigo
        }
    }


}
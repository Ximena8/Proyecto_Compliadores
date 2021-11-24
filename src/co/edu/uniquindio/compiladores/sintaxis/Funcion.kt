package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Funcion(var identificador:Token, var tipoRetorno:Token,var listaParametros:ArrayList<Parametro>,var list:ArrayList<Sentencia>) {
    override fun toString(): String  {
        return "Metodo(identificador=$identificador, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencias=$list)"
    }

    fun getArbolVisual():TreeItem<String>
    {
        var raiz = TreeItem("Metodo")
        raiz.children.add(TreeItem("${identificador.lexema}"))
        raiz.children.add(TreeItem("${tipoRetorno.lexema}"))

        if(!listaParametros.isEmpty())
        {
            var parametros = TreeItem("Parámetros")
            raiz.children.add(parametros)
            for (parametro in listaParametros)
            {
                parametros.children.add(parametro.getArbolVisual())
            }
        }
        if(list != null)
        {
            var sentencias = TreeItem("Sentencias")
            raiz.children.add(sentencias)
            for (s in list)
            {
                sentencias.children.add(s.getArbolVisual())
            }
        }

        return raiz
    }

}
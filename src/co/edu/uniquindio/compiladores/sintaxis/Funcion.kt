package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Funcion(var identificador:Token, var tipoRetorno:Token,var listaParametros:ArrayList<Parametro>,var list:ArrayList<Sentencia>) {
    override fun toString(): String {
        return "Metodo(identificador=$identificador, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencias=$list)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Metodo")
        raiz.children.add(TreeItem("${identificador.lexema}"))
        raiz.children.add(TreeItem("${tipoRetorno.lexema}"))

        if (!listaParametros.isEmpty()) {
            var parametros = TreeItem("Parámetros")
            raiz.children.add(parametros)
            for (parametro in listaParametros) {
                parametros.children.add(parametro.getArbolVisual())
            }
        }
        if (list != null) {
            var sentencias = TreeItem("Sentencias")
            raiz.children.add(sentencias)
            for (s in list) {
                sentencias.children.add(s.getArbolVisual())
            }
        }

        return raiz
    }

    fun obtenerTiposParametros(): ArrayList<String> {
        var lista = ArrayList<String>()
        for (l in listaParametros) {
            //  print("  este es "+ l.tipoDato.lexema+ " ")
            lista.add(l.tipoDato.lexema)
        }
        return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        var lista = obtenerTiposParametros()
        tablaSimbolos.guardarSimboloFuncion(   identificador.lexema,tipoRetorno.lexema,lista, ambito,identificador.fila, identificador.columna)
        for (l in listaParametros) {
            tablaSimbolos.guardarSimboloValor(
                l.identificador.lexema,
                l.tipoDato.lexema,
                true,
                identificador.lexema,
                identificador.fila,
                identificador.columna,
                lista
            )
        }
        for (i in list) {
            i.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, identificador.lexema, obtenerTiposParametros())
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>) {

        for (l in list) {
            l.analizarSemantica(tablaSimbolos, erroresSemanticos, identificador.lexema, obtenerTiposParametros())
        }
    }

    fun getJavaCode(): String {

        var codigo = ""
        if (identificador.lexema == "KmainK") {
            codigo = "public static void main(String[] args){" +"\n"

        } else {
            codigo = "static " + tipoRetorno.getJavaCode() + " " + identificador.getJavaCode() + " ("
            if(listaParametros.isNotEmpty()) {
                for (l in listaParametros) {
                    codigo += l.getJavaCode() + ","
                }

                codigo = codigo.substring(0, codigo.length - 1)
            }
            codigo += "){"+ "\n"
        }
        for (l in list) {
            codigo += l.getJavaCode()+ "\n"
        }
        codigo += "}"
        return codigo
    }


}
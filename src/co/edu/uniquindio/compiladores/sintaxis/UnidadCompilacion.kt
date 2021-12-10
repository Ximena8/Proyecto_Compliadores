package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
class UnidadCompilacion (var listaFunciones:ArrayList<Funcion>){

    public var count = 0
    override fun toString(): String {
        return "UnidadCompilacion(listaFunciones=$listaFunciones)"
    }


    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Unidad de compilación")
        for (metodo in listaFunciones) {
            raiz.children.add(metodo.getArbolVisual())
        }
        return raiz

    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos:ArrayList<Error>)
    {
        for (f in listaFunciones)
        {


            f.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos,"unidad Compilacion")
        }
    }

    fun analizarSemantica(tablaSimbolos:TablaSimbolos, erroresSemanticos:ArrayList<Error>)
    {
        for (f in listaFunciones)
        {
            f.analizarSemantica(tablaSimbolos, erroresSemanticos)
        }
    }
    fun getJavaCode():String
    {
        var codigo="import javax.swing.JOptionPane; public class Principal{"+"\n"
        for (l in listaFunciones)
        {
            codigo+= l.getJavaCode()+"\n"
        }
        codigo+="}"
        return codigo
    }

}
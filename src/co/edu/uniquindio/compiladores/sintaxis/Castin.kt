package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Castin(var tipo:Token,var identificador:Token) : Sentencia() {

    override fun toString(): String{

        return "Castin variable(tipo=$tipo, dato=$identificador)"
    }
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Castin")
        raiz.children.add(TreeItem("tipo de dato:${tipo.lexema}"))
        raiz.children.add(TreeItem("Nombre:${identificador.lexema}"))
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String,tipos:ArrayList<String>) {
        var s = tablaSimbolos.buscarSimboloValor(identificador.lexema, ambito,tipos,identificador.fila,identificador.columna)
        if (s == null) {
            erroresSemanticos.add(Error("la variable  ${identificador.lexema} no existe dentro del ambito $ambito", identificador.fila, identificador.columna))
        } else {
            var tipoValor = s.tipo
            if (tipo != null) {
                if ((tipo.lexema != tipoValor) && tipoValor == "booleano"  && (tipo.lexema == "entero" || tipo.lexema == "real" )) {
                    erroresSemanticos.add(Error("El tipo de dato (${tipo.lexema}) no se puede hacer el castin a el tipo de dato (${tipoValor}) ",identificador.fila,identificador.columna))
                }
                if ((tipo.lexema != tipoValor ) &&  (tipoValor == "entero" || tipoValor== "real")  && (tipo.lexema == "booleano")) {
                    erroresSemanticos.add(Error("El tipo de dato (${tipo.lexema}) no se puede hacer el castin con el tipo de dato del campo(${tipoValor}) ",identificador.fila,identificador.columna))
                }
            }
        }
    }

    override  fun getJavaCode():String
    {
        var codigo=""
        if(tipo.lexema== "entero")
        {
            codigo="int castin =(int) "+identificador.getJavaCode()+";"
        }
        if(tipo.lexema== "decimal")
        {
            codigo="double castin =  "+identificador.getJavaCode()+";"
        }
        if(tipo.lexema == "cadena")
        {
            codigo="String castin = "+identificador.getJavaCode()+"\" \""+";"
        }
        return codigo
    }
}
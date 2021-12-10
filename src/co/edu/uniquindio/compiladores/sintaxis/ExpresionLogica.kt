package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionLogica() : Expresion() {

    var expresionLogica1:Expresion? = null
    var expresionLogica2:Expresion? = null
    var terminoLogico:TerminoLogico? = null
    var operador:Token? = null
    var operadorNegador:Token? = null


    constructor(expresionLogica1: Expresion?,operador:Token? ,expresionLogica2: Expresion?):this()
    {
        this.expresionLogica1 = expresionLogica1
        this.operador = operador
        this.expresionLogica2 = expresionLogica2


    }


    constructor(terminoLogico: TerminoLogico,operador: Token,expresionLogica2: Expresion):this()
    {

        this.terminoLogico = terminoLogico
        this.operador = operador
        this.expresionLogica2 = expresionLogica2
    }

    constructor(expresionLogica1: ExpresionLogica):this()
    {

        this.expresionLogica1 = expresionLogica1

    }

    constructor(terminoLogico: TerminoLogico):this()
    {

        this.terminoLogico = terminoLogico
    }

    constructor(operadorNegador:Token,expresionLogica1: ExpresionLogica):this()
    {
        this.operadorNegador = operadorNegador
        this.expresionLogica1 = expresionLogica1

    }


    override fun getArbolVisual():TreeItem<String>
    {


        if(operadorNegador!=null)
        {
            var raizNegador = TreeItem("$operadorNegador.lexema")
            raizNegador.children.add(expresionLogica1!!.getArbolVisual())
            return raizNegador
        }
        else
        {
            var raiz = TreeItem("Expresion Logica")

            if(terminoLogico!=null)
            {

                if(expresionLogica2!=null)
                {
                    var raiz2 =TreeItem("${operador!!.lexema}")
                    raiz2.children.add(terminoLogico!!.getArbolVisual())
                    raiz2.children.add(expresionLogica2!!.getArbolVisual())
                    raiz.children.add(raiz2)
                    return raiz
                }
                else
                {
                    var raizAux =terminoLogico!!.getArbolVisual()

                    return  raizAux

                }

            }
            if(expresionLogica1!=null)
            {
                if(expresionLogica2!=null)
                {

                    var raiz2 = TreeItem("${operador!!.lexema}")
                    raiz2.children.add(expresionLogica1!!.getArbolVisual())
                    raiz2.children.add(expresionLogica2!!.getArbolVisual())
                    raiz.children.add(raiz2)
                    return raiz
                }
                else
                {
                    raiz.children.add(expresionLogica1!!.getArbolVisual())
                    return raiz
                }
            }
            return raiz
        }



    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,erroresSemanticos:ArrayList<Error>):String?
    {
        if(expresionLogica1 != null && expresionLogica2!=null && terminoLogico== null)
        {

            var tipo1 = expresionLogica1!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
            var tipo2 = expresionLogica2!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)

            if(tipo1 == "booleano" && tipo2 == "booleano"  )
            {
                return "booleano"
            }
            else
            {
                erroresSemanticos.add(Error("Las expresiones no representan valores booleanos" ,  operador!!.fila, operador!!.columna))
                return ""
            }
        }
        else if(terminoLogico!= null && expresionLogica2!= null && expresionLogica1== null)
        {

            var tipo1 = terminoLogico!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
            var tipo2 = expresionLogica2!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
            if(tipo1 == "booleano" && tipo2 == "booleano"  )
            {
                return "booleano"
            }
            else
            {
                erroresSemanticos.add(Error("Las expresiones no representan valores booleanos" ,  operador!!.fila, operador!!.columna))
                return ""
            }
        }
        else if(expresionLogica1!= null && expresionLogica2== null && terminoLogico== null)
        {
            var tipo1 = expresionLogica1!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
            if(tipo1 == "booleano")
            {
                return "booleano"
            }
            else
            {
                //Fila y columna del error arreglar
                erroresSemanticos.add(Error("Las expresiones no representan valores booleanos" , 2 , 2))
                return "$tipo1"
            }

        }
        else if(terminoLogico!= null && expresionLogica1 == null && expresionLogica2 == null)
        {
            val tipo:String? = terminoLogico!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
            if(tipo!=null)
            {
                return tipo
            }
            else
            {
                erroresSemanticos.add(Error("La expresion no esta declarada con anterioridad" , 2,2 ))
                return ""
            }
        }
        return ""
    }


    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, ambito: String, erroresSemanticos: ArrayList<Error>) {

        if(expresionLogica1!= null )
        {
            expresionLogica1!!.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
            if(expresionLogica2 != null)
            {
                expresionLogica2!!.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
            }
        }
        if(terminoLogico!= null )
        {
            terminoLogico!!.analizarSemantica(tablaSimbolos,ambito, erroresSemanticos)
            if(expresionLogica2!=null)
            {
                expresionLogica2!!.analizarSemantica(tablaSimbolos, ambito, erroresSemanticos)
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = ""
        if(expresionLogica1 != null && expresionLogica2 != null && terminoLogico == null)
        {
            codigo += expresionLogica1!!.getJavaCode() +" ${operador!!.getJavaCode()} " + expresionLogica2!!.getJavaCode()
            return codigo
        }else if(terminoLogico!= null && expresionLogica2!= null && expresionLogica1 == null)
        {
            codigo+= terminoLogico!!.getJavaCode() + " ${operador!!.getJavaCode()} " + expresionLogica2!!.getJavaCode()
            return codigo
        }else if(expresionLogica1!= null && terminoLogico == null && expresionLogica2 == null)
        {
            if(operadorNegador!= null)
            {
                codigo += operadorNegador!!.getJavaCode() + "( " + expresionLogica1!!.getJavaCode() + ")"
                return codigo
            }
            else
            {
                codigo += "( " + expresionLogica1!!.getJavaCode() + " )"
                return codigo
            }
        }else if(terminoLogico!= null && expresionLogica2 == null && expresionLogica1 == null)
        {
            codigo+= terminoLogico!!.getJavaCode()
            return codigo
        }
        return codigo
    }


}
package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionLogica() : Expresion() {


    var expresionLogica1:ExpresionLogica? = null
    var expresionLogica2:ExpresionLogica? = null
    var terminoLogico:TerminoLogico? = null
    var operador:Token? = null
    var operadorNegador:Token? = null


    constructor(expresionLogica1: ExpresionLogica?,operador:Token? ,expresionLogica2: ExpresionLogica?):this()
    {
        this.expresionLogica1 = expresionLogica1
        this.operador = operador
        this.expresionLogica2 = expresionLogica2


    }


    constructor(terminoLogico: TerminoLogico,operador: Token,expresionLogica2: ExpresionLogica):this()
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

}
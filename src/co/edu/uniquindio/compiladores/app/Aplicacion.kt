package co.edu.uniquindio.compiladores.app

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico

/*
 *@ Ximena Silva
 */
class Aplicacion : Application() {


    companion object{
        @JvmStatic
        fun main (args :Array<String>)
        {

            launch(Aplicacion::class.java)
        }
    }

}
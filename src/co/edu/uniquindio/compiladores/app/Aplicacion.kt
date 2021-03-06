package co.edu.uniquindio.compiladores.app

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
/*
 *@ Ximena Silva
 */
class Aplicacion : Application() {

    override fun start(p0: Stage?) {
        val loader= FXMLLoader(Aplicacion::class.java.getResource("/inicio.fxml"))
        val parent:Parent = loader.load()
        val scena = Scene(parent)
        p0?.scene= scena
        p0?.title="Analizador Lexico"
        p0?.show()
    }

    companion object{
        @JvmStatic
        fun main (args :Array<String>)
        {

            launch(Aplicacion::class.java)
        }
    }

}
package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.sintaxis.UnidadCompilacion
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.io.File
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


/*
 * @ Ximena Silva
 */
class InicioController : Initializable {

    /*
        Representa componente grafico de donde se extrae el condigo fuente
     */
    @FXML lateinit var codigoFuente: TextArea
    /*
        Tabla en donde se presentan los Tokens identificados
     */
    @FXML lateinit var tablaTokens: TableView<Token>
    /*
        Columna dentro de la tabla que representa el Lexema del Token
     */
    @FXML lateinit var CLexema: TableColumn<Token,String>
    /*
        Columna dentro de la tabla que representa la categoria a la que pertenece el Token

     */
    @FXML lateinit var Ccategoria : TableColumn<Token,String>
    /*
        Columna dentro de la tabla que representa la fila en donde se encuentra el ToKen dentro del codigo fuente
     */
    @FXML lateinit var Cfila : TableColumn<Token,Int>
    /*
        Columna dentro de la tabla que representa la columna en donde se encuentra el Token dentro del condigo fuente
     */
    @FXML lateinit var Ccolumna : TableColumn<Token,Int>

    @FXML lateinit var arbolVisual : TreeView<String>


    @FXML lateinit var Cmensaje: TableColumn<Error,String>
    @FXML lateinit var Cfilaa : TableColumn<Error,Int>
    @FXML lateinit var Ccolumnaa : TableColumn<Error,Int>
    @FXML lateinit var lexico: TableView<Error>

    @FXML lateinit var CmensajeERROR: TableColumn<Error,String>
    @FXML lateinit var CFila : TableColumn<Error,Int>
    @FXML lateinit var CColumnaa : TableColumn<Error,Int>
    @FXML lateinit var sintactico: TableView<Error>

    @FXML lateinit var CmensajeErrorS: TableColumn<Error,String>
    @FXML lateinit var CFilaS : TableColumn<Error,Int>
    @FXML lateinit var CColumnaS : TableColumn<Error,Int>
    @FXML lateinit var semantico: TableView<Error>
    private var lexicoo:AnalizadorLexico? = null
    private var sintaxis:AnalizadorSintactico? = null
    private var unidadCompilacion: UnidadCompilacion? =null
    private var semantica:AnalizadorSemantico? = null


    override fun initialize(location: URL?, resources: ResourceBundle?)
    {
        CLexema.cellValueFactory= PropertyValueFactory("lexema")
        Ccategoria.cellValueFactory = PropertyValueFactory("categoria")
        Cfila.cellValueFactory = PropertyValueFactory("fila")
        Ccolumna.cellValueFactory = PropertyValueFactory("columna")

        Cmensaje.cellValueFactory =PropertyValueFactory("error")
        Cfilaa.cellValueFactory =PropertyValueFactory("Fila")
        Ccolumnaa.cellValueFactory = PropertyValueFactory("Columna")

        CmensajeERROR.cellValueFactory =PropertyValueFactory("error")
        CFila.cellValueFactory =PropertyValueFactory("Fila")
        CColumnaa.cellValueFactory = PropertyValueFactory("Columna")

        CmensajeErrorS.cellValueFactory =PropertyValueFactory("error")
        CFilaS.cellValueFactory =PropertyValueFactory("Fila")
        CColumnaS.cellValueFactory = PropertyValueFactory("Columna")

    }

    /*
        Accion del boton "Analizar Codigo" en donde se analiza el codigo fuente
     */
    @FXML
    fun analizarCodigo(e: ActionEvent)
    {

        if(codigoFuente.text.length>0)
        {

            lexicoo = AnalizadorLexico(codigoFuente = codigoFuente.text)
            lexicoo!!.analizar()

            if(lexicoo!!.listaErroresLexicos.isEmpty())
            {
                sintaxis = AnalizadorSintactico(lexicoo!!.ListaTokens)
                if(sintaxis!!.listaErroresSitacticos.isEmpty())
                {
                    unidadCompilacion= sintaxis!!.esUnidadDeCompilacion()
                    if(unidadCompilacion!=null)
                    {
                        arbolVisual.root = unidadCompilacion!!.getArbolVisual()
                        semantica =AnalizadorSemantico(unidadCompilacion!!)
                        semantica!!.llenarTablaSimbolos()
                        semantica!!.analizarSemantica()
                        semantico.items = FXCollections.observableArrayList(semantica!!.erroresSemanticos)
                    }
                    else
                    {
                        val alerta  = Alert(Alert.AlertType.ERROR)
                        alerta.headerText = null
                        alerta.contentText = "No se pudo constuir el arbol sintactico "
                        alerta.show()
                    }
                }
                else
                {
                    sintactico.items = FXCollections.observableArrayList(sintaxis!!.listaErroresSitacticos)
                }


            }
            else
            {
                tablaTokens.items = FXCollections.observableArrayList(lexicoo!!.ListaTokens)
                lexico.items = FXCollections.observableArrayList(lexicoo!!.listaErroresLexicos)
            }
        }
        else
        {
            val alerta  = Alert(Alert.AlertType.ERROR)
            alerta.headerText = null
            alerta.contentText = "El codigo fuente se encuentra vacio"
            alerta.show()
        }



    }
    @FXML
    fun traducirCodigo(e:ActionEvent){
        if(lexicoo!!.listaErroresLexicos.isEmpty() && sintaxis!!.listaErroresSitacticos.isEmpty() && semantica!!.erroresSemanticos.isEmpty() ){

            val codigo= unidadCompilacion!!.getJavaCode()
            println(codigo)
            File("src/Principal.java").writeText( codigo )
            try {
                val p = Runtime.getRuntime().exec("javac src/Principal.java")
                p.waitFor()
                Runtime.getRuntime().exec("java Principal", null, File("src"))
            } catch (ea: Exception) {
                ea.printStackTrace()
            }
        }
        else
        {
            val alerta  = Alert(Alert.AlertType.ERROR)
            alerta.headerText = null
            alerta.contentText = "El codigo no se pudo traducir por que existen errores "
            alerta.show()
        }

    }



}
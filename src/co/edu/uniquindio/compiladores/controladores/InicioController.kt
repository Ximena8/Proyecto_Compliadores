package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea

import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*


/*
 * @ Ximena Silva, Elier Duque
 */
class InicioController : Initializable {


    /*
        Representa componente grafico de donde se extrae el condigo fuente
     */
    @FXML lateinit var codigoFuente: TextArea
    /*
        Tabla en donde se presentan los Tokens identificados
     */
    @FXML  lateinit var tablaTokens: TableView<Token>
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
        Columna dentro de la tabla que representa la columna en donde se encuentra el Token dentro del codigo fuente
     */
    @FXML lateinit var Ccolumna : TableColumn<Token,Int>





    @FXML lateinit var lexico: TableView<Error>



    override fun initialize(location: URL?, resources: ResourceBundle?)
    {
        CLexema.cellValueFactory= PropertyValueFactory("lexema")
        Ccategoria.cellValueFactory = PropertyValueFactory("categoria")
        Cfila.cellValueFactory = PropertyValueFactory("fila")
        Ccolumna.cellValueFactory = PropertyValueFactory("columna")


    }

    /*
        Accion del boton "Analizar Codigo" en donde se analiza el codigo fuente
     */
    @FXML
    fun analizarCodigo(e: ActionEvent)
    {

        if(codigoFuente.text.length>0)
        {

            var lexicoo = AnalizadorLexico(codigoFuente = codigoFuente.text)
            lexicoo.analizar()
             tablaTokens.items = FXCollections.observableArrayList(lexicoo.ListaTokens)
            lexico.items = FXCollections.observableArrayList(lexicoo.listaErroresLexicos)


        }
        else
        {
            print("El texto esta vacio")
        }



    }


}
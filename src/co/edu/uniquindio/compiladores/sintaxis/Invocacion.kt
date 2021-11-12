package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * <esInvocacion>::= "." identificador "(" [<esListaArgumentos>] ")" "#"
 */

class Invocacion(var ident:Token,var list:ArrayList<Argumento>?):Sentencia() {

    override fun toString(): String{
        return "Invocacion(identificador=$ident,lista=$list)"
    }
    override  fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Invocacion Metodo")
        raiz.children.add(TreeItem("Nombre:${ident.lexema}"))

        if(list != null)
        {
            var raizLista = TreeItem("ListaArgumentos")
            for(i in list!!)
            {
                raizLista.children.add(i.getArbolVisual())
            }
            raiz.children.add(raizLista)
        }


        return raiz
    }

}
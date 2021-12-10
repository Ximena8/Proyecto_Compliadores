package co.edu.uniquindio.compiladores.semantica
import co.edu.uniquindio.compiladores.lexico.Error

class TablaSimbolos(var listaErrores: ArrayList<Error>) {
    var listaSimbolos: ArrayList<Simbolo> = ArrayList()

    /**
     * Permite guardar un símbolo de tipo variable en la tabla de símbolos
     * ambito no va el ambito es solo para las clases??
     */
    fun guardarSimboloValor(nombre: String, tipoDato: String, modificable: Boolean, ambito: String, fila: Int, columna: Int,lista:ArrayList<String>): Simbolo? {
        val s = buscarSimboloValor(nombre, ambito,lista,fila,columna)
        if (s == null ) {
            listaSimbolos.add(Simbolo(nombre, tipoDato,modificable, ambito, fila, columna,lista))
        } else {
            listaErrores.add(Error( "el campo $nombre ya existe dentro del ambito $ambito",fila,columna))
        }
        return null
    }

    /**
     * Permite guardar un símbolo de tipo función en la tabla de símbolos
     * ambito no va el ambito es solo para las clases??
     */

    fun guardarSimboloFuncion(nombre: String, tipoRetorno: String, tipoParametros: ArrayList<String>, ambito: String, fila:Int,columna:Int): Simbolo? {
        val s = buscarSimboloFuncion(nombre, tipoParametros)
        if (s == null) {
            listaSimbolos.add(Simbolo(nombre, tipoRetorno, tipoParametros,ambito))
        } else {
            listaErrores.add(Error("La función $nombre $tipoParametros ya existe dentro del ambito $ambito",fila,columna))
        }
        return null
    }

    /**
     * . permite buscar una Funcion dentro de la tabla de simbolo
     */
    fun buscarSimboloFuncion(nombre: String, tiposParametros: ArrayList<String>): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (simbolo.tiposParametros != null) {
                if (nombre == simbolo.nombre && tiposParametros == simbolo.tiposParametros) {
                    return simbolo
                }
            }
        }
        return null
    }

    /**
     * permite buscar un valor dentro de la tabla de simbolos
     * ambito no va el ambito es solo para las clases??
     */
    fun buscarSimboloValor(nombre: String, ambito: String,tipos:ArrayList<String>,fila:Int,columna:Int): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (nombre == simbolo.nombre &&( ambito == simbolo.ambito ||-1 == simbolo.ambito!!.indexOf(ambito) )&& simbolo.tiposParametros == tipos ) {
                if(simbolo.fila < fila )
                {
                    return simbolo
                }
                if(simbolo.fila == fila && simbolo.columna < columna) {
                    return simbolo
                }
                else if(simbolo.fila>= fila && simbolo.columna> columna)
                {
                    listaErrores.add(Error("La variable $nombre  se declaro despues de llamarla $ambito",fila,columna))
                }
            }
        }
        return null
    }




    /**
     * . permite buscar una Funcion dentro de la tabla de simbolo
     */
    fun buscarTipoRetornoFuncion(nombre: String, tiposParametros: ArrayList<String>): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (simbolo.tiposParametros != null) {
                if (nombre == simbolo.nombre && tiposParametros == simbolo.tiposParametros) {
                    return simbolo
                }
            }
        }
        return null
    }
    fun buscarSimboloValor(nombre: String, ambito: String,fila:Int,columna:Int): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (nombre == simbolo.nombre && ambito == simbolo.ambito ) {
                if(simbolo.fila < fila )
                {
                    return simbolo
                }
                if(simbolo.fila == fila && simbolo.columna < columna) {
                    return simbolo
                }
                else if(simbolo.fila>= fila && simbolo.columna> columna)
                {
                    listaErrores.add(Error("La variable $nombre  se declaro despues de llamarla $ambito",fila,columna))
                }
            }
        }
        return null
    }


    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }

}
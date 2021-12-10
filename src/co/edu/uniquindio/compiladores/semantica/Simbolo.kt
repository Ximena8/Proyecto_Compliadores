package co.edu.uniquindio.compiladores.semantica

class Simbolo {
    var nombre: String? =""
    var tipo: String? =""
    var modificable:Boolean?=false
    var ambito: String? =""
    var fila = 0
    var columna = 0
    var tiposParametros: ArrayList<String>? = null


    /**
     * constructor para crear un simbolo de tipo valor
     */
    constructor(nombre: String, tipo: String, modificable:Boolean , ambito: String, fila:Int, columna:Int,tipoParametros: ArrayList<String>){
        this.nombre = nombre
        this.tipo = tipo
        this.modificable=modificable
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
        this.tiposParametros=tipoParametros
    }

    /**
     *constructor para crear un simbolo de tipo metodo
     */
    constructor(nombre: String, tipoRetorno: String,tipoParametros: ArrayList<String>, ambito:String){
        this.nombre = nombre
        this.tipo = tipoRetorno
        this.tiposParametros = tipoParametros
        this.ambito=ambito

    }

    override fun toString(): String {
        return "Simbolo(nombre=$nombre, tipo=$tipo, modificable=$modificable, ambito=$ambito, fila=$fila, columna=$columna, tiposParametros=$tiposParametros)"
    }

    /**
     * override fun toString(): String {
    return   if (tiposParametros == null) {
    "Simbolo(nombre=$nombre, tipo=$tipo, modificable=$modificable, ambito=$ambito, fila=$fila, columna=$columna)"
    } else {
    "Simbolo(nombre=$nombre, tipo=$tipo, ambito=$ambito, tiposParametros=$tiposParametros)"
    }
    }
     */


}
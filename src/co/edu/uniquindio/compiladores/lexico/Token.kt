package co.edu.uniquindio.compiladores.lexico

//Representa un token del lenguanje y se compone por lexema:palabra o cadena de caracteres, Categoria:Categoria a la que pertenece el lexema, fila y columna representa posicion en donde fue encontrado el lexema en el codigo fuente


class Token (var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int  ){
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }
}
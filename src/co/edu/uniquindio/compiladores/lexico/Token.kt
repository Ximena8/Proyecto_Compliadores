package co.edu.uniquindio.compiladores.lexico

//Representa un token del lenguanje y se compone por lexema:palabra o cadena de caracteres, Categoria:Categoria a la que pertenece el lexema, fila y columna representa posicion en donde fue encontrado el lexema en el codigo fuente


class Token (var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int  ){
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }

    fun getJavaCode(): String {
        if (categoria == Categoria.PALABRA_RESERVADA) {
            if (lexema == "entero") {
                return "int"
            } else if (lexema == "booleano") {
                return "boolean"
            } else if (lexema == "decimal") {
                return "double"
            }
            else if(lexema=="cadena")
            {
                return "String"
            }
            else if(lexema=="mientras")
            {
                return "while"
            }
            else if(lexema=="hacer")
            {
                return "{"
            }
            else if(lexema=="terminar")
            {
                return "}"
            }
            else if(lexema=="arreglo")
            {
                return "[]"
            }
            else if(lexema=="si")
            {
                return "if"
            }

            else if(lexema=="vacio")
            {
                return "void"
            }
            else if(lexema == "verdadero")
            {
                return "true"
            }
            else if (lexema == "falso")
            {
                return "false"
            }
        }
        else if(categoria==Categoria.IDENTIFICADOR)
        {
            return lexema.replace("K", "")
        }else if(categoria== Categoria.OPERADOR_ARITMETICO)
        {
            if(lexema == "G")
            {
                return "+"
            }
            else if(lexema == "H")
            {
                return "-"
            }
            else if(lexema == "I")
            {
                return "*"
            }
            else if(lexema == "J")
            {
                return "/"
            }

        }else if (categoria== Categoria.OPERADOR_LOGICO)
        {
            if(lexema == "¿?")
            {
                return "&&"
            }
            else if(lexema == "¿¿")
            {
                return "||"
            }
            else if(lexema == "?/")
            {
                return "!"
            }

        }else if (categoria== Categoria.OPERADOR_RELACIONAL)
        {
            if(lexema == "?=")
            {
                return "!="
            }
            else if(lexema == "==")
            {
                return "=="
            }
            else if(lexema == ">=")
            {
                return ">="
            }
            else if(lexema == "<=")
            {
                return "<="
            }
            else if(lexema == "<")
            {
                return "<"
            }
            else if(lexema == ">")
            {
                return ">"
            }

        }else if (categoria== Categoria.DECIMAL || categoria == Categoria.ENTERO)
        {
            return lexema

        }else if (categoria == Categoria.CADENA_CARACTERES)
        {
            return lexema.replace("||" , "\"")

        }
        return ""
    }
}
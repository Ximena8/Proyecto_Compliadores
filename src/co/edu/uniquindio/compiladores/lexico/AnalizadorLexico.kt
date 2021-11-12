package co.edu.uniquindio.compiladores.lexico

/*
 * Ximena Silva, Elier Duque
 */

class AnalizadorLexico (var codigoFuente:String){

    var posicionActual=0;
    var caracterActual= codigoFuente[0]
    var finCodigo=0.toChar()
    var ListaTokens =ArrayList<Token>()
    var filaActual=0
    var lexema=""
    var columnaActual=0
    var listaPalabrasReservadas = listOf<String>("entero","hacer","terminar","arreglo","invocar","inicio","fin","inc","dec","metodo","lista","verdadero","falso","boolean","vacio","castin", "leer","var","real","si","para","mientras","privado","publico","paquete","importar","clase","retornar","detener","imprimir")
    var listaErroresLexicos= ArrayList<Error>()
    /*
        Almacena un Token en una lista
        @lexema
        @categoria Categoria a la que pertenece el lexema
        @fila Fila en donde se encontro el lexema
        @columna Columna en donde se encontro el lexema
     */
    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int ) = ListaTokens.add(
        Token(lexema, categoria, fila, columna)
    )

    /**
     * crea un error
     */
    fun reportarError (mensaje:String)
    {
        var error = Error(mensaje, filaActual, columnaActual)
        listaErroresLexicos.add(error)
    }

    /*
        Funcion que recorre el codigo fuente y realiza el analisis Lexico
     */
    fun analizar(){


        while (caracterActual !=finCodigo)
        {
            if(caracterActual == ' ' || caracterActual== '\n' || caracterActual== '\t'){
                obtenerSiguienteCaracter()
                continue
            }
            if(esEntero())continue
            if(esDecimal())continue
            if(esCadenaCaracteres())continue
            if(esIdentificador())continue
            if(esOperadorAritmetico())continue
            if(esOperadorRelacional())continue
            if(esOperadorLogico())continue
            if(esOperadorAsignacion())continue
            if(esParentesis())continue
            if(esLlave())continue
            if(esTerminal())continue
            if(esSeparador())continue
            if(esComentarioLinea())continue
            if(esPalabraReservada())continue
            if(esIncremento())continue
            if(esDecremento())continue
            if(punto())continue
            if(dosPuntos())continue
            if(comentarioBloque())continue
            if(esCaracter())continue
            if(esCorchete())continue


            almacenarToken( "" +caracterActual,Categoria.DESCONOCIDO,filaActual,columnaActual)
            obtenerSiguienteCaracter()
        }
    }


    /*
        Funcion que obtiene el caracter siguiente en el codigo Fuente
     */
    fun  obtenerSiguienteCaracter()
    {
        if (posicionActual == codigoFuente.length-1){
            caracterActual=finCodigo
        }
        else
        {
            if(caracterActual=='\n'){
                filaActual++
                columnaActual=0
            }
            else {
                columnaActual++
            }
            posicionActual++;
            caracterActual=codigoFuente[posicionActual]
        }
    }

    /*
        Funcion encargada de hacer Backtracking cuando no se logra identificar el segmento de codigo
     */
    fun pasoAtras(posicionInicial:Int, filaInicial:Int, columnaInicial:Int)
    {
        posicionActual=posicionInicial
        filaActual= filaInicial
        columnaActual=columnaInicial
        caracterActual= codigoFuente[posicionActual]
    }

    /*
        Funcion encargada de verificar si el segmento de codigo es un numero Entero
     */
    fun esEntero(): Boolean
    {
        if (caracterActual.isDigit()){
            var lexema=""
            var filaInicial= filaActual
            var columnaInicial= columnaActual
            var posicionInicial = posicionActual
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            while (caracterActual.isDigit())
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
            }
            if(caracterActual=='.')
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
            almacenarToken(lexema, Categoria.ENTERO,filaInicial,columnaInicial)

            return true
        }


        return false
    }

    fun esDecimal(): Boolean{
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual== '.' || caracterActual.isDigit())
        {
            if(caracterActual=='.')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual.isDigit())
                {
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual.isDigit()){
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                    }
                    almacenarToken(lexema,Categoria.DECIMAL,filaInicial,columnaInicial)
                    return true
                }
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false

            }
            else
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isDigit()){
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                }
                if(caracterActual=='.')
                {
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual.isDigit()){
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                    }
                    almacenarToken(lexema,Categoria.DECIMAL,filaInicial,columnaInicial)
                    return true
                }
                else
                {
                    reportarError("falto un .")
                    return false
                }
            }

        }

        return false;
    }

    /*
       Funcion encargada de verificar si el segmento de codigo es un identificador
    */
    fun esIdentificador():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='K')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual.isLetter())
            {
                while (caracterActual != 'K') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(!caracterActual.isLetter() &&  caracterActual != 'K')
                    {
                        reportarError("solo letras ")
                    }

                }
            }
            else
            {
                reportarError("no se ingresaron caracteres")
            }

            if(caracterActual=='K')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.IDENTIFICADOR,filaInicial,columnaInicial)
                return true
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        else
        {

            return false
        }
    }

    /*
       Funcion encargada de verificar si el segmento de codigo es un Operador Aritmetico
    */
    fun esOperadorAritmetico():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='G' ||caracterActual=='H' ||caracterActual=='I'||caracterActual=='J')
        {
            lexema+= caracterActual
            if(caracterActual=='G')
            {
                obtenerSiguienteCaracter()
                if(caracterActual=='G')
                {
                    pasoAtras(posicionInicial,filaInicial,columnaInicial)
                    return false
                }
                else
                {
                    almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO,filaInicial,columnaInicial)
                    return true
                }
            }
            if(caracterActual=='H')
            {
                obtenerSiguienteCaracter()
                if(caracterActual=='H')
                {
                    pasoAtras(posicionInicial,filaInicial,columnaInicial)
                    return false
                }
                else
                {
                    almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO,filaInicial,columnaInicial)
                    return true
                }
            }
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO,filaInicial,columnaInicial)
            return true
        }

        return false
    }

    /*
       Funcion encargada de verificar si el segmento de codigo es un OperadorRelacional
    */
    fun esOperadorRelacional():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='?'||caracterActual=='=')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='=')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicial,columnaInicial)
                return true
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        if(caracterActual=='>'||caracterActual=='<')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='=')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicial,columnaInicial)
                return true
            }
            else
            {
                almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicial,columnaInicial)
                return true
            }
        }

        return false
    }

    /*
       Funcion encargada de verificar si el segmento de codigo es un OperadorLogico
    */
    fun esOperadorLogico():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='¿')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='¿'||caracterActual=='?')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicial,columnaInicial)
                return true
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        if(caracterActual=='?')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='/')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicial,columnaInicial)
                return true
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }

        return false
    }

    /*
       Funcion encargada de verificar si el segmento de codigo es un Operador de Asignacion
    */
    fun esOperadorAsignacion():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='+'||caracterActual=='-'||caracterActual=='%'||caracterActual=='*'||caracterActual=='\\')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='=')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_ASIGNACION,filaInicial,columnaInicial)
                return true
            }
            else
            {
                reportarError("tiene que ser un =")
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        if(caracterActual=='&')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_ASIGNACION,filaInicial,columnaInicial)
            return true
        }

        return false
    }

    /*
      Funcion encargada de verificar si el segmento de codigo es una Llave
   */
    fun esParentesis():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='(' )
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.PARENTESIS_IZQ,filaInicial,columnaInicial)
            return true
        }
        if(caracterActual==')' )
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.PARENTESIS_DER,filaInicial,columnaInicial)
            return true
        }


        return false
    }

    /*
        Funcion encargada de verificar si el segmento de codigo es un Parentesis
     */
    fun esLlave():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='{' )
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema,Categoria.LLAVE_IZQ,filaInicial,columnaInicial)
            return true

        }
        if(caracterActual=='}' )
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.LLAVE_DER,filaInicial,columnaInicial)
            return true
        }
        return false
    }
    /*
       Funcion encargada de verificar si el segmento de codigo es un Termial
    */
    fun esTerminal():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='#')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.FIN_SENTENCIA,filaInicial,columnaInicial)
            return true
        }

        return false
    }

    /*
       Funcion encargada de verificar si el segmento de codigo es un Separador
    */
    fun esSeparador():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='/')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='=')
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
            almacenarToken(lexema,Categoria.SEPARADOR,filaInicial,columnaInicial)
            return true
        }

        return false
    }

    /*
           Funcion encargada de verificar si el segmento de codigo es una cadena de caracteres
        */
    fun esCadenaCaracteres():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='|')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='|')
            {

                lexema+=caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual!='|'&& caracterActual!=finCodigo)
                {
                    if(caracterActual=='\\')
                    {
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                        if(caracterActual=='\\'||caracterActual=='n'||caracterActual=='s'||caracterActual=='t'||caracterActual=='|'||caracterActual=='\'')
                        {
                            lexema+=caracterActual
                            obtenerSiguienteCaracter()
                        }
                        else
                        {
                            pasoAtras(posicionInicial,filaInicial,columnaInicial)
                            return false
                        }

                    }
                    else
                    {
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                    }

                }


                if(caracterActual=='|')
                {


                    lexema+=caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual=='|')
                    {
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema,Categoria.CADENA_CARACTERES,filaInicial,columnaInicial)
                        return true
                    }
                    else
                    {
                        pasoAtras(posicionInicial,filaInicial,columnaInicial)
                        return false
                    }
                }
                else
                {
                    pasoAtras(posicionInicial,filaInicial,columnaInicial)
                    return false
                }
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        else
        {
            return false
        }

    }

    /*
       Funcion encargada de verificar si el segmento de codigo es un Comentario de Linea
    */
    fun esComentarioLinea():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='@')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='B')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual=='@')
                {
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual=='B')
                    {
                        pasoAtras(posicionInicial,filaInicial,columnaInicial)
                        return false
                    }
                    else
                    {
                        almacenarToken(lexema,Categoria.COMENTARIOLINEA,filaInicial,columnaInicial)
                        return true
                    }

                }
                else
                {
                    pasoAtras(posicionInicial,filaInicial,columnaInicial)
                    return false
                }

            }

            while(caracterActual!='@'&&caracterActual!=finCodigo)
            {
                if(caracterActual=='\\')
                {
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual=='@')
                    {
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                    }
                }
                else
                {
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                }

            }
            if(caracterActual=='@')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.COMENTARIOLINEA,filaInicial,columnaInicial)
                return true
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }

        }
        else
        {
            return false
        }

    }

    /*
     Funcion encargada de verificar si el segmento de codigo es un Comentario de bloque
  */
    fun comentarioBloque():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='@')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='B')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual=='@')
                {
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual=='B')
                    {
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                        while(caracterActual!='@'&&caracterActual!=finCodigo)
                        {
                            lexema+=caracterActual
                            obtenerSiguienteCaracter()
                        }
                        if(caracterActual=='@')
                        {
                            lexema+=caracterActual
                            obtenerSiguienteCaracter()
                            if(caracterActual=='B')
                            {
                                lexema+=caracterActual
                                obtenerSiguienteCaracter()
                                if(caracterActual=='@')
                                {
                                    lexema+=caracterActual
                                    obtenerSiguienteCaracter()
                                    if(caracterActual=='B')
                                    {
                                        lexema+=caracterActual
                                        obtenerSiguienteCaracter()
                                        almacenarToken(lexema,Categoria.COMENTARIO_BLOQUE,filaInicial,columnaInicial)
                                        return true
                                    }
                                    else
                                    {
                                        pasoAtras(posicionInicial,filaInicial,columnaInicial)
                                        return false
                                    }
                                }
                                else
                                {
                                    pasoAtras(posicionInicial,filaInicial,columnaInicial)
                                    return false
                                }
                            }
                            else
                            {
                                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                                return false
                            }
                        }
                        else
                        {
                            pasoAtras(posicionInicial,filaInicial,columnaInicial)
                            return false
                        }

                    }
                    else
                    {
                        pasoAtras(posicionInicial,filaInicial,columnaInicial)
                        return false
                    }
                }
                else
                {
                    pasoAtras(posicionInicial,filaInicial,columnaInicial)
                    return false
                }
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        return false

    }

    /*
       Funcion encargada de verificar si el segmento de codigo es una Palabra Reservada
    */
    fun esPalabraReservada():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        for (palabra in listaPalabrasReservadas)
        {
            if(caracterActual==palabra[0])
            {

                var centinela = true

                for(caracter in palabra)
                {
                    if(caracter == caracterActual)
                    {
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                    }
                    else
                    {
                        centinela= false
                        pasoAtras(posicionInicial,filaInicial,columnaInicial)
                        break
                    }
                }
                if(centinela==true)
                {
                    almacenarToken(lexema,Categoria.PALABRA_RESERVADA,filaInicial,columnaInicial)
                    return true
                }
                lexema=""
            }

        }


        return false
    }

    /*
       Funcion encargada de verificar si el segmento de codigo es un Operador de Incremento
    */
    fun esIncremento():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='G')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='G')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.INCREMENTO,filaInicial,columnaInicial)
                return true
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        else
        {
            return false
        }


    }

    /*
           Funcion encargada de verificar si el segmento de codigo es un Operador de Decremento
        */
    fun esDecremento():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='H')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='H')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.DECREMENTO,filaInicial,columnaInicial)
                return true
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        else
        {
            return false
        }


    }

    /*
     Funcion encargada de verificar si el segmento de codigo es un punto
  */
    fun punto():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='P')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual =='P')
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
            else
            {
                almacenarToken(lexema,Categoria.PUNTO,filaInicial,columnaInicial)
                return true
            }
        }
        else
        {
            return false
        }

    }

    /*
     Funcion encargada de verificar si el segmento de codigo son DosPuntos
  */
    fun dosPuntos ():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual
        if(caracterActual=='P')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='P')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.DOS_PUNTOS,filaInicial,columnaInicial)
                return true
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        else
        {
            return false
        }
    }

    fun esCaracter():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='|')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual!='|')
            {
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual=='|')
                {
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema,Categoria.CARACTER,filaInicial,columnaInicial)
                    return true
                }
                else
                {
                    pasoAtras(posicionInicial,filaInicial,columnaInicial)
                    return false
                }
            }
            else
            {
                pasoAtras(posicionInicial,filaInicial,columnaInicial)
                return false
            }

        }
        else
        {
            return false
        }

    }

    fun esCorchete():Boolean
    {
        var lexema=""
        var filaInicial= filaActual
        var columnaInicial= columnaActual
        var posicionInicial = posicionActual

        if(caracterActual=='[')
        {
            lexema+=caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.CORCHETE_IZQ,filaInicial,columnaInicial)
            return true
        }
        if(caracterActual==']')
        {

            lexema+=caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.CORCHETE_DER,filaInicial,columnaInicial)
            return true


        }

        return false

    }



}
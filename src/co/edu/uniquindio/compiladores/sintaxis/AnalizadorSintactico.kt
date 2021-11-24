package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
/*
 * Ximena Silva
 */
class AnalizadorSintactico(var listaTokens:ArrayList<Token>) {

    var posActual = 0
    var posAuxiliar=0
    var tokenActual = listaTokens[posActual]
    var listaErroresSitacticos = ArrayList<Error>()

    /**
     * Metodo que obtiene el token siguiente de la lista de tokens
     */
    fun obtenerSiguienteToken ()
    {
        posActual++;

        if(posActual < listaTokens.size)
        {
            tokenActual = listaTokens[posActual]
        }
    }

    /**
     * Metodo que obtiene el token anterior de la lista de tokens
     */
    fun obtenerAnteriorToken ()
    {
        posActual--;

        if(posActual < listaTokens.size)
        {

            tokenActual = listaTokens[posActual]
        }
    }

    /**
     * Metodo que obtiene el token anterior de la lista de tokens
     */
    fun pasoAtras()
    {
        posActual--;

        if(posActual>0)
        {
            tokenActual = listaTokens[posActual]
        }
    }

    /**
     * Metodo que reporta un error
     */
    fun reportarError (MensajeError:String)
    {
        println(MensajeError)
        obtenerAnteriorToken()
        var error = Error(error = MensajeError, fila = tokenActual.fila, columna = tokenActual.columna)
        listaErroresSitacticos.add(error)
        obtenerSiguienteToken()
    }

    /**
     * funcion que verifica si es unidad de compilacion
     * <UnidadDeCompilacion>::= <ListaFunciones>
     */
    fun esUnidadDeCompilacion():UnidadCompilacion?
    {
        val listaFunciones:ArrayList<Funcion> = esListaFunciones()

        return if (listaFunciones.size > 0)
        {

            return  UnidadCompilacion(listaFunciones)
        }
        else return null
    }

    /**
     *funcion que verifica si es lista de funciones
     * <ListaFunciones>:: = <Funcion>[<ListaFunciones>]
     */
    fun esListaFunciones():ArrayList<Funcion>
    {
        var listaFunciones:ArrayList<Funcion> = ArrayList<Funcion>()
        var funcion:Funcion? = esFuncion()

        while(funcion!= null)
        {

            listaFunciones.add(funcion)
            funcion = esFuncion()
        }

        return listaFunciones

    }

    /**
     * funcion que verifica si es una función
     * <Funcion>::= metodo identificador <TipoRetorno> "(" [<ListaParametros>] ")" "{" <ListaSentencias>"}"
     */
    fun esFuncion():Funcion?
    {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "metodo")
        {
            obtenerSiguienteToken()
            if(tokenActual.categoria== Categoria.IDENTIFICADOR)
            {
                val identificador = tokenActual
                obtenerSiguienteToken()

                var tipoRetorno:Token? = esTipoRetorno()

                if(tipoRetorno!=null)
                { obtenerSiguienteToken()

                    if(tokenActual.categoria== Categoria.PARENTESIS_IZQ)
                    {
                        obtenerSiguienteToken()
                        var listaParametros:ArrayList<Parametro> = esListaParametro()

                        if(tokenActual.categoria==Categoria.PARENTESIS_DER)
                        {
                            obtenerSiguienteToken()
                            if(tokenActual.categoria == Categoria.LLAVE_IZQ)
                            {
                                obtenerSiguienteToken()
                                var lista:ArrayList<Sentencia> = esListaSentencia()
                                if(lista != null)
                                {

                                    if(tokenActual.categoria == Categoria.LLAVE_DER)
                                    {
                                        var metodo:Funcion = Funcion(identificador,tipoRetorno,listaParametros,lista)
                                        obtenerSiguienteToken()
                                        return metodo
                                    }
                                    else
                                    {
                                        reportarError("Falta llave  derecha")
                                        return null
                                    }
                                }
                                else
                                {
                                    reportarError("Falta lista de sentencias esta vacio ")
                                    return null
                                }


                            }
                            else
                            {
                                reportarError("Falta llave  izquierda")
                                return null
                            }


                        }
                        else
                        {
                            reportarError("Falta Parentesis derecho")
                            return null
                        }

                    }
                    else
                    {
                        reportarError("Falta el parentesis izquierdo")
                        return null
                    }
                }
                else
                {
                    reportarError("El retorno del metodo no esta especificado ")
                    return null
                }
            }
        }
        return null

    }

    /**
     * funcion que verifica si es Lista de parametros
     * <ListaParametro>::=  <Parametro>  ["/"<ListaParametros>]
     */
    fun esListaParametro():ArrayList<Parametro> {
        var lista: ArrayList<Parametro> = ArrayList<Parametro>()
        var parametro: Parametro? = esParametro()

        while(parametro!= null)
        {
            obtenerSiguienteToken()
            lista.add(parametro)
            if(tokenActual.categoria == Categoria.SEPARADOR)
            {
                obtenerSiguienteToken()
                parametro = esParametro()

                if(parametro!=null)
                {
                    continue
                }
                else
                {
                    reportarError("Luego del separador deberia ir un parametro")
                }
            }
            else
            {
                if(tokenActual.categoria == Categoria.PARENTESIS_DER)
                {
                    return lista
                }
                else
                {
                    parametro = esParametro()
                    if(parametro!= null)
                    {
                        reportarError("Falta el separador entre los argumentos")
                    }
                    else
                    {
                        return lista
                    }
                }
            }
        }

        if(tokenActual.categoria == Categoria.SEPARADOR)
        {
            reportarError("Existe una division entre parametros pero no existe ningun  parametro")
        }
        else
        {
            return lista

        }

        return lista

    }

    /**
     * funcion que verifica si es parametro
     * <Parametro>::=  <TipoDato> Identificador
     */
    fun esParametro():Parametro? {
        var tipoDato:Token? = esTipoDato()
        if(tipoDato!=null)
        {
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.IDENTIFICADOR)
            {
                val identificador:Token = tokenActual
                var parametro:Parametro= Parametro(tipoDato,identificador)
                return parametro

            }
        }
        else
        {


            if(tokenActual.categoria== Categoria.IDENTIFICADOR)
            {
                reportarError("El tipo de dato del parametro no esta especificado")


            }
            else
            {
                return null
            }
        }
        return null

    }


    /**
     * funcion que verifica si es tipoRetorno
     * <TipoRetorno> entero |real | boolean | vacio
     */
    fun esTipoRetorno():Token?
    {
        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA)
        {
            if(tokenActual.lexema== "entero" ||tokenActual.lexema== "real" ||tokenActual.lexema== "booleano" ||tokenActual.lexema== "vacio")
            {
                return tokenActual
            }
            else
            {

                reportarError("El retorno no coincide con los tipos de datos definidos")
                return null
            }
        }

        return null
    }

    /**
     * funcion que verifica si es tipoDato
     * <TipoDato>::= entero | real | boolean
     */
    fun esTipoDato():Token?
    {
        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA)
        {
            if(tokenActual.lexema== "entero" ||tokenActual.lexema== "real" ||tokenActual.lexema== "booleano" )
            {
                return tokenActual
            }
            else
            {

                reportarError("El retorno no coincide con los tipos de datos definidos")
                return null
            }
        }

        return null
    }



    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una lista de expresiones
     * <Expresion>::= <ExpresionLogica> | <ExpresionRelaciona> | <ExpresionAritmetica> | <ExpresionCadena>
     */
    fun esExpresion():Expresion?
    {
        salvarToken()
        var expresion:Expresion? = esExpresionLogica()
        if(expresion!=null)
        {
            return expresion
        }
        cargarToken()
        expresion = esExpresionRelacional()
        if(expresion!=null)
        {
            return expresion
        }
        cargarToken()
        expresion = esExpresionAritmetica()
        if(expresion!=null)
        {
            return expresion
        }
        cargarToken()
        expresion = esExpresionCadena()
        if(expresion!= null)
        {
            return expresion
        }
        cargarToken()
        return null
    }

    /**
     * Metodo que se encarga de guardar una  posicion en especifico por si no se logra determinar una expresion
     */
    fun salvarToken ()
    {
        posAuxiliar  = posActual

    }

    /**
     * Metodo que se encarga de actualiar la posicion actual en caso de que una expresion retorne null
     */
    fun cargarToken ()
    {
        posActual = posAuxiliar
        tokenActual=listaTokens[posActual]
    }


    /**
     *  Metodo que se encarga de verificar si una secuencia de tokens constituye una Expresion Aritmetica
     *<ExpresionAritmetica>::="("<ExpresionAritmetica>")" [<Z>]| <ValorNumerico>[<Z>]
    <Z>::=OPA<ExpresionAritmetica>[<Z>]
     */
    fun esExpresionAritmetica():ExpresionAritmetica?
    {
        if(tokenActual.categoria== Categoria.PARENTESIS_IZQ)
        {
            obtenerSiguienteToken()
            val expresion1:ExpresionAritmetica? = esExpresionAritmetica()
            if(expresion1!=null)
            {
                if(tokenActual.categoria==Categoria.PARENTESIS_DER)
                {
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO)
                    {
                        val operador:Token= tokenActual
                        obtenerSiguienteToken()
                        var expresion2 = esExpresionAritmetica()

                        if(expresion2!=null)
                        {
                            return ExpresionAritmetica(expresion1, operador,expresion2)
                        }
                        else
                        {
                            reportarError("Falta la expresion aritmentica luego del operador")
                            return null
                        }
                    }
                    else
                    {
                        return ExpresionAritmetica(expresion1)
                    }
                }
                else
                {
                    reportarError("Falta el parentesis derecho")
                    return null
                }
            }
            else
            {
                reportarError("Deberia existir una expresion aritmetica luego del parentesis")
            }
        }
        else
        {
            val valorNumerico = esValorNumerico ()
            if(valorNumerico!= null)
            {
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.OPERADOR_ARITMETICO)
                {
                    val operador:Token = tokenActual
                    obtenerSiguienteToken()
                    val expresion2 :ExpresionAritmetica? = esExpresionAritmetica()
                    if(expresion2!=null)
                    {
                        return ExpresionAritmetica(valorNumerico,operador,expresion2)
                    }
                    else
                    {
                        reportarError("Luego drel operador deberia i una expresion aritmetica ")
                    }

                }
                else
                {
                    return ExpresionAritmetica(valorNumerico)
                }

            }
            else
            {
                return null
            }
        }
        return null
    }

    /**
     * metodo que verifique si es ValorNumerico
     * <ValorNumerico>::= operador_Aritmetico ENTERO | operador_Aritmetico DECIMAL | operador_Aritmetico IDENTIFICADOR
     */
    fun esValorNumerico():ValorNumerico?
    {

        if(tokenActual.categoria==Categoria.OPERADOR_ARITMETICO && (tokenActual.lexema=="G" || tokenActual.lexema=="H"))
        {
            val signo:Token= tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.ENTERO||tokenActual.categoria==Categoria.DECIMAL||tokenActual.categoria==Categoria.IDENTIFICADOR )
            {
                val valor:Token = tokenActual
                return ValorNumerico(signo,valor)
            }
            else
            {
                reportarError("Falta el valor numerico")
                return null
            }
        }
        else
        {
            if(tokenActual.categoria==Categoria.ENTERO||tokenActual.categoria==Categoria.DECIMAL||tokenActual.categoria==Categoria.IDENTIFICADOR )
            {
                val valor:Token = tokenActual
                return ValorNumerico(null,valor)
            }
            else
            {
                return null
            }
        }

    }


    /**
     *  Metodo que se encarga de verificar si una secuencia de tokens constituye una Expresion Relacional
     *  <ExpresionRelacional>::= ["("]<ExpresionAritmetica>OPR<ExpresionAritmetica>[")"]
     */
    fun esExpresionRelacional():ExpresionRelacional?
    {
        if(tokenActual.categoria == Categoria.PARENTESIS_IZQ)
        {
            obtenerSiguienteToken()
            val expresionAritmetica1:ExpresionAritmetica?= esExpresionAritmetica()
            if(expresionAritmetica1!=null)
            {
                if(tokenActual.categoria==Categoria.OPERADOR_RELACIONAL)
                {
                    val operador:Token = tokenActual
                    obtenerSiguienteToken()
                    val expresionAritmetica2:ExpresionAritmetica? = esExpresionAritmetica()
                    if(expresionAritmetica2!=null)
                    {
                        if(tokenActual.categoria==Categoria.PARENTESIS_DER)
                        {

                            return ExpresionRelacional(expresionAritmetica1, operador , expresionAritmetica2)


                        }
                        else
                        {
                            reportarError("falta un paretesis derecho")
                            return null
                        }
                    }
                    else
                    {
                        reportarError("Luego de un operador logico deberia ir una expresion aritmetica")
                        return null
                    }

                }
                else
                {
                    return null
                }
            }

        }
        else
        {
            val expresionAritmetica1:ExpresionAritmetica?= esExpresionAritmetica()
            if(expresionAritmetica1!=null)
            {
                if(tokenActual.categoria==Categoria.OPERADOR_RELACIONAL)
                {
                    val operador:Token = tokenActual
                    obtenerSiguienteToken()
                    val expresionAritmetica2:ExpresionAritmetica? = esExpresionAritmetica()
                    if(expresionAritmetica2!=null)
                    {

                        return ExpresionRelacional(expresionAritmetica1, operador , expresionAritmetica2)
                    }
                    else
                    {
                        reportarError("Luego de un operador logico deberia ir una expresion aritmetica")
                        return null
                    }

                }
                else
                {
                    return null
                }
            }
        }

        return null
    }

    /**
     *  Metodo que se encarga de verificar si una secuencia de tokens constituye una Expresion Logica
     *  <ExpresionLogica>::=<TerminoLogico>OPLBINARIO<ExpresionLogica>[<Z>] |<TerminoLogico>[<Z>] | ["?/"]"(" <ExpresionLogica>")"[<Z>]
     *  <Z>::=OPLBINARIO <ExpresionLogica> [<Z>] | OPLBINARIO <TerminoLogico> [<Z>
     */
    fun esExpresionLogica():ExpresionLogica?
    {

        if(tokenActual.categoria==Categoria.OPERADOR_LOGICO && tokenActual.lexema == "?/")
        {
            val operadorNegador:Token = tokenActual
            obtenerSiguienteToken()
            val expresion:ExpresionLogica? = esExpresionLogica()
            if(expresion!=null)
            {
                return ExpresionLogica(operadorNegador,expresion)
            }
            else
            {
                reportarError("Luego del operador de negacion deberia ir una expresion Logica")
            }

        }

        if(tokenActual.categoria == Categoria.LLAVE_IZQ)
        {
            obtenerSiguienteToken()
            val expresion1:ExpresionLogica? =esExpresionLogica()

            if(expresion1!=null)
            {
                if(tokenActual.categoria == Categoria.LLAVE_DER)
                {
                    obtenerSiguienteToken()
                    if(tokenActual.categoria== Categoria.OPERADOR_LOGICO)
                    {
                        val operador:Token = tokenActual
                        obtenerSiguienteToken()
                        val expresion2:ExpresionLogica? = esExpresionLogica()
                        if(expresion2!=null)
                        {
                            return ExpresionLogica(expresion1,operador,expresion2)
                        }
                        else
                        {
                            reportarError("Luego del operador deberia existir una expresion logica")
                            return null
                        }



                    }
                    else
                    {
                        return ExpresionLogica(expresion1)
                    }

                }
                else
                {
                    reportarError("Falta el parentesis derecho luego de la expresion Logica")
                    return null
                }


            }
            else
            {
                reportarError("Deberia existir una expresion logica luego del parentesis")
                return null
            }
        }
        else
        {
            val terminoLogico:TerminoLogico? = esTerminoLogico()
            if(terminoLogico!=null)
            {
                if(tokenActual.categoria==Categoria.OPERADOR_LOGICO)
                {
                    var operador:Token = tokenActual
                    obtenerSiguienteToken()
                    var expresion2:ExpresionLogica? = esExpresionLogica()
                    if(expresion2!=null)
                    {
                        return ExpresionLogica(terminoLogico,operador,expresion2)
                    }
                    else
                    {
                        reportarError("Luego del operador deberia ir una expresion logica")
                        return null
                    }

                }
                else
                {
                    return ExpresionLogica(terminoLogico)
                }
            }


        }


        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye terminologica
     *<TerminoLogico>::= verdadero | falso | <ExpresionRelacional>
     */
    fun esTerminoLogico():TerminoLogico?
    {

        val expresionRelacional:ExpresionRelacional? =esExpresionRelacional()
        if(expresionRelacional!=null)
        {
            obtenerSiguienteToken()
            return TerminoLogico(expresionRelacional)
        }
        else
        {
            if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && (tokenActual.lexema == "verdadero" || tokenActual.lexema =="falso"))
            {
                val booleano:Token = tokenActual
                obtenerSiguienteToken()

                return TerminoLogico(booleano)
            }
            return null
        }


    }


    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una Expresion cadena
     * <ExpresionCadena>::= CadenaCaracteres ["G" Identificador [<ExpresionCadena>]] | Identificador ["G" CadenaCaracteres [<ExpresionCadena>]]
     */
    fun esExpresionCadena():ExpresionCadena?
    {

        if(tokenActual.categoria== Categoria.CADENA_CARACTERES)
        {
            val cadena:Token= tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.OPERADOR_ARITMETICO && tokenActual.lexema == "G")
            {
                obtenerSiguienteToken()
                val expresionCadena:ExpresionCadena? = esExpresionCadena()
                if(expresionCadena!=null)
                {

                    return ExpresionCadena(cadena,expresionCadena)
                }
                else
                {
                    reportarError("Falta una expresion de tipo cadena luego del G")
                }
            }
            else
            {

                obtenerSiguienteToken()
                return ExpresionCadena(cadena)
            }

        }
        else
        {
            if(tokenActual.categoria==Categoria.IDENTIFICADOR)
            {
                val identificador:Token = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.OPERADOR_ARITMETICO && tokenActual.lexema == "G")
                {
                    obtenerSiguienteToken()
                    val expresionCadena:ExpresionCadena? = esExpresionCadena()
                    if(expresionCadena!=null)
                    {

                        return ExpresionCadena(identificador,expresionCadena)
                    }
                    else
                    {
                        reportarError("Falta una expresion de tipo cadena luego del G")
                    }
                }
                else
                {
                    return ExpresionCadena(identificador)
                }

            }
        }

        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una lista de sentencias
     * <ListaSentencias> ::= <Sentencia> [<ListaSentencias>]
     */
    fun esListaSentencia(): ArrayList<Sentencia>
    {
        var lista: ArrayList<Sentencia> = ArrayList<Sentencia>()
        var sentencia: Sentencia? = esSentencia()

        while(sentencia!= null)
        {
            lista.add(sentencia)
            sentencia=esSentencia()
        }

        return lista
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una sentencia
    <Sentencia> ::= <Decision> | <DeclaracionVariables> | <AsignacionDeVariable> | <DeclaracionArreglo><inicializacion>|
    <Impresion> | <Mientras> | <Retorno>| <Lectura> | <InvocacionFuncion> | <incremento> | <Decremento> /<castin> | <Impresion> |<CicloMientras>/
     */
    fun esSentencia():Sentencia?
    {
        var sentencia:Sentencia?=esDecision()
        if(sentencia!=null){
            return sentencia
        }
        sentencia=esDeclaracionVariables()
        if(sentencia!=null){
            return sentencia
        }
        sentencia=esAsignacionVariable()
        if(sentencia!=null){
            return sentencia
        }
        sentencia=esDeclaracionArreglos()
        if(sentencia!=null){
            return sentencia
        }
        sentencia=esInicializacion()
        if(sentencia!=null){
            return sentencia
        }

        sentencia=esImpresion()
        if(sentencia!=null){
            return sentencia
        }
        sentencia=esMientras()
        if(sentencia!=null){
            return sentencia
        }
        sentencia=esRetorno()
        if(sentencia!= null)
        {
            return sentencia
        }
        sentencia=esLectura()
        if(sentencia!= null)
        {
            return sentencia
        }
        sentencia=esInvocacionFuncion()
        if(sentencia!= null)
        {
            return sentencia
        }
        sentencia=esIncremento()
        if(sentencia!= null)
        {
            return sentencia
        }
        sentencia=esDecremento()
        if(sentencia!= null)
        {
            return sentencia
        }
        sentencia=esCastin()
        if(sentencia!=null){
            return sentencia
        }
        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una decision
     * <Desicion> ::= si "(" <ExpresionLogica> ")"  inicio <ListaSentencias> fin
     */
    fun esDecision(): Decision?
    {

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "si") {

            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQ) {
                obtenerSiguienteToken()
                var expLogica: ExpresionLogica? = esExpresionLogica()
                if (expLogica != null) {

                    if (tokenActual.categoria == Categoria.PARENTESIS_DER)
                    {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "inicio")
                        {
                            obtenerSiguienteToken()
                            var lisSentencias: ArrayList<Sentencia> = esListaSentencia()
                            if (lisSentencias != null)
                            {
                                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "fin")
                                {
                                    obtenerSiguienteToken()
                                    var des: Decision = Decision(expLogica, lisSentencias)
                                    return des
                                }
                                else {
                                    reportarError("falta la palabra fin en la desicion ")
                                    return null
                                }

                            } else {
                                reportarError("falta la lista de sentencias  en la desicion ")
                                return null
                            }

                        }
                        else{
                            reportarError("fata la palabra inicio en la desicion")
                            return null
                        }
                    }
                    else{
                        reportarError("no se el parentesis derecho   en la desicion")
                        return null
                    }
                } else {
                    reportarError("no se encontro una expresion logica  en la desicion")
                    return null
                }
            } else {
                reportarError("no se encontro parentesis izquierdo  en la desicion")
                return null
            }
        }

        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una declaracion de variable
     * <DeclaracionVariables> ::= Var <TipoDato> ​"​&​" Identificador "#"
     */
    fun esDeclaracionVariables():DeclaracionVariable?
    {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "var")
        {
            obtenerSiguienteToken()
            var tipoDato:Token? = esTipoDato()

            if(tipoDato!=null)
            {
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.OPERADOR_ASIGNACION && tokenActual.lexema=="&")
                {
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.IDENTIFICADOR)
                    {
                        var identificador:Token= tokenActual
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.FIN_SENTENCIA)
                        {
                            obtenerSiguienteToken()
                            var declaracion:DeclaracionVariable= DeclaracionVariable(tipoDato,identificador)
                            return declaracion
                        }
                        else{
                            reportarError("falto la terminal que es # en Declaracion de variable")
                            return null
                        }
                    }
                    else{
                        reportarError("falto el identificador en Declaracion de variable")
                        return null
                    }

                }
                else
                {
                    reportarError("falta el operador de asignacion & en Declaracion de variable")
                    return null
                }
            }
            else
            {
                reportarError("no se encontro ningun tipo de dato en Declaracion de variable")
                return null
            }
        }

        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una asignacion
     *   <Asignacion> ::= "PP" identificador OperadorAsignacion <Expresion> "#"
     */
    fun esAsignacionVariable(): Asignacion?
    {
        if(tokenActual.categoria == Categoria.DOS_PUNTOS)
        {
            obtenerSiguienteToken()

            if(tokenActual.categoria==Categoria.IDENTIFICADOR)
            {
                var identificador: Token =tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.OPERADOR_ASIGNACION)
                {
                    obtenerSiguienteToken()
                    var asignacion:Expresion?= esExpresion()
                    if(asignacion != null)
                    {
                        if(tokenActual.categoria==Categoria.FIN_SENTENCIA)
                        {
                            obtenerSiguienteToken()
                            var asig:Asignacion= Asignacion(identificador,asignacion)
                            return asig
                        }
                        else
                        {
                            reportarError("no se encontro la terminal en asignacion")
                            return null
                        }
                    }
                    else
                    {
                        reportarError("no se encontro ninguna expresion en asignacion")
                        return null
                    }
                }
                else
                {

                    reportarError("no se encontro  ningun operador asignacion en asignacion  ")
                    return null
                }
            }
            else {
                reportarError("Falta identificador  en la Asignacion")
                return null
            }
        }
        return null
    }


    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un impresion
     * <Impresion> ::= imprimir "(" <ExpresionCadena> ")" "#"
     */
    fun  esImpresion():Impresion?
    {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "imprimir")
        {
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.PARENTESIS_IZQ)
            {
                obtenerSiguienteToken()
                var exp : Expresion?=esExpresion()
                var num: Token?=null
                if(exp!=null) {


                    if(tokenActual.categoria == Categoria.PARENTESIS_DER)
                    {
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.FIN_SENTENCIA)
                        {
                            obtenerSiguienteToken()
                            var imp:Impresion= Impresion(exp ,num)
                            return imp
                        }
                        else
                        {
                            reportarError("no se encontro  la terminal #")
                            return null
                        }
                    }
                    else{

                        reportarError("no se encontro  parentesisDer ")
                        return null
                    }

                }
                else {
                    reportarError("no se encontro  ninguna cadena de texto ni un tipo de dato  en imprimir")
                    return null
                }
            }
            else
            {
                reportarError("no se encontro  el parentesisIzquierdo")
                return null
            }

        }
        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un ciclo mientras
     *  <mientras> ::= mientras "(" <ExpresionRelacional> ")" hacer  <Sentencia> [<ListaSentencias>] terminar
     */

    fun esMientras(): Mientras?
    {
        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema == "mientras") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQ) {
                obtenerSiguienteToken()
                var expresion: ExpresionRelacional? = esExpresionRelacional()
                if (expresion != null) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.PARENTESIS_DER) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "hacer")
                        {
                            obtenerSiguienteToken()
                            var lista: ArrayList<Sentencia> = esListaSentencia()
                            if (lista != null) {
                                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "terminar")
                                {
                                    obtenerSiguienteToken()
                                    var mientras: Mientras = Mientras(expresion, lista)
                                    return mientras
                                } else {

                                    reportarError("no se encontro  la palabra terminar en el ciclo  mientras ")
                                    return null
                                }
                            } else {
                                reportarError(" no se encontro ninguna sentencia en  mientras")
                                return null
                            }

                        } else {
                            reportarError(" no se encontro la palabra hacer en el ciclo  mientras")
                            return null
                        }
                    } else {
                        reportarError("no se encontro  el parentesis derecho en el ciclo  mientras")
                        return null
                    }
                } else {
                    reportarError("no se encontro  ninguna expresion en el ciclo  mientras")
                    return null
                }
            } else {
                reportarError("no se encontro  el parentesis izquierdo en el ciclo  mientras  ")
                return null
            }
        }
        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un un arreglo
     * <DeclaracionArreglo> ::= arreglo <TipoDato>  identificador "#" mas identificadores
     */

    fun esDeclaracionArreglos() : DeclaracionArreglo?
    {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "arreglo")
        {
            obtenerSiguienteToken()
            var tipo: Token? = esTipoDato()
            if(tipo != null)
            {
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR)
                {
                    var identificador:Token = tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.FIN_SENTENCIA)
                    {
                        obtenerSiguienteToken()
                        var declaracion: DeclaracionArreglo= DeclaracionArreglo(tipo,identificador)
                        return declaracion
                    }
                    else
                    {
                        reportarError("falto la terminal # en la declaracion de arreglo")
                        return null
                    }
                }
                else
                {
                    reportarError("no hay identificadores en la declaracion de arreglo")
                    return null
                }
            }
            else
            {
                reportarError("no se encontro ningun tipo de dato en la declaracion de arreglo ")
                return null
            }

        }
        return null
    }


    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un arreglo
     *<InicializacionArreglo> ::=  "P"identificador "[" <Entero> "]" operadorAsignaccion <Expresion> "#"
     */

    fun esInicializacion(): InicializacionArreglo?
    {

        if(tokenActual.categoria == Categoria.PUNTO)
        {

            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador: Token = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.CORCHETE_IZQ) {
                    obtenerSiguienteToken()

                    if (tokenActual.categoria == Categoria.ENTERO) {
                        var numero: Token = tokenActual
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.CORCHETE_DER) {
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) {
                                obtenerSiguienteToken()
                                var exp: Expresion? = esExpresion()
                                if (exp != null) {
                                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                                        obtenerSiguienteToken()
                                        var ini: InicializacionArreglo = InicializacionArreglo(identificador, exp)
                                        return ini
                                    } else {
                                        reportarError("falta la terminal de la inicializacion ")
                                        return null
                                    }
                                } else {
                                    reportarError("no se encontro ninguna expresion de la inicializacion ")
                                    return null
                                }

                            } else {
                                reportarError("no se encontro ningun operador asignacion de la inicializacion")
                                return null
                            }
                        } else {
                            reportarError("no se encontro el corchete derecho de la inicializacion")
                            return null
                        }
                    } else {
                        reportarError("no se encontro un numero entero donde se va agregar la expresion de la inicializacion")
                        return null
                    }
                } else {
                    reportarError("falta el corchete izquierdo de la inicializacion")
                    return null
                }
            }
            else
            {
                reportarError("falta el identificador de la inicializacion")
                return null
            }
        }
        return null;
    }



    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un retorno
     * <Retorno>::= retornar <esExpresion>  "#"
     */
    fun esRetorno(): Retorno? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA  && tokenActual.lexema == "retornar") {
            obtenerSiguienteToken();
            var exp:Expresion?= esExpresion()
            if (exp != null) {
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken()
                    var ret:Retorno= Retorno(exp)
                    return ret
                }
                else {
                    reportarError("Falta el final de sentencia en el retorno")
                    return null
                }
            }
            else {
                reportarError("Falta la expresion en el retorno")
                return null
            }
        }
        return null;
    }


    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un lectura
     * <Leer> ::= leer identificador "#"
     */
    fun  esLectura(): Leer? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "leer")
        {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador:Token=tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.FIN_SENTENCIA)
                {
                    obtenerSiguienteToken()
                    var lectura:Leer= Leer(identificador)
                    return lectura
                } else {
                    reportarError("Falta el final de sentencia en el leer")
                    return null

                }

            } else {
                reportarError("Falta el identificador de leer")
                return null

            }

        }

        return null;
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un argumento
     * <Argumento> ::= <ExpresionCadena>
     */
    fun   esArgumento(): Argumento? {
        var exp:ExpresionCadena? =esExpresionCadena()
        if (exp != null) {
            obtenerAnteriorToken()
            var arg:Argumento=Argumento(exp)
            return  arg
        }
        return null;
    }


    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una lista de argumentos
     * <ListaArgumentos> ::= <Argumento> ["/"<ListaArgumentos>]
     */
    fun esListaArgumentos():ArrayList<Argumento>? {
        var lista: ArrayList<Argumento> = ArrayList<Argumento>()
        var argumento: Argumento? = esArgumento()

        while(argumento!= null)
        {
            lista.add(argumento)
            if(tokenActual.categoria == Categoria.SEPARADOR)
            {
                obtenerSiguienteToken()
                argumento = esArgumento()

                if(argumento!=null)
                {
                    continue
                }
                else
                {
                    reportarError("Luego del separador deberia ir un argumento")
                }
            }
            else
            {
                if(tokenActual.categoria == Categoria.PARENTESIS_DER)
                {
                    return lista
                }
                else
                {
                    argumento = esArgumento()
                    if(argumento!= null)
                    {
                        reportarError("Falta el separador entre los argumentos")
                    }
                    else
                    {
                        return lista
                    }
                }
            }
        }

        if(tokenActual.categoria == Categoria.SEPARADOR)
        {
            reportarError("Existe una division entre parametros pero no existe ningun  argumento")
        }
        else
        {
            return lista
        }
        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye una invocacion de funcion
     * <esInvocacion>::= invocar identificador "(" [<esListaArgumentos>] ")" "#"
     */

    fun esInvocacionFuncion():Invocacion? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "invocar") {
            obtenerSiguienteToken();
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var variable:Token=tokenActual
                obtenerSiguienteToken();
                if (tokenActual.categoria == Categoria.PARENTESIS_IZQ) {
                    obtenerSiguienteToken()
                    var list:ArrayList<Argumento>? = esListaArgumentos()

                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        var inv:Invocacion=Invocacion(variable,list)
                        return inv
                    } else {
                        reportarError("Falta el finsentencia en la invocacion")
                        return null
                    }
                } else {
                    reportarError("Falta parentisis izquierdo en la invocacion")
                    return null
                }
            } else {
                reportarError("Falta el identificador de la funcion a invocar")
                return null
            }
        }
        return null
    }

    /**
     *  Metodo que se encarga de verificar si una secuencia de tokens constituye un incremento
     *<Incremento> ::= inc identificador <Operador incremento> “#”
     */
    fun esIncremento():Incremento?
    {

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA  &&  tokenActual.lexema == "inc")
        {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var ident: Token = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.INCREMENTO) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()

                        var incremento: Incremento = Incremento(ident)
                        return incremento
                    } else {
                        reportarError("Falta terminal  en el incremento")
                        return null
                    }
                } else {
                    reportarError("Falta el operador incremento en el incremento")
                    return null
                }

            }
            else {
                reportarError("Falta el el identificador en el incremento")
                return null
            }
        }
        return null
    }

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un castin
     *   <CastingVariable> ::= castin <tipoDato> identificador "#"
     */
    fun esCastin():Castin?
    {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA  && tokenActual.lexema == "castin")
        {
            obtenerSiguienteToken()
            var tipo :Token?=esTipoDato()
            if (tipo != null)
            {
                obtenerSiguienteToken()
                if(tokenActual.categoria ==Categoria.IDENTIFICADOR)
                {
                    var dato:Token=tokenActual
                    obtenerSiguienteToken()

                    if(tokenActual.categoria == Categoria.FIN_SENTENCIA)
                    {
                        obtenerSiguienteToken()
                        var cas:Castin=Castin(tipo,dato)
                        return  cas
                    }
                    else
                    {
                        reportarError("Falta el fin de sentencia # en castin")
                        return null
                    }
                }
                else{
                    reportarError("Falta el identificador del castin")
                    return null
                }
            }
            else{
                reportarError("Falta el tipo de dato en castin")
                return null
            }
        }
        return null
    }

    /**
     * <Decremento>
     */

    /**
     * Metodo que se encarga de verificar si una secuencia de tokens constituye un Decremento
     *<Decremento> ::= identificador Operador Decremento “#”
     */
    fun esDecremento():Decremento?
    {

        if(tokenActual.categoria == Categoria.IDENTIFICADOR )
        {
            var ident:Token=tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria== Categoria.DECREMENTO)
            {
                obtenerSiguienteToken()
                if(tokenActual.categoria== Categoria.FIN_SENTENCIA)
                {
                    obtenerSiguienteToken()
                    var decremento:Decremento=Decremento(ident)
                    return decremento
                }
                else{
                    reportarError("Falta terminal  en el Decremento")
                    return null
                }
            }
            else
            {
                reportarError("Falta el operador incremento en el Decremento")
                return null
            }
        }
        return null
    }

}
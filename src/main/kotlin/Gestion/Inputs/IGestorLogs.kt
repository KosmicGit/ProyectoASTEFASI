package es.cifpvirgen.Gestion.Inputs

import es.cifpvirgen.Data.Log

interface IGestorLogs {

    fun addLog(log: Log)

    fun obtenerLog(email: String): ArrayList<Log>?

    fun obtenerLogs(): ArrayList<Log>

    fun modificarEmailLog(correoOriginal: String, correoNuevo: String)

    fun modificarUsernameLog(usernameOriginal: String, usernameNuevo: String)

}
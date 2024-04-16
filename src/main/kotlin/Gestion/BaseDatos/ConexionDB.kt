package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Gestion.Encriptacion
import java.sql.Connection
import java.sql.DriverManager

class ConexionBD {
    companion object {
        var connection : Connection? = null
    }

    constructor() {
        val credenciales : ArrayList<String> = recogerRemoto()
        ConexionBD.connection = DriverManager.getConnection(credenciales[2], credenciales[1], credenciales[0])
    }

    fun recogerRemoto() : ArrayList<String> {
        val enc = Encriptacion("A3VapMg22jAlIvO1")
        val credenciales : ArrayList<String> = arrayListOf()
        enc.desencriptar("credencialesRemoto.dat").forEach {str ->
            credenciales.add(str)
        }
        return credenciales
    }

}
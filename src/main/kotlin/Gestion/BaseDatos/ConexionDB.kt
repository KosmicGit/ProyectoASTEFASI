package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Gestion.Gestores
import java.sql.Connection
import java.sql.DriverManager

class ConexionBD {
    companion object {
        var connection : Connection? = null
    }

    constructor() {
        val credenciales : ArrayList<String> = recogerRemoto()
        ConexionBD.connection = DriverManager.getConnection(credenciales[2], credenciales[0], credenciales[1])
    }

    private fun recogerRemoto() : ArrayList<String> {
        val credenciales : ArrayList<String> = arrayListOf()
        Gestores.encrypt.desencriptarBD("SqlSecret.dat").forEach { str ->
            credenciales.add(str)
        }
        return credenciales
    }

}
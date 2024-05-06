package com.astefasi.interfazastefasi.gestion.bbdd

import com.astefasi.interfazastefasi.gestion.inputs.Gestores
import java.sql.Connection
import java.sql.DriverManager

class ConexionDB {
    companion object {
        var connection : Connection? = null
    }

    constructor() {
        val credenciales : ArrayList<String> = recogerRemoto()
        connection = DriverManager.getConnection(credenciales[2], credenciales[0], credenciales[1])
    }

    private fun recogerRemoto() : ArrayList<String> {
        val credenciales : ArrayList<String> = arrayListOf()
        Gestores.encrypt.desencriptarBD("data/SqlSecret.dat").forEach {str ->
            credenciales.add(str)
        }
        return credenciales
    }
}
package com.astefasi.interfazastefasi.gestion.inputs

import com.astefasi.interfazastefasi.gestion.bbdd.GestionarLogs
import com.astefasi.interfazastefasi.gestion.bbdd.GestionarUsuarios
import io.github.cdimascio.dotenv.Dotenv
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Gestores {
    companion object {
        //Gestores
        val encrypt = Encriptacion(Dotenv.load().get("encryptKey"))
        val gestorUsuarios = GestionarUsuarios()
        val gestorLogs = GestionarLogs()

        fun fechaActual(): String {
            return (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
        }
    }
}
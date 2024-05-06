package com.astefasi.interfazastefasi.gestion.inputs

import io.github.cdimascio.dotenv.Dotenv


class Gestores {
    companion object {
        val encrypt = Encriptacion(Dotenv.load().get("encryptKey"))
    }
}
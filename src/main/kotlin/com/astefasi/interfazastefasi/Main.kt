package com.astefasi.interfazastefasi

import com.astefasi.interfazastefasi.gestion.ficheros.CacheFile
import com.astefasi.interfazastefasi.gestion.ficheros.ConfigFile
import com.astefasi.interfazastefasi.screen.carga.PantallaCarga
import com.astefasi.interfazastefasi.util.AjustesAplicacion
import com.astefasi.interfazastefasi.util.data.user.Cliente
import com.astefasi.interfazastefasi.util.data.user.Terapeuta
import com.astefasi.interfazastefasi.util.data.user.Usuario
import javafx.application.Application
import javafx.stage.Stage
import javafx.stage.StageStyle

class Main : Application() {

    companion object {
        lateinit var configuracion : ConfigFile
        lateinit var cache : CacheFile
        var usuario : Usuario? = null
        var cliente : Cliente? = null
        var terapeuta : Terapeuta? = null
    }

    override fun start(stage: Stage) {
        val carga = PantallaCarga()
        stage.title = "ASTEFASI"
        stage.scene = carga.escena
        stage.initStyle(StageStyle.TRANSPARENT)

        stage.show()
        carga.controlador!!.cargar()
        AjustesAplicacion.mainStage = stage
    }
}

fun main() {
    Application.launch(Main::class.java)
}
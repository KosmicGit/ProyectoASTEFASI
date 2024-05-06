package com.astefasi.interfazastefasi

import com.astefasi.interfazastefasi.screen.carga.PantallaCarga
import com.astefasi.interfazastefasi.util.AjustesAplicacion
import javafx.application.Application
import javafx.stage.Stage
import javafx.stage.StageStyle

class Main : Application() {

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
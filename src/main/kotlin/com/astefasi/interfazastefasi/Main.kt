package com.astefasi.interfazastefasi

import com.astefasi.interfazastefasi.screen.carga.PantallaCarga
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
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
    }
}

fun main() {
    Application.launch(Main::class.java)
}
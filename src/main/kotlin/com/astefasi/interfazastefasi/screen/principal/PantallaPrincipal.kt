package com.astefasi.interfazastefasi.screen.principal

import com.astefasi.interfazastefasi.Main
import com.astefasi.interfazastefasi.util.AjustesAplicacion
import javafx.fxml.FXMLLoader
import javafx.scene.Scene

class PantallaPrincipal {
    val controlador : PantallaPrincipalController
    val escena : Scene

    constructor() {
        val fxmlLoader = FXMLLoader(Main::class.java.getResource("main-view.fxml"))
        this.escena = Scene(fxmlLoader.load(), AjustesAplicacion.DIMENSIONES, AjustesAplicacion.DIMENSIONES)
        this.controlador = fxmlLoader.getController<PantallaPrincipalController>()
    }
}
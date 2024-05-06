package com.astefasi.interfazastefasi.screen.principal

import com.astefasi.interfazastefasi.Main
import com.astefasi.interfazastefasi.gestion.ficheros.ConfigFile
import com.astefasi.interfazastefasi.util.AjustesAplicacion
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.paint.Color

class PantallaPrincipal {
    val controlador : PantallaPrincipalController
    val escena : Scene

    constructor() {
        val fxmlLoader = FXMLLoader(Main::class.java.getResource("main-view.fxml"))
        this.escena = Scene(fxmlLoader.load(), ConfigFile.confUsuario.resolucion.anchura, ConfigFile.confUsuario.resolucion.altura)
        this.escena.fill = Color.TRANSPARENT
        this.controlador = fxmlLoader.getController<PantallaPrincipalController>()
    }
}
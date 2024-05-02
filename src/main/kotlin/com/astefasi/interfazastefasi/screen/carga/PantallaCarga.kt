package com.astefasi.interfazastefasi.screen.carga

import com.astefasi.interfazastefasi.Main
import com.astefasi.interfazastefasi.util.AjustesAplicacion
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.paint.Color

class PantallaCarga() {
    private val fxmlLoader : FXMLLoader = FXMLLoader(Main::class.java.getResource("loading-view.fxml"))

    var controlador : PantallaCargaController? = null
        get() {
            if (field == null) {
                field = this.fxmlLoader.getController<PantallaCargaController>()
            }
            return field
        }
    val escena : Scene = Scene(this.fxmlLoader.load(), 300.0, AjustesAplicacion.DIMENSIONES)

    init {
        this.escena.fill = Color.TRANSPARENT
    }
}
package com.astefasi.interfazastefasi.util

import javafx.scene.Scene
import javafx.stage.Stage
import java.awt.Dimension
import java.awt.Toolkit
class AjustesAplicacion {
    companion object {
        //Version de la aplicacion
        val VERSION = 0.33

        //Stage principal
        lateinit var mainStage : Stage

        //Pantalla usuario
        val tamanioPantalla : Dimension = Toolkit.getDefaultToolkit().screenSize

        //Resolucion Carga 1:1
        val DIMENSIONES : Double = 220.0

        fun cambiarEscena(escena : Scene) {
            mainStage.hide()
            mainStage.scene = escena
            mainStage.show()
        }
    }
}
package com.astefasi.interfazastefasi.util

import javafx.scene.Scene
import javafx.stage.Stage
import java.awt.Dimension
import java.awt.Toolkit
class AjustesAplicacion {
    companion object {
        //Stage principal
        lateinit var mainStage : Stage

        //Pantalla usuario
        val tamanioPantalla : Dimension = Toolkit.getDefaultToolkit().screenSize

        //Resolucion Principal Adaptada
        val ANCHURA_AD : Double = tamanioPantalla.getWidth() * 0.7
        val ALTURA_AD : Double = tamanioPantalla.getHeight() * 0.7

        //Resolucion Principal 16:9 x2
        val ANCHURA_X2 : Double = 1920.0
        val ALTURA_X2 : Double = 1080.0

        //Resolucion Principal 16:9 x1
        val ANCHURA_X1 : Double = 1080.0
        val ALTURA_X1 : Double = 720.0

        //Resolucion Carga 1:1
        val DIMENSIONES : Double = 220.0

        fun cambiarEscena(escena : Scene) {
            mainStage.hide()
            mainStage.scene = escena
            mainStage.show()
        }
    }
}
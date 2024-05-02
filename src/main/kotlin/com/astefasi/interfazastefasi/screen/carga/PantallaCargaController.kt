package com.astefasi.interfazastefasi.screen.carga

import javafx.animation.PauseTransition
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.util.Duration

class PantallaCargaController {

    @FXML
    lateinit var chargeBar : ProgressIndicator
    @FXML
    lateinit var chargeIndicator : Label

    fun cargar() {
        val progression = PauseTransition(Duration.seconds(0.1))
        progression.setOnFinished {
            if (chargeBar.progress < 1.0) {
                chargeBar.progress += 0.01

                when (chargeBar.progress) {
                    in 0.1..0.11 -> {
                        chargeIndicator.text = "Comprobando actualizacion..."
                    }
                    in 0.4..0.41 -> {
                        chargeIndicator.text = "Cargando BBDD..."
                    }
                    in 0.8..0.81 -> {
                        chargeIndicator.text = "Cargando Usuario..."
                    }
                }

                progression.playFromStart()
            }else {

            }
        }
        progression.play()
    }
}
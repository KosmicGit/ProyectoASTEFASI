package com.astefasi.interfazastefasi

import javafx.fxml.FXML
import javafx.scene.control.Label

class MainController {
    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
    }
}
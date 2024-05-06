package com.astefasi.interfazastefasi.screen.principal

import com.astefasi.interfazastefasi.animation.AnimationHandler
import com.astefasi.interfazastefasi.gestion.bbdd.ConexionDB
import com.astefasi.interfazastefasi.util.AjustesAplicacion
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import java.net.URL
import java.util.*

class PantallaPrincipalController : Initializable {

    //Secciones
    @FXML
    lateinit var loginPage : HBox
    @FXML
    lateinit var mainPage : HBox

    //Botones
    @FXML
    lateinit var exitButton : Button

    //Componentes
    @FXML
    lateinit var imageLogin1 : ImageView
    @FXML
    lateinit var imageLogin2 : ImageView

    override fun initialize(location : URL?, resources : ResourceBundle?) {
        imageLogin1.fitWidthProperty().bind(AjustesAplicacion.mainStage.widthProperty().multiply(0.9))
        imageLogin1.fitHeightProperty().bind(AjustesAplicacion.mainStage.heightProperty())
        imageLogin2.fitWidthProperty().bind(AjustesAplicacion.mainStage.widthProperty().multiply(0.9))
        imageLogin2.fitHeightProperty().bind(AjustesAplicacion.mainStage.heightProperty())

        AnimationHandler.imageTransition(imageLogin1, imageLogin2)
    }

    @FXML
    fun checkAccount() {
        AnimationHandler.fadeAnimation(loginPage, mainPage)
    }

    @FXML
    fun onExitButton() {
        ConexionDB.connection!!.close()
        Platform.exit()
    }

    @FXML
    fun onHideButton() {
        AjustesAplicacion.mainStage.isIconified = true
    }

    private var xOffset = 0.0
    private var yOffset = 0.0

    @FXML
    fun onMousePressed(event : MouseEvent) {
        xOffset = event.sceneX
        yOffset = event.sceneY
    }

    @FXML
    fun onMouseDragged(event : MouseEvent) {
        AjustesAplicacion.mainStage.x = event.screenX - xOffset
        AjustesAplicacion.mainStage.y = event.screenY - yOffset
    }

    @FXML
    fun onHoverExit() {
        exitButton.style = "-fx-background-color: red; -fx-background-radius: 0;"
    }

    @FXML
    fun onUnhoverExit() {
        exitButton.style = "-fx-background-color: transparent"
    }
}
package es.cifpvirgen.datmaker

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class DatMaker : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(DatMaker::class.java.getResource("datmaker-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 620.0, 240.0)
        stage.title = "DatMaker 1.0 | Confidential"

        stage.icons.add(Image(javaClass.getResourceAsStream("/es/cifpvirgen/datmaker/logo.png")))

        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(DatMaker::class.java)
}


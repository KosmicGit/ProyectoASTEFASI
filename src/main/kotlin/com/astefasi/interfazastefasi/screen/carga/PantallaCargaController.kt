package com.astefasi.interfazastefasi.screen.carga

import com.astefasi.interfazastefasi.Main
import com.astefasi.interfazastefasi.gestion.actualizador.UpdaterHandler
import com.astefasi.interfazastefasi.gestion.bbdd.ConexionDB
import com.astefasi.interfazastefasi.gestion.ficheros.CacheFile
import com.astefasi.interfazastefasi.gestion.ficheros.ConfigFile
import com.astefasi.interfazastefasi.screen.principal.PantallaPrincipal
import com.astefasi.interfazastefasi.util.AjustesAplicacion
import javafx.animation.PauseTransition
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.util.Duration
import java.io.IOException
import java.sql.SQLException

class PantallaCargaController {

    @FXML
    lateinit var chargeBar : ProgressIndicator
    @FXML
    lateinit var chargeIndicator : Label

    var error = false

    fun cargar() {
        val progression = PauseTransition(Duration.seconds(0.1))
        progression.setOnFinished {
            if (chargeBar.progress < 1.0 && !error) {
                chargeBar.progress += 0.01

                when (chargeBar.progress) {
                    //Comprueba en el repositorio en los releases si es la ultima version.
                    in 0.1..0.11 -> {
                        chargeIndicator.text = "Comprobando actualizacion..."
                    }
                    in 0.2..0.21 -> {
                        try {
                            val handler = UpdaterHandler()
                        }catch (_ : IOException) {}
                    }
                    //Intenta conectar con la base de datos.
                    in 0.40..0.41 -> {
                        chargeIndicator.text = "Cargando BBDD..."
                    }
                    in 0.50..0.51 -> {
                        try {
                            ConexionDB()
                        }catch (ex : SQLException) {
                            this.error = true
                        }
                    }
                    //Carga la configuracion y usuario en cuestion.
                    in 0.7..0.71 -> {
                        chargeIndicator.text = "Cargando Configuracion..."
                    }
                    in 0.8.. 0.81 -> {
                        Main.configuracion = ConfigFile()
                    }
                    in 0.85..0.86 -> {
                        chargeIndicator.text = "Cargando Usuario..."
                    }
                    in 0.9.. 0.91 -> {
                        Main.cache = CacheFile()
                    }
                }

                progression.playFromStart()
            }else if (error) {
                progression.stop()
                msgConnectionAlert()
            }else {
                val principal = PantallaPrincipal()
                AjustesAplicacion.cambiarEscena(principal.escena)
            }
        }
        progression.play()
    }

    private fun msgConnectionAlert() {
        var mensaje : Alert = Alert(Alert.AlertType.WARNING)
        mensaje.title = "Error al conectarse con la base de datos."
        mensaje.contentText = "Se ha producido un error al intentar conectarse con la base de datos, compruebe su conexion a internet y pruebe mas tarde."
        mensaje.setOnCloseRequest {
            Platform.exit()
        }
        mensaje.show()
    }
}
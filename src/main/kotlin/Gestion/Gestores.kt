package es.cifpvirgen.Gestion

import es.cifpvirgen.Gestion.BaseDatos.ConexionBD
import es.cifpvirgen.Gestion.BaseDatos.GestionarLogs
import es.cifpvirgen.Gestion.BaseDatos.GestionarUsuarios
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Gestores {

    companion object {
        val conexion = ConexionBD()
        val gestorUsuarios = GestionarUsuarios()
        val gestorLogs = GestionarLogs()

        fun fechaActual(): String {
            return (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
        }
    }

}
package es.cifpvirgen.Gestion

import com.google.gson.Gson
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.BaseDatos.ConexionBD
import es.cifpvirgen.Gestion.BaseDatos.GestionarLogs
import es.cifpvirgen.Gestion.BaseDatos.GestionarUsuarios
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Gestores {

    companion object {
        //Gestores
        val encript = Encriptacion("A3VapMg22jAlIvO1")
        val conexion = ConexionBD()
        val gestorUsuarios = GestionarUsuarios()
        val gestorLogs = GestionarLogs()

        fun fechaActual(): String {
            return (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
        }

        fun encriptarUsuario(usuario: Usuario): String {
            return encript.encriptar(Gson().toJson(usuario))
        }

        fun desencriptarUsuario(usuarioEncriptado: String): Usuario {
            return Gson().fromJson(encript.desencriptar(usuarioEncriptado), Usuario::class.java)
        }

    }

}
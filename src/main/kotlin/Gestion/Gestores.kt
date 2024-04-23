package es.cifpvirgen.Gestion

import com.google.gson.Gson
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.BaseDatos.ConexionBD
import es.cifpvirgen.Gestion.BaseDatos.GestionarLogs
import es.cifpvirgen.Gestion.BaseDatos.GestionarUsuarios
import es.cifpvirgen.Gestion.Email.ConexionMail
import es.cifpvirgen.Gestion.Email.GestionarEmail
import io.github.cdimascio.dotenv.dotenv
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Base64

class Gestores {

    companion object {
        //Gestores
        val encrypt = Encriptacion(dotenv()["encryptKey"])
        val conexionMail = ConexionMail()
        val conexionBD = ConexionBD()
        val gestorUsuarios = GestionarUsuarios()
        val gestorLogs = GestionarLogs()
        val gestorMail = GestionarEmail()

        fun fechaActual(): String {
            return (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
        }

        fun encriptarUsuario(usuario: Usuario): String {
            return encrypt.encriptar(Gson().toJson(usuario))
        }

        fun desencriptarUsuario(usuarioEncriptado: String): Usuario {
            return Gson().fromJson(encrypt.desencriptar(usuarioEncriptado), Usuario::class.java)
        }

        fun codificarURL(url: String): String {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
        }

        fun decodificarURL(url: String): String {
            return URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
        }

        fun cadenaValida(cadena: String): Boolean {
            return try {
                val decodedBytes = Base64.getDecoder().decode(cadena)
                val decodedString = String(decodedBytes)
                decodedString == cadena
            } catch (e: IllegalArgumentException) {
                println(e)
                false
            }
        }

    }

}
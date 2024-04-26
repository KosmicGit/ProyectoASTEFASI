package es.cifpvirgen.Gestion

import com.google.gson.Gson
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.BaseDatos.ConexionBD
import es.cifpvirgen.Gestion.BaseDatos.GestionarLogs
import es.cifpvirgen.Gestion.BaseDatos.GestionarPacientes
import es.cifpvirgen.Gestion.BaseDatos.GestionarUsuarios
import es.cifpvirgen.Gestion.Email.ConexionMail
import es.cifpvirgen.Gestion.Email.GestionarEmail
import io.github.cdimascio.dotenv.dotenv
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Gestores {

    companion object {
        //Encriptado
        val encrypt = Encriptacion(dotenv()["encryptKey"])

        //Conexiones
        val conexionMail = ConexionMail()
        val conexionBD = ConexionBD()

        //Gestores
        val gestorUsuarios = GestionarUsuarios()
        val gestorPacientes = GestionarPacientes()
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

        fun parsearFecha(fecha: Int): LocalDate {
            return (LocalDate.parse(fecha.toString(), DateTimeFormatter.ofPattern("ddMMyyyy")))
        }

        fun parsearFecha(fecha: String): LocalDate {
            return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }

        fun formatearFecha(fecha: LocalDate): Int {
            return fecha.format(DateTimeFormatter.ofPattern("ddMMyyyy")).toInt()
        }

        fun stringFecha(fecha: LocalDate): String {
            return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }

}
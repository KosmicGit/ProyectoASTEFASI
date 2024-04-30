package es.cifpvirgen.Gestion.Email

import es.cifpvirgen.Gestion.Inputs.Gestores
import javax.mail.*
import java.util.*
import kotlin.collections.ArrayList

class ConexionMail {

    companion object {
        var conexion : Session? = null
        var userAddress = ""
    }

    constructor() {
        val credenciales : ArrayList<String> = recogerRemoto()
        userAddress = credenciales[2]
        conexion = obtenerSesion(credenciales)
    }

    private fun obtenerSesion(credenciales: ArrayList<String>): Session {
        val properties = Properties()
        properties["mail.smtp.host"] = credenciales[0]
        properties["mail.smtp.port"] = credenciales[1]
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"

        return Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(credenciales[2], credenciales[3])
            }
        })
    }

    private fun recogerRemoto() : ArrayList<String> {
        val credenciales : ArrayList<String> = arrayListOf()
        Gestores.encrypt.desencriptarBD("MailSecret.dat").forEach { str ->
            credenciales.add(str)
        }
        return credenciales
    }
}
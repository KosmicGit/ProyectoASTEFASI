package es.cifpvirgen.Gestion.Email

import es.cifpvirgen.Gestion.Inputs.DebugColors
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class GestionarEmail {

    fun enviarCorreo(destinatario: String, asunto: String, contenido: String) {
        val session = ConexionMail.conexion
        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(ConexionMail.userAddress))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(destinatario))
            message.subject = asunto

            message.setContent(contenido, "text/html; charset=utf-8")

            Transport.send(message)
            println(DebugColors.ok() + " Correo electrónico enviado correctamente.")
        } catch (e: MessagingException) {
            println(DebugColors.error() + " Error al enviar el correo electrónico: ${e.message}")
        }
    }
}
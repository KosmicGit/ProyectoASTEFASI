package es.cifpvirgen.Gestion.Inputs

import java.time.LocalDate

class InputsUsuario {

    companion object {

        fun comprobarEmail(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
            return email.matches(Regex(emailRegex))
        }

        fun comprobarDNI(dni: String): Boolean {
            val dniRegex = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]\$"
            if (!dni.matches(Regex(dniRegex))) {
                return false
            }

            val numeroDNI = dni.substring(0, 8).toInt()
            val letraDNI = dni[8]
            val letrasDNI = "TRWAGMYFPDXBNJZSQVHLCKE"

            val letraCalculada = letrasDNI[(numeroDNI % 23)]

            return letraDNI == letraCalculada
        }

        fun comprobarFecha(fechaNacimiento: LocalDate): Boolean {
            val edadMinima = 18
            val edadMaxima = 120
            val fechaActual = LocalDate.now()
            val edad = fechaActual.year - fechaNacimiento.year
            if (edad in edadMinima until edadMaxima) {
                return fechaNacimiento.plusYears(edadMinima.toLong()).isBefore(fechaActual)
            }
            return false
        }
    }
}
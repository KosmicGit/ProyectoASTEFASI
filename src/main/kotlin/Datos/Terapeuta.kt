package es.cifpvirgen.Datos

import java.time.LocalDate

data class Terapeuta(var id: Int, var nombre: String, var apellidos: String, var fecha_nacimiento: LocalDate, var idUsuario: Int) {

    fun copy(): Terapeuta {
        return Terapeuta(this.id, this.nombre, this.apellidos, this.fecha_nacimiento, this.idUsuario)
    }
}
package es.cifpvirgen.Datos

import es.cifpvirgen.Data.Roles
import java.time.LocalDate

data class Paciente(var dni: String, var nombre: String, var apellido: String, var fecha_nacimiento:LocalDate, var idUsuario: Int) {

    fun copy(): Paciente {
        return Paciente(this.dni, this.nombre, this.apellido, this.fecha_nacimiento, this.idUsuario)
    }
}

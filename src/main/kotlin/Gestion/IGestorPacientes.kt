package es.cifpvirgen.Gestion

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Datos.Paciente

interface IGestorPacientes {

    fun addPaciente(paciente: Paciente)

    fun borrarPaciente(paciente: Paciente)

    fun obtenerPaciente(dni: String): Paciente?

    fun obtenerPacienteIdUsuario(idUsuario: Int): Paciente?

    fun modificarPaciente(pacienteOriginal: Paciente, datosNuevos: Paciente)

    fun obtenerPacientes(): ArrayList<Paciente>
}
package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Data.Log
import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Datos.Paciente
import es.cifpvirgen.Gestion.DebugColors
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.IGestorPacientes
import java.sql.SQLException

class GestionarPacientes: IGestorPacientes {

    override fun addPaciente(paciente: Paciente) {
        val query = "INSERT INTO Cliente (DNI, NOMBRE, APELLIDO, FECHA_NACIMIENTO, ID_USUARIO) VALUES (?, ?, ?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setString(1, paciente.dni)
            statement.setString(2, paciente.nombre)
            statement.setString(3, paciente.apellido)
            statement.setInt(4, Gestores.formatearFecha(paciente.fecha_nacimiento))
            statement.setInt(5, paciente.idUsuario)

            val usuario = Gestores.gestorUsuarios.obtenerUsuarioId(paciente.idUsuario)
            if (usuario != null) {
                val logAdd = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Ficha Paciente creada")
                Gestores.gestorLogs.addLog(logAdd)
            }

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            if (e.errorCode == 1062) {
                println(DebugColors.error() + " El paciente ya existe.")
                println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
            } else {
                println(DebugColors.error() + " Error al añadir al paciente:")
                println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        }
    }

    override fun borrarPaciente(paciente: Paciente) {
        val user = obtenerPaciente(paciente.dni)
        if (user != null) {
            val query = "DELETE FROM Cliente WHERE DNI = ?"

            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, paciente.dni)

                val usuario = Gestores.gestorUsuarios.obtenerUsuarioId(paciente.idUsuario)
                if (usuario != null) {
                    val logEliminar = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Ficha Paciente eliminado")
                    Gestores.gestorLogs.addLog(logEliminar)
                }

                statement.executeUpdate()
                statement.close()
            } catch (e: SQLException) {
                println(DebugColors.error() + " Error al " + DebugColors.rojo("eliminar") + " el usuario:")
                println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        } else {
            println(DebugColors.error() + " El usuario no existe.")
        }
    }

    override fun obtenerPaciente(dni: String): Paciente? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM Cliente WHERE DNI = ?")
        statement.setString(1, dni)
        val resultSet = statement.executeQuery()

        var paciente: Paciente? = null

        try {
            if (resultSet.next()) {
                val dni = resultSet.getString("DNI")
                val nombre = resultSet.getString("NOMBRE")
                val apellido = resultSet.getString("APELLIDO")
                val fecha_nacimiento = Gestores.parsearFecha(resultSet.getInt("FECHA_NACIMIENTO"))
                val idUsuario = resultSet.getInt("ID_USUARIO")

                paciente = Paciente(dni, nombre, apellido, fecha_nacimiento, idUsuario)
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos del paciente:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return paciente
    }

    override fun obtenerPacienteIdUsuario(idUsuario: Int): Paciente? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM Cliente WHERE ID_USUARIO = ?")
        statement.setInt(1, idUsuario)
        val resultSet = statement.executeQuery()

        var paciente: Paciente? = null

        try {
            if (resultSet.next()) {
                val dni = resultSet.getString("DNI")
                val nombre = resultSet.getString("NOMBRE")
                val apellido = resultSet.getString("APELLIDO")
                val fecha_nacimiento = Gestores.parsearFecha(resultSet.getInt("FECHA_NACIMIENTO"))

                paciente = Paciente(dni, nombre, apellido, fecha_nacimiento, idUsuario)
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos del paciente:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return paciente
    }

    override fun modificarPermisos(paciente: Paciente, rol: Roles) {
        TODO("Not yet implemented")
    }

    override fun modificarPaciente(pacienteOriginal: Paciente, datosNuevos: Paciente) {
        TODO("Not yet implemented")
    }


    override fun obtenerPacientes(): ArrayList<Paciente> {
        TODO("Not yet implemented")
    }

}
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

    override fun modificarPaciente(pacienteOriginal: Paciente, datosNuevos: Paciente) {
        val paciente = obtenerPaciente(pacienteOriginal.dni)
        if (paciente != null) {
            val query = "UPDATE Cliente SET DNI = ?, NOMBRE = ?, APELLIDO = ?, FECHA_NACIMIENTO = ? WHERE DNI = ?"
            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, datosNuevos.dni)
                statement.setString(2, datosNuevos.nombre)
                statement.setString(3, datosNuevos.apellido)
                statement.setInt(4, Gestores.formatearFecha(datosNuevos.fecha_nacimiento))
                statement.setString(5, pacienteOriginal.dni)

                var cambios = ""
                if (datosNuevos.dni != pacienteOriginal.dni) {
                    cambios = "DNI: ${pacienteOriginal.dni} -> ${datosNuevos.dni}"
                }
                if (datosNuevos.nombre != pacienteOriginal.nombre) {
                    cambios = "Nombre: ${pacienteOriginal.nombre} -> ${datosNuevos.nombre}"
                }
                if (datosNuevos.apellido != pacienteOriginal.apellido) {
                    cambios = "Apellido: ${pacienteOriginal.apellido} -> ${datosNuevos.apellido}"
                }
                if (datosNuevos.fecha_nacimiento != pacienteOriginal.fecha_nacimiento) {
                    cambios = "Fecha_Nacimiento: ${pacienteOriginal.fecha_nacimiento} -> ${datosNuevos.fecha_nacimiento}"
                }

                val usuario = Gestores.gestorUsuarios.obtenerUsuarioId(paciente.idUsuario)!!
                val logModificacion = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Se ha modificado el campo $cambios")
                Gestores.gestorLogs.addLog(logModificacion)

                statement.executeUpdate()
                statement.close()
                println(DebugColors.ok() + " Usuario " + DebugColors.magenta(usuario.username) + " modificado con éxito " + DebugColors.cian(cambios))
            } catch (e: SQLException) {
                println(DebugColors.error() + " Error al modificar el usuario:")
                println(DebugColors.amarillo("[${e.errorCode}]") + "${e.message}")
            }
        } else {
            println(DebugColors.error() + " El paciente no existe.")
        }
    }

    override fun obtenerPacientes(): ArrayList<Paciente> {
        TODO("Not yet implemented")
    }

}
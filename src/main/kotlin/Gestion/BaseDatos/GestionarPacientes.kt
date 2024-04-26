package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Data.Log
import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Datos.Paciente
import es.cifpvirgen.Gestion.DebugColors
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.IGestorPacientes
import java.sql.SQLException

class GestionarPacientes: IGestorPacientes {

    override fun addPaciente(paciente: Paciente) {
        val query = "INSERT INTO cliente (DNI, NOMBRE, APELLIDO, FECHA_NACIMIENTO, ID_ROL, ID_USUARIO) VALUES (?, ?, ?, ?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setString(1, paciente.dni)
            statement.setString(2, paciente.nombre)
            statement.setString(3, paciente.apellido)
            statement.setInt(4, Gestores.formatearFecha(paciente.fecha_nacimiento))
            when (paciente.rol) {
                Roles.TERAPEUTA -> {
                    statement.setInt(5,1)
                }
                Roles.ADMINISTRADOR -> {
                    statement.setInt(5, 2)
                }
                else -> {
                    statement.setInt(5, 0)
                }
            }
            statement.setInt(6, paciente.idUsuario)

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
        TODO("Not yet implemented")
    }

    override fun obtenerPaciente(dni: String): Paciente? {
        //TODO("Not yet implemented")
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM cliente WHERE DNI = ?")
        statement.setString(1, dni)
        val resultSet = statement.executeQuery()

        var paciente: Paciente? = null

        try {
            if (resultSet.next()) {
                val idUsuario = resultSet.getInt("idUsuario")
                val username = resultSet.getString("username")
                val email = resultSet.getString("email")
                val password = Gestores.encrypt.desencriptar(resultSet.getString("password"))
                var rol: Roles
                if (resultSet.getInt("rol") == 1) {
                    rol = Roles.TERAPEUTA
                } else if (resultSet.getInt("rol") == 2){
                    rol = Roles.ADMINISTRADOR
                } else {
                    rol = Roles.PACIENTE
                }
                var verificado = false
                if (resultSet.getInt("verificado") == 1) {
                    verificado = true
                }

                usuario = Usuario(idUsuario, username, email, password, rol, verificado)
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos del usuario:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return usuario
    }

    override fun obtenerPacienteIdUsuario(idUsuario: Int): Paciente? {
        TODO("Not yet implemented")
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
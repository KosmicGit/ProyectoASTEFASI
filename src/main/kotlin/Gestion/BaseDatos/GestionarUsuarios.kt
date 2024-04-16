package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Data.Log
import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.DebugColors
import es.cifpvirgen.Gestion.Gestores
import java.sql.SQLException


class GestionarUsuarios {
    /**
     * Añade un nuevo usuario a la base de datos.
     *
     * @param usuario El objeto Usuario que se va a añadir a la base de datos.
     */
    fun addUsuario(usuario : Usuario) {
        val query = "INSERT INTO Usuario (username, email, password, rol) VALUES (?, ?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setString(1, usuario.username)
            statement.setString(2, usuario.email)
            statement.setString(3, usuario.password)
            when (usuario.rol) {
                Roles.TERAPEUTA -> {
                    statement.setInt(4,1)
                }
                Roles.ADMINISTRADOR -> {
                    statement.setInt(4, 2)
                }
                else -> {
                    statement.setInt(4, 0)
                }
            }
            val logRegistro = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Usuario creado")
            Gestores.gestorLogs.addLog(logRegistro)

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            if (e.errorCode == 1062) {
                println(DebugColors.error() + " El usuario ya existe.")
            } else {
                println(DebugColors.error() + " Error al añadir al usuario:")
                println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param usuario El objeto Usuario que se va a eliminar de la base de datos.
     */
    fun borrarUsuario(usuario: Usuario) {
        val user = obtenerUsuario(usuario.email)
        if (user != null) {
            val query = "DELETE FROM Usuario WHERE email = ?"

            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, usuario.email)

                val logEliminar = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Usuario eliminado")
                Gestores.gestorLogs.addLog(logEliminar)

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

    /**
     * Obtiene un usuario de la base de datos según su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario que se desea obtener.
     * @return El objeto Usuario correspondiente al correo electrónico proporcionado, o null si no se encuentra en la base de datos.
     */
    fun obtenerUsuario(email : String) : Usuario? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM Usuario WHERE email = ?")
        statement.setString(1, email)
        val resultSet = statement.executeQuery()

        var usuario: Usuario? = null

        try {
            if (resultSet.next()) {
                val idUsuario = resultSet.getInt("idUsuario")
                val username = resultSet.getString("username")
                val email = resultSet.getString("email")
                val password = resultSet.getString("password")
                var rol: Roles
                if (resultSet.getInt("rol") == 1) {
                    rol = Roles.TERAPEUTA
                } else if (resultSet.getInt("rol") == 2){
                    rol = Roles.ADMINISTRADOR
                } else {
                    rol = Roles.PACIENTE
                }

                usuario = Usuario(idUsuario, username, email, password, rol)
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

    /**
     * Modifica los permisos de un usuario en la base de datos.
     *
     * @param usuario El objeto Usuario cuyos permisos se van a modificar.
     * @param rol El nuevo rol que se asignará al usuario.
     */
    fun modificarPermisos(usuario : Usuario, rol : Roles) {
        val user = obtenerUsuario(usuario.email)
        if (user != null) {
            val query = "UPDATE Usuario SET rol = ? WHERE email = ?"

            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                when (rol) {
                    Roles.ADMINISTRADOR -> {
                        statement.setInt(1, 2)
                    }
                    Roles.TERAPEUTA -> {
                        statement.setInt(1,1)
                    }
                    else -> {
                        statement.setInt(1, 0)
                    }
                }
                statement.setString(2, usuario.email)

                val logPermiso = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Permisos del usuario modificados.")
                Gestores.gestorLogs.addLog(logPermiso)

                statement.executeUpdate()
                statement.close()
                println(DebugColors.ok() + " Permisos modificados con éxito")
            } catch (e: SQLException) {
                println(DebugColors.error() + " Error al cambiar el permiso del usuario:")
                println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        } else {
            println(DebugColors.error() + " El usuario no existe.")
        }
    }

    /**
     * Modifica los datos de un usuario en la base de datos.
     *
     * @param usuarioOriginal El objeto Usuario cuyos datos se van a modificar.
     * @param datosNuevos El objeto Usuario con los nuevos datos que se van a asignar al usuario.
     */
    fun modificarUsuario(usuarioOriginal: Usuario, datosNuevos: Usuario) {
        val user = obtenerUsuario(usuarioOriginal.email)
        if (user != null) {
            val query = "UPDATE Usuario SET username = ?, email = ?, password = ? WHERE email = ?"
            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, datosNuevos.username)
                statement.setString(2, datosNuevos.email)
                statement.setString(3, datosNuevos.password)
                statement.setString(4, usuarioOriginal.email)

                var cambios = ""
                if (datosNuevos.username != usuarioOriginal.username) {
                    cambios = "Username: ${usuarioOriginal.username} -> ${datosNuevos.username}"
                }
                if (datosNuevos.email != usuarioOriginal.email) {
                    cambios = "Email: ${usuarioOriginal.email} -> ${datosNuevos.email}"
                }
                if (datosNuevos.password != usuarioOriginal.password) {
                    cambios = "Contraseña"
                }

                val logModificacion = Log(datosNuevos.username, datosNuevos.email, Gestores.fechaActual(), "Se ha modificado el campo $cambios")
                Gestores.gestorLogs.addLog(logModificacion)

                statement.executeUpdate()
                statement.close()
                println(DebugColors.ok() + " Usuario " + DebugColors.magenta(datosNuevos.username) + " modificado con éxito")
            } catch (e: SQLException) {
                println(DebugColors.error() + " Error al modificar el usuario:")
                println(DebugColors.amarillo("[${e.errorCode}]") + "${e.message}")
            }
        } else {
            println(DebugColors.error() + " El usuario no existe.")
        }
    }

    /**
     * Obtiene todos los usuarios almacenados en la base de datos.
     *
     * @return ArrayList de objetos Usuario que representan a todos los usuarios almacenados en la base de datos.
     */
    fun obtenerUsuarios(): ArrayList<Usuario> {
        val listaUsuarios = ArrayList<Usuario>()

        val query = "SELECT * FROM Usuario"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        val resultSet = statement.executeQuery()

        try {
            while (resultSet.next()) {
                val email = resultSet.getString("email")
                val usuario = obtenerUsuario(email)
                if (usuario != null) {
                    listaUsuarios.add(usuario)
                }
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos de los usuarios:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return listaUsuarios
    }
}

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
     * @return ultimoID El último ID de la base de Datos, en caso de haber un error, devuelve nulo
     */
    fun obtenerUltimoID(): Int? {
        val query = "SELECT MAX(idUsuario) FROM Usuario"
        var ultimoID: Int? = null

        val statement = ConexionBD.connection!!.prepareStatement(query)
        val resultSet = statement.executeQuery()

        try {
            if (resultSet.next()) {
                ultimoID = resultSet.getInt(1)
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos de los usuarios:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }
        return ultimoID
    }

    fun verificarUsuario(usuario: Usuario) {
        val query = "UPDATE Usuario SET verificado = ? WHERE email = ?"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setInt(1, 1)
            statement.setString(2, usuario.email)

            val logPermiso = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Usuario Verificado.")
            Gestores.gestorLogs.addLog(logPermiso)

            statement.executeUpdate()
            statement.close()
            println(DebugColors.ok() + " Usuario verificado con éxito")
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al cambiar el permiso del usuario:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        }
    }

    fun estaVerificado(usuario: Usuario): Boolean {
        val query = "SELECT verificado FROM Usuario WHERE email = ?"
        var verificado = false

        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, usuario.email)
        val resultSet = statement.executeQuery()

        try {
            if (resultSet.next()) {
                if (resultSet.getInt("verificado") == 1) {
                    verificado = true
                }
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener el estado de verificación del usuario:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }
        return verificado
    }
    /**
     * Añade un nuevo usuario a la base de datos.
     *
     * @param usuario El objeto Usuario que se va a añadir a la base de datos.
     */
    fun addUsuario(usuario : Usuario) {
        val query = "INSERT INTO Usuario (idUsuario, username, email, password, rol, verificado) VALUES (?, ?, ?, ?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setInt(1, usuario.idUsuario)
            statement.setString(2, usuario.username)
            statement.setString(3, usuario.email)
            statement.setString(4, Gestores.encript.encriptar(usuario.password))
            when (usuario.rol) {
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
            statement.setBoolean(6, usuario.verificado)

            val logRegistro = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Usuario creado")
            Gestores.gestorLogs.addLog(logRegistro)

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            if (e.errorCode == 1062) {
                println(DebugColors.error() + " El usuario ya existe.")
                println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
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
     * @param username El UserName del usuario que se desea obtener.
     * @return El objeto Usuario correspondiente al correo electrónico proporcionado, o null si no se encuentra en la base de datos.
     */
    fun obtenerUsuario(username : String) : Usuario? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM Usuario WHERE username = ?")
        statement.setString(1, username)
        val resultSet = statement.executeQuery()

        var usuario: Usuario? = null

        try {
            if (resultSet.next()) {
                val idUsuario = resultSet.getInt("idUsuario")
                val username = resultSet.getString("username")
                val email = resultSet.getString("email")
                val password = Gestores.encript.desencriptar(resultSet.getString("password"))
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
                statement.setString(3, Gestores.encript.encriptar(datosNuevos.password))
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
                val username = resultSet.getString("username")
                val usuario = obtenerUsuario(username)
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

package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Data.Log
import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.DebugColors
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.Gestores.Companion.conexionBD
import es.cifpvirgen.Gestion.IGestorUsuarios
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement


class GestionarUsuarios: IGestorUsuarios {
    /**
     * Añade un nuevo usuario a la base de datos.
     *
     * @return ultimoID El último ID de la base de Datos, en caso de haber un error, devuelve nulo
     */
    override fun obtenerUltimoID(): Int? {
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

    override fun verificarUsuario(usuario: Usuario) {
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

    override fun estaVerificado(usuario: Usuario): Boolean {
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
    override fun addUsuario(usuario : Usuario) {
        val query = "INSERT INTO Usuario (idUsuario, username, email, password, rol, verificado) VALUES (?, ?, ?, ?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setInt(1, usuario.idUsuario)
            statement.setString(2, usuario.username)
            statement.setString(3, usuario.email)
            statement.setString(4, Gestores.encrypt.encriptar(usuario.password))
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
    override fun borrarUsuario(usuario: Usuario) {
        val user = obtenerUsuarioMail(usuario.email)
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
     * @return El objeto Usuario correspondiente al Username proporcionado, o null si no se encuentra en la base de datos.
     */
    override fun obtenerUsuario(username : String) : Usuario? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM Usuario WHERE username = ?")
        statement.setString(1, username)
        val resultSet = statement.executeQuery()

        var usuario: Usuario? = null

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

    /**
     * Obtiene un usuario de la base de datos según su dirección de correo electrónico.
     *
     * @param email El Correo del usuario que se desea obtener.
     * @return El objeto Usuario correspondiente al correo electrónico proporcionado, o null si no se encuentra en la base de datos.
     */
    override fun obtenerUsuarioMail(email : String) : Usuario? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM Usuario WHERE email = ?")
        statement.setString(1, email)
        val resultSet = statement.executeQuery()

        var usuario: Usuario? = null

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

    override fun guardarFoto(imgB64: String, usuario: Usuario) {
        val query = "UPDATE Usuario SET foto_perfil = ? WHERE username = ?"

        try {
            val connection = ConexionBD.connection ?: throw SQLException("No hay conexión a la base de datos.")
            val statement = connection.prepareStatement(query)
            statement.setString(1, imgB64)
            statement.setString(2, usuario.username)

            statement.executeUpdate()
            statement.close()

            val logRegistro = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Foto de perfil actualizada.")
            Gestores.gestorLogs.addLog(logRegistro)
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al actualizar la foto de perfil del usuario:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        }
    }

    override fun obtenerFoto(usuario: Usuario): String? {
        var fotoBase64: String? = null

        try {
            val statement = ConexionBD.connection!!.prepareStatement("SELECT foto_perfil FROM Usuario WHERE email = ?")
            statement.setString(1, usuario.email)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                fotoBase64 = resultSet.getString("foto_perfil")
                if (resultSet.wasNull()) {
                    fotoBase64 = null
                }
            }

            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener la foto del usuario:")
            println(DebugColors.amarillo("[${e.errorCode}]") + "${e.message}")
        }
        return fotoBase64
    }

    /**
     * Modifica los permisos de un usuario en la base de datos.
     *
     * @param usuario El objeto Usuario cuyos permisos se van a modificar.
     * @param rol El nuevo rol que se asignará al usuario.
     */
    override fun modificarPermisos(usuario : Usuario, rol : Roles) {
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
    override fun modificarUsuario(usuarioOriginal: Usuario, datosNuevos: Usuario) {
        val user = obtenerUsuarioMail(usuarioOriginal.email)
        if (user != null) {
            val query = "UPDATE Usuario SET username = ?, email = ?, password = ? WHERE email = ?"
            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, datosNuevos.username)
                statement.setString(2, datosNuevos.email)
                statement.setString(3, Gestores.encrypt.encriptar(datosNuevos.password))
                statement.setString(4, usuarioOriginal.email)

                var cambios = ""
                if (datosNuevos.username != usuarioOriginal.username) {
                    cambios = "Username: ${usuarioOriginal.username} -> ${datosNuevos.username}"
                }
                if (datosNuevos.email != usuarioOriginal.email) {
                    cambios = "Email: ${usuarioOriginal.email} -> ${datosNuevos.email}"
                    Gestores.gestorLogs.modificarEmailLog(usuarioOriginal.email, datosNuevos.email)
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
    override fun obtenerUsuarios(): ArrayList<Usuario> {
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

    override fun comprobarCredenciales(correo: String, pass: String): Usuario? {
        val query = "SELECT * FROM 'USUARIO' WHERE correo = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, correo)
        var rs = statement.executeQuery()!!
        var usuario : Usuario? = null
        try {
            if (rs.next()) {
                if (pass == Gestores.encrypt.desencriptar(rs.getString("CLAVE_ACCESO"))) {
                    val idUsuario = rs.getInt("ID_USUARIO")
                    val username = rs.getString("NOMBRE_USUARIO")
                    val email = rs.getString("EMAIL")
                    val password = Gestores.encrypt.desencriptar(rs.getString("CLAVE_ACCESO"))
                    var rol: Roles
                    if (rs.getInt("ROL") == 1) {
                        rol = Roles.TERAPEUTA
                    } else if (rs.getInt("ROL") == 2){
                        rol = Roles.ADMINISTRADOR
                    } else {
                        rol = Roles.PACIENTE
                    }
                    var verificado = false
                    if (rs.getInt("VERIFICADO") == 1) {
                        verificado = true
                    }

                    usuario = Usuario(idUsuario, username, email, password, rol, verificado)
                }
            }
        } catch (_: Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return usuario
    }

}


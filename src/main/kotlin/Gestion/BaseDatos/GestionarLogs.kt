package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Data.Log
import es.cifpvirgen.Gestion.DebugColors
import es.cifpvirgen.Gestion.IGestorLogs
import java.sql.SQLException

class GestionarLogs: IGestorLogs {

    /**
     * Añade un nuevo registro a la base de datos.
     *
     * @param log El objeto Log que se va a añadir a la base de datos.
     */
    override fun addLog(log: Log) {
        val query = "INSERT INTO Log (username, email, fecha, accion) VALUES (?, ?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setString(1, log.username)
            statement.setString(2, log.email)
            statement.setString(3, log.fecha)
            statement.setString(4, log.accion)

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al añadir el registro:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        }
    }

    /**
     * Obtiene los logs de un usuario de la base de datos según su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario que se desea obtener.
     * @return Devuelve un Array con los logs en caso de encontrarlo o null si no se encuentra en la Basd de Datos.
     */
    override fun obtenerLog(email: String): ArrayList<Log>? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM Log WHERE email = ?")
        statement.setString(1, email)
        val resultSet = statement.executeQuery()

        val logsEncontrados = ArrayList<Log>()

        try {
            while (resultSet.next()) {
                val usernameLog = resultSet.getString("username")
                val emailLog = resultSet.getString("email")
                val fechaLog = resultSet.getString("fecha")
                val accionLog = resultSet.getString("accion")

                val log = Log(usernameLog,emailLog, fechaLog, accionLog)
                logsEncontrados.add(log)
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos del registro:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return if (logsEncontrados.isNotEmpty()) {
            logsEncontrados
        } else {
            null
        }
    }

    /**
     * Obtiene todos los registros almacenados en la base de datos.
     *
     * @return ArrayList de objetos Log que representan a todos los registros almacenados en la base de datos.
     */
    override fun obtenerLogs(): ArrayList<Log> {
        val listaLogs = ArrayList<Log>()

        val query = "SELECT * FROM Log"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        val resultSet = statement.executeQuery()

        try {
            while (resultSet.next()) {
                val username = resultSet.getString("username")
                val email = resultSet.getString("email")
                val fecha = resultSet.getString("fecha")
                val accion = resultSet.getString("accion")

                val log = Log(username, email, fecha, accion)
                listaLogs.add(log)
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos de los registros:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return listaLogs
    }

    /**
     * Modifica los registros con el nuevo correo del Usuario.
     *
     * @param correoOriginal Registro a modificar.
     * @param correoNuevo Datos nuevos para el Registro a modificar.
     */
    override fun modificarEmailLog(correoOriginal: String, correoNuevo: String) {
        val query = "UPDATE Log SET email = ? WHERE email = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, correoNuevo)
        statement.setString(2, correoOriginal)

        try {
            val rowsAffected = statement.executeUpdate()
            println(DebugColors.info() + " Se han modificado $rowsAffected registros.")
            println(DebugColors.ok() + " Logs modificados correctamente.")
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al modificar los registros de logs:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            statement.close()
        }
    }

    /**
     * Modifica los registros con el nuevo username del Usuario.
     *
     * @param usernameOriginal Registro a modificar.
     * @param usernameNuevo Datos nuevos para el Registro a modificar.
     */
    override fun modificarUsernameLog(usernameOriginal: String, usernameNuevo: String) {
        val query = "UPDATE Log SET username = ? WHERE username = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, usernameNuevo)
        statement.setString(2, usernameOriginal)

        try {
            val rowsAffected = statement.executeUpdate()
            println(DebugColors.info() + " Se han modificado $rowsAffected registros.")
            println(DebugColors.ok() + " Logs modificados correctamente.")
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al modificar los registros de logs:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            statement.close()
        }
    }
}
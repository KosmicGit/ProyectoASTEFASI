package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Data.Log
import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Datos.Paciente
import es.cifpvirgen.Datos.Terapeuta
import es.cifpvirgen.Gestion.DebugColors
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.IGestorTerapeutas
import java.sql.SQLException

class GestionarTerapeutas: IGestorTerapeutas {

    override fun borrarTerapeuta(terapeuta: Terapeuta) {
        val user = obtenerTerapeuta(terapeuta.id)
        if (user != null) {
            val query = "DELETE FROM terapeuta WHERE ID_TERAPEUTA = ?"

            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setInt(1, terapeuta.id)

                val usuario = Gestores.gestorUsuarios.obtenerUsuarioId(terapeuta.idUsuario)
                if (usuario != null) {
                    val logEliminar = Log(usuario.username, usuario.email, Gestores.fechaActual(), "Ficha Paciente eliminado")
                    Gestores.gestorLogs.addLog(logEliminar)
                }

                statement.executeUpdate()
                statement.close()
            } catch (e: SQLException) {
                println(DebugColors.error() + " Error al " + DebugColors.rojo("eliminar") + " el terapeuta:")
                println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        } else {
            println(DebugColors.error() + " El terapeuta no existe.")
        }
    }

    override fun obtenerTerapeuta(id: Int): Terapeuta? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM terapeuta WHERE ID_TERAPEUTA = ?")
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        var terapeuta: Terapeuta? = null

        try {
            if (resultSet.next()) {
                val id = resultSet.getInt("ID_TERAPEUTA")
                val nombre = resultSet.getString("NOMBRE")
                val apellido = resultSet.getString("APELLIDO")
                val fecha_nacimiento = Gestores.parsearFecha(resultSet.getInt("FECHA_NACIMIENTO"))
                val idUsuario = resultSet.getInt("ID_USUARIO")

                terapeuta = Terapeuta(id, nombre, apellido, fecha_nacimiento, idUsuario)
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos del terapeuta:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return terapeuta
    }

    override fun obtenerTerapeutaIdUsuario(idUsuario: Int): Terapeuta? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM terapeuta WHERE ID_USUARIO = ?")
        statement.setInt(1, idUsuario)
        val resultSet = statement.executeQuery()

        var terapeuta: Terapeuta? = null

        try {
            if (resultSet.next()) {
                val id = resultSet.getInt("ID_TERAPEUTA")
                val nombre = resultSet.getString("NOMBRE")
                val apellido = resultSet.getString("APELLIDO")
                val fecha_nacimiento = Gestores.parsearFecha(resultSet.getInt("FECHA_NACIMIENTO"))

                terapeuta = Terapeuta(id, nombre, apellido, fecha_nacimiento, idUsuario)
            }
        } catch (e: SQLException) {
            println(DebugColors.error() + " Error al obtener los datos del terapeuta:")
            println(DebugColors.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return terapeuta
    }

    override fun modificarTerapeuta(terapeutaOriginal: Terapeuta, datosNuevos: Terapeuta) {
        TODO("Not yet implemented")
    }

    override fun obtenerTerapeutas(): ArrayList<Terapeuta> {
        TODO("Not yet implemented")
    }
}
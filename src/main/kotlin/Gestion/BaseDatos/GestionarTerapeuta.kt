package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Datos.Sesion
import es.cifpvirgen.Datos.Terapeuta
import es.cifpvirgen.Gestion.Inputs.IGestorTerapeuta
import java.sql.ResultSet

class GestionarTerapeuta : IGestorTerapeuta {
    override fun insertarCita(sesion: Sesion, dni : String, idTerapeuta: Int): Boolean {
        val query =  """
            INSERT INTO SESION (ID_SESION, INDIVIDUO_DNI, TERAPEUTA_ID, FECHA_SESION, SESION_FAMILIAR)
            VALUES(?,?,?,?,?)
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        var rs : ResultSet? = null
        try {
            statement.setInt(1, sesion.idSesion)
            statement.setString(2, dni)
            statement.setInt(3, idTerapeuta)
            statement.setDate(4, sesion.fecha)
            statement.setInt(5, sesion.familiar)
            rs = statement.executeQuery()!!
        } catch (e : Exception) {
            return false
        } finally {
            statement.close()
            rs?.close()
        }
        return true
    }

    override fun modificarDatos(terapeuta: Terapeuta): Boolean {
        val query =  """
            UPDATE TERAPEUTA
            SET NOMBRE = ?
            APELLIDO = ?
            WHERE ID_TERAPEUTA = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        var rs : ResultSet? = null
        try {
            statement.setString(1, terapeuta.nombre)
            statement.setString(2, terapeuta.apellido)
            statement.setInt(3, terapeuta.idTerapeuta)
            rs = statement.executeQuery()!!
        } catch (e : Exception) {
            return false
        } finally {
            statement.close()
            rs?.close()
        }
        return true
    }
}
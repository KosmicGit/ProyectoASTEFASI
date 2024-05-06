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

    override fun modificarCita(sesion: Sesion): Boolean {
        val query =  """
            UPDATE SESION_INDIVIDUO_TERAPEUTA
            SET 
            FECHA = ?
            DNI_INDIVIDUO = ?
            FAMILIAR = ?
            WHERE ID_TERAPEUTA = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        var rs : ResultSet? = null
        try {
            statement.setDate(1, sesion.fecha)
            statement.setString(2,sesion.dniCliente)
            statement.setInt(3,sesion.familiar)
            statement.setInt(4,sesion.idTerapeuta)
            rs = statement.executeQuery()!!
        } catch (e : Exception) {
            return false
        } finally {
            statement.close()
            rs?.close()
        }
        return true
    }

    override fun borrarCita(sesion: Sesion): Boolean {
        val query =  """
            DELETE FROM SESION_INDIVIDUO_TERAPEUTA
            WHERE ID_SESION = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        var rs : ResultSet? = null
        try {
            statement.setInt(1, sesion.idSesion)
            rs = statement.executeQuery()!!
        } catch (e : Exception) {
            return false
        } finally {
            statement.close()
            rs?.close()
        }
        return true
    }

    override fun historicoCitasTerapeuta(idUsuario: Int): ArrayList<Sesion> {
        val query = """
            SELECT SIT.ID_SESION, SIT.FECHA_SESION FECHA, C.NOMBRE, C.APELLIDO, SIT.SESION_FAMILIAR  
            FROM SESION_INDIVIDUO_TERAPEUTA SIT
            INNER JOIN TERAPEUTA T ON SIT.ID_TERAPEUTA = T.ID_TERAPEUTA
            INNER JOIN CLIENTE C ON C.DNI = SIT.INDIVIDUO_DNI
            INNER JOIN USUARIO U ON U.ID_USUARIO = C.ID_USUARIO
            WHERE U.ID_USUARIO = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, idUsuario)
        var rs = statement.executeQuery()!!
        var sesiones = ArrayList<Sesion>()
        try {
            while (rs?.next() == true) {
                val sesion = Sesion(rs.getInt("ID_SESION"),rs.getDate("FECHA"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getInt("SESION_FAMILIAR"))
                sesiones.add(sesion)
            }
        } catch (e : Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return sesiones
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
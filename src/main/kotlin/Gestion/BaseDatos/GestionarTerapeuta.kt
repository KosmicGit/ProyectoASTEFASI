package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Datos.Sesion
import es.cifpvirgen.Datos.Terapeuta
import es.cifpvirgen.Gestion.Inputs.IGestorTerapeuta
import java.sql.ResultSet

class GestionarTerapeuta : IGestorTerapeuta {
    /**
     * Función para que el terapeuta inserte sesiones
     *
     * @param sesion
     * @param dni
     * @param idTerapeuta
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
    override fun insertarSesion(sesion: Sesion, dni : String, idTerapeuta: Int): Boolean {
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

    /**
     * Función para que el terapeuta modifique las sesiones
     *
     * @param sesion
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
    override fun modificarSesion(sesion: Sesion): Boolean {
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

    /**
     * Función para que el terapueta borre sesiones
     *
     * @param sesion
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
    override fun borrarSesion(sesion: Sesion): Boolean {
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

    /**
     * Muestra el historico de citas por la parte del terapeuta
     *
     * @param terapeuta
     * @return Devuelve un arrayList vacio si falla en algun momento o ArrayList con todas las sesiones del terapeuta
     */
    override fun historicoCitasTerapeuta(terapeuta: Terapeuta): ArrayList<Sesion> {
        val query = """
            SELECT SIT.ID_SESION, SIT.FECHA_SESION FECHA, C.NOMBRE, C.APELLIDO, SIT.SESION_FAMILIAR  
            FROM SESION_INDIVIDUO_TERAPEUTA SIT
            INNER JOIN CLIENTE C ON C.DNI = SIT.INDIVIDUO_DNI
            WHERE SIT.TERAPEUTA_ID_TERAPEUTA = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, terapeuta.idTerapeuta)
        val rs = statement.executeQuery()!!
        val sesiones = ArrayList<Sesion>()
        try {
            while (rs.next()) {
                val sesion = Sesion(rs.getInt("ID_SESION"),rs.getDate("FECHA"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getInt("SESION_FAMILIAR"))
                sesiones.add(sesion)
            }
        } catch (_: Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return sesiones
    }

    /**
     * Funcion para que el teraputa modifique sus propios datos
     *
     * @param terapeuta
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
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
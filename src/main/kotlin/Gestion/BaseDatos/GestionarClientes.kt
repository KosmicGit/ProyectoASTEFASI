package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Datos.Sesion
import es.cifpvirgen.Datos.Cliente
import es.cifpvirgen.Gestion.Inputs.IGestorCliente
import java.sql.ResultSet

class GestionarClientes : IGestorCliente {

    override fun conseguirClientePorId(idUsuario: Int): Cliente? {
        val query = "SELECT * FROM CLIENTE WHERE ID_USUARIO = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, idUsuario)
        val rs = statement.executeQuery()!!
        var cliente : Cliente? = null
        try {
            if (rs.next()) {
                cliente = Cliente(rs.getString("DNI"), rs.getString("NOMBRE"), rs.getString("CAUSA_CITA"), rs.getString("APELLIDO"), rs.getInt("EDAD"), rs.getInt("ID_ROL"), rs.getInt("ID_USUARIO") )
            }
        } catch (_ : Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return cliente
    }

    override fun modificarDatos(cliente: Cliente) : Boolean {
        val query =  """
            UPDATE CLIENTE
            SET NOMBRE = ?
            APELLIDO = ?
            CAUSA_CITA = ?
            WHERE DNI = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        var rs : ResultSet? = null
        try {
            statement.setString(1, cliente.nombre)
            statement.setString(2, cliente.apellido)
            statement.setString(3, cliente.causa_cita)
            statement.setString(4, cliente.dni)
            rs = statement.executeQuery()!!
        } catch (e : Exception) {
            return false
        } finally {
            statement.close()
            rs?.close()
        }
        return true
    }

    override fun historicoCitasUsuario(idUsuario: Int): ArrayList<Sesion> {
        val query = """
            SELECT SIT.ID_SESION, SIT.FECHA_SESION FECHA, T.NOMBRE, T.APELLIDO, SIT.SESION_FAMILIAR  
            FROM SESION_INDIVIDUO_TERAPEUTA SIT
            INNER JOIN TERAPEUTA T ON SIT.ID_TERAPEUTA = T.ID_TERAPEUTA
            INNER JOIN CLIENTE C ON C.DNI = SIT.INDIVIDUO_DNI
            INNER JOIN USUARIO U ON U.ID_USUARIO = C.ID_USUARIO
            WHERE U.ID_USUARIO = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, idUsuario)
        val rs = statement.executeQuery()!!
        val sesiones = ArrayList<Sesion>()
        try {
            while (rs.next()) {
                val sesion = Sesion(rs.getInt("ID_SESION"),rs.getDate("FECHA"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getInt("SESION_FAMILIAR"))
                sesiones.add(sesion)
            }
        } catch (_ : Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return sesiones
    }

}
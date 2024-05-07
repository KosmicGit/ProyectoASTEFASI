package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Datos.Sesion
import es.cifpvirgen.Datos.Cliente
import es.cifpvirgen.Gestion.Inputs.IGestorCliente
import java.sql.ResultSet

class GestionarClientes : IGestorCliente {

    /**
     * Función para conseguir un cliente a partir de un la Id de un usuario pasado por parametro
     *
     * @param idUsuario
     * @return cliente vacio si el id a fallado o cliente con sus respectivos datos
     */
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

    /**
     * Funcion para que el cliente modifique sus propios datos
     *
     * @param cliente
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
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

    /**
     * Metodo para conseguir el historico de sesiones de un cliente concreto
     *
     * @param cliente
     * @return Devuelve un arrayList vacio si falla en algun momento o ArrayList con todas las sesiones del cliente
     */
    override fun historicoCitasCliente(cliente: Cliente): ArrayList<Sesion> {
        val query = """
            SELECT SIT.ID_SESION, SIT.FECHA_SESION FECHA, T.NOMBRE, T.APELLIDO, SIT.SESION_FAMILIAR  
            FROM SESION_INDIVIDUO_TERAPEUTA SIT
            INNER JOIN TERAPEUTA T ON SIT.ID_TERAPEUTA = T.ID_TERAPEUTA
            WHERE SIT.DNI_CLIENTE = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, cliente.dni)
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
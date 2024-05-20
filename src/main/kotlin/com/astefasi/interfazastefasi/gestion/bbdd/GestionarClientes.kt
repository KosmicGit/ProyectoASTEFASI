package com.astefasi.interfazastefasi.gestion.bbdd

import com.astefasi.interfazastefasi.util.data.Sesion
import com.astefasi.interfazastefasi.util.data.user.Cliente
import java.sql.ResultSet

class GestionarClientes {
    /**
     * Función para conseguir un cliente a partir de un la Id de un usuario pasado por parametro
     *
     * @param idUsuario
     * @return cliente vacio si el id a fallado o cliente con sus respectivos datos
     */
    fun conseguirClientePorId(idUsuario: Int): Cliente? {
        val query = "SELECT * FROM Cliente WHERE ID_USUARIO = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, idUsuario)
        val rs = statement.executeQuery()
        var cliente : Cliente? = null
        try {
            if (rs.next()) {
                val clienteEncontrado = Cliente(rs.getString("DNI"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("CAUSA_CITA"), rs.getDate("FECHA_NACIMIENTO"), rs.getInt("ID_USUARIO") )
                cliente = clienteEncontrado
            }
        } catch (_ : Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return cliente
    }

    /**
     * Función para conseguir un cliente a partir de un dni  pasado por parametro
     *
     * @param dni
     * @return cliente vacio si el id a fallado o cliente con sus respectivos datos
     */
    fun conseguirClientePorDNI(dni: String): Cliente? {
        val query = "SELECT * FROM Cliente WHERE DNI = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, dni)
        val rs = statement.executeQuery()
        var cliente : Cliente? = null
        try {
            if (rs.next()) {
                cliente = Cliente(
                    rs.getString("DNI"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getString("CAUSA_CITA"),
                    rs.getDate("FECHA_NACIMIENTO"),
                    rs.getInt("ID_USUARIO")
                )
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
    fun modificarDatos(cliente: Cliente) : Boolean {
        val query =  """
            UPDATE Cliente
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
    fun historicoCitasCliente(cliente: Cliente): ArrayList<Sesion> {
        val query = """
            SELECT S.ID_SESION, S.INDIVIDUO_DNI, S.FECHA_SESION FECHA, S.ID_TERAPEUTA, S.FAMILIAR, S.ACTIVA  
            FROM Sesion S
            WHERE S.INDIVIDUO_DNI = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, cliente.dni)
        val rs = statement.executeQuery()!!
        val sesiones = ArrayList<Sesion>()
        try {
            while (rs.next()) {
                val sesion = Sesion(rs.getInt("ID_SESION"), rs.getString("INDIVIDUO_DNI"), rs.getInt("ID_TERAPEUTA"), rs.getDate("FECHA"), rs.getInt("FAMILIAR"), rs.getInt("ACTIVA"))
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
package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Datos.Cita
import es.cifpvirgen.Datos.Cliente
import es.cifpvirgen.Gestion.Inputs.IGestorCliente

class GestionarClientes : IGestorCliente {

    override fun conseguirClientePorId(idUsuario: Int): Cliente? {
        val query = "SELECT * FROM CLIENTE WHERE ID_USUARIO = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, idUsuario)
        var rs = statement.executeQuery()!!
        var cliente : Cliente? = null
        try {
            if (rs.next()) {
                cliente = Cliente(rs.getString("DNI"), rs.getString("NOMBRE"), rs.getString("CAUSA_CITA"), rs.getString("APELLIDO"), rs.getInt("EDAD"), rs.getInt("ID_ROL"), rs.getInt("ID_USUARIO") )
            }
        } catch (e : Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return cliente
    }

    override fun historicoCitasUsuario(idUsuario: Int): ArrayList<Cita> {
        val query = """
            SELECT SIT.FECHA_SESION FECHA, T.NOMBRE, T.APELLIDO, SIT.SESION_FAMILIAR  
            FROM SESION_INDIVIDUO_TERAPEUTA SIT, CLIENTE C, USUARIO U, TERAPEUTA T  
            WHERE U.ID_USUARIO = ?, U.ID_USUARIO = C.ID_USUARIO, C.DNI = SIT.INDIVIDUO_DNI, SIT.ID_TERAPEUTA = T.ID_TERAPEUTA
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, idUsuario)
        var rs = statement.executeQuery()!!
        var citas = ArrayList<Cita>()
        try {
            while (rs?.next() == true) {
                val cita = Cita(rs.getDate("FECHA"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getInt("SESION_FAMILIAR"))
                citas.add(cita)
            }
        } catch (e : Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return citas
    }

}
package es.cifpvirgen.Gestion.BaseDatos

import es.cifpvirgen.Datos.Cliente
import es.cifpvirgen.Datos.Familia
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
            INSERT INTO SESION ( INDIVIDUO_DNI, TERAPEUTA_ID, FECHA_SESION, SESION_FAMILIAR)
            VALUES(?,?,?,?)
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        var rs : ResultSet? = null
        try {
            statement.setString(1, dni)
            statement.setInt(2, idTerapeuta)
            statement.setDate(3, sesion.fecha)
            statement.setInt(4, sesion.familiar)
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
            UPDATE SESION
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
            DELETE FROM SESION
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
            SELECT S.ID_SESION, S.FECHA_SESION FECHA, C.NOMBRE, C.APELLIDO, S.SESION_FAMILIAR  
            FROM SESION S
            INNER JOIN CLIENTE C ON C.DNI = S.INDIVIDUO_DNI
            WHERE S.ID_TERAPEUTA = ?
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

    /**
     * Funcion para crear familia
     *
     * @param nombre
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
    override fun aniadirFamilia(nombre : String) :Boolean {
        val query =
            """
            INSERT INTO FAMILIA(NOMBRE_FAMILIA)
            VALUES(?) 
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        var rs : ResultSet? = null
        try {
            statement.setString(1, nombre)
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
     * Funcion para añadir clientes a la familia
     *
     * @param familia
     * @param cliente
     * @param parentesco
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
    override fun aniadirFamiliar(familia: Familia, cliente: Cliente, parentesco : Int): Boolean {
        val query =
            """
            INSERT INTO CLIENTE_FAMILIA(INDIVIDUO_DNI,ID_FAMILIA,ID_PARENTESCO)
            VALUES(?,?,?) 
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        var rs : ResultSet? = null
        try {
            statement.setString(1, cliente.dni)
            statement.setInt(2, familia.id)
            statement.setInt(3, parentesco)
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
     * Funcion para ver todas las familias
     *
     * @return Retorna un ArrayList con las familias
     */
    override fun verFamilias(): ArrayList<Familia> {
        val query = """
            SELECT ID_FAMILIA, NOMBRE_FAMILIA
            FROM FAMILIA
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        val rs = statement.executeQuery()!!
        val familias = ArrayList<Familia>()
        try {
            while (rs.next()) {
                val familia = Familia(rs.getInt("ID_FAMILIA"), rs.getString("NOMBRE_FAMILIA"))
                familias.add(familia)
            }
        } catch (_: Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return familias
    }


    /**
     * Funcion para ver todas las familias de un cliente
     *
     * @param dni
     * @return Retorna una lista con los integrantes de la familia del cliente
     */
    override fun verFamilia(dni:String) : ArrayList<Familia> {
        val query = """
            SELECT CF.ID_FAMILIA, CF.INDIVIDUO_DNI , F.NOMBRE_FAMILIA, P.NOMBRE_PARENTESCO
            FROM CLIENTE_FAMILIA CF
            INNER JOIN PARENTESCO P INTO CF.ID_PARENTECO = P.PARENTESCO
            INNER JOIN FAMILIA F INTO CF.ID_FAMILIA = C.ID_FAMILIA
            WHERE C.CLIENTE = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, dni)
        val rs = statement.executeQuery()!!
        val familia = ArrayList<Familia>()
        try {
            if (rs.next()) {
                while(rs.next()) {
                    val familiar = Familia (
                        rs.getInt("ID_FAMILIA"),
                        rs.getString("NOMBRE_FAMILIA"),
                        rs.getString("INDIVIDUO_DNI"),
                        rs.getString("NOMBRE_PARENTESCO")
                    )
                    familia.add(familiar)
                }

            }
        } catch (_: Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return familia
    }

    /**
     * Funcion para ver todas las familias de un cliente
     *
     * @param id
     * @return Retorna una lista con los integrantes de la familia del cliente
     */
    override fun verFamilia(id: Int): ArrayList<Familia> {
        val query = """
            SELECT CF.ID_FAMILIA, CF.INDIVIDUO_DNI , F.NOMBRE_FAMILIA, P.NOMBRE_PARENTESCO
            FROM CLIENTE_FAMILIA CF
            INNER JOIN PARENTESCO P INTO CF.ID_PARENTECO = P.PARENTESCO
            INNER JOIN FAMILIA F INTO CF.ID_FAMILIA = C.ID_FAMILIA
            WHERE F.ID_FAMILIA = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, id)
        val rs = statement.executeQuery()!!
        val familia = ArrayList<Familia>()
        try {
            if (rs.next()) {
                while(rs.next()) {
                    val familiar = Familia (
                        rs.getInt("ID_FAMILIA"),
                        rs.getString("NOMBRE_FAMILIA"),
                        rs.getString("INDIVIDUO_DNI"),
                        rs.getString("NOMBRE_PARENTESCO")
                    )
                    familia.add(familiar)
                }

            }
        } catch (_: Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return familia
    }
}
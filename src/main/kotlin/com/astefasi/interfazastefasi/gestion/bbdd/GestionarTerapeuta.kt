package com.astefasi.interfazastefasi.gestion.bbdd

import com.astefasi.interfazastefasi.util.data.Sesion
import com.astefasi.interfazastefasi.util.data.family.Familia
import com.astefasi.interfazastefasi.util.data.user.Cliente
import com.astefasi.interfazastefasi.util.data.user.Terapeuta
import java.sql.ResultSet
import java.sql.SQLException

class GestionarTerapeuta {
    /**
     * Función para conseguir un terapeuta a partir de un la Id de un usuario pasado por parametro
     *
     * @param idUsuario
     * @return terapeuta vacio si el id a fallado o terapeuta con sus respectivos datos
     */
    fun conseguirTerapeutaPorIdUsuario(idUsuario: Int): Terapeuta? {
        val query = "SELECT * FROM Terapeuta WHERE ID_USUARIO = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, idUsuario)
        val rs = statement.executeQuery()!!
        var terapeuta : Terapeuta? = null
        try {
            if (rs.next()) {
                terapeuta = Terapeuta(rs.getInt("ID_TERAPEUTA"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getDate("FECHA_NACIMIENTO"), rs.getInt("AÑOS_EXPERIENCIA"), rs.getInt("ID_USUARIO"))
            }
        } catch (_ : Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return terapeuta
    }

    /**
     * Función para conseguir un terapeuta a partir de un la Id de un terapeuta pasado por parametro
     *
     * @param idUsuario
     * @return terapeuta vacio si el id a fallado o terapeuta con sus respectivos datos
     */
    fun conseguirTerapeutaPorIdTerapeuta(idTerapeuta: Int): Terapeuta? {
        val query = "SELECT * FROM Terapeuta WHERE ID_TERAPEUTA = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, idTerapeuta)
        val rs = statement.executeQuery()!!
        var terapeuta : Terapeuta? = null
        try {
            if (rs.next()) {
                terapeuta = Terapeuta(rs.getInt("ID_TERAPEUTA"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getDate("FECHA_NACIMIENTO"), rs.getInt("AÑOS_EXPERIENCIA"), rs.getInt("ID_USUARIO"))
            }
        } catch (_ : Exception) {

        } finally {
            statement.close()
            rs.close()
        }
        return terapeuta
    }

    /**
     * Función para que el terapeuta inserte sesiones
     *
     * @param sesion
     * @param dni
     * @param idTerapeuta
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
    fun insertarSesion(sesion: Sesion, dni : String, idTerapeuta: Int): Boolean {
        val query =  """
            INSERT INTO Sesion ( INDIVIDUO_DNI, ID_TERAPEUTA, FECHA_SESION, FAMILIAR, ACTIVA)
            VALUES(?,?,?,?,?)
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        try {
            statement.setString(1, dni)
            statement.setInt(2, idTerapeuta)
            statement.setDate(3, sesion.fecha)
            statement.setInt(4, sesion.familiar)
            statement.setInt(5, sesion.activa)
            statement.executeUpdate()
        } catch (e : Exception) {
            return false
        } finally {
            statement.close()
        }
        return true
    }

    /**
     * Función para que el terapeuta modifique las sesiones
     *
     * @param sesion
     * @return true si a funcionado correctamente o false si falla en alguna parte del proceso
     */
    fun modificarSesion(sesion: Sesion): Boolean {
        val query =  """
            UPDATE Sesion
            SET FECHA = ?,
            DNI_INDIVIDUO = ?,
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
    fun borrarSesion(idSesion: Int): Boolean {
        val query =  """
            DELETE FROM Sesion
            WHERE ID_SESION = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        try {
            statement.setInt(1, idSesion)
            statement.executeUpdate()
        } catch (e : Exception) {
            return false
        } finally {
            statement.close()
        }
        return true
    }

    /**
     * Muestra el historico de citas por la parte del terapeuta
     *
     * @param terapeuta
     * @return Devuelve un arrayList vacio si falla en algun momento o ArrayList con todas las sesiones del terapeuta
     */
    fun historicoCitasTerapeuta(terapeuta: Terapeuta): ArrayList<Sesion> {
        val query = """
            SELECT S.ID_SESION, S.INDIVIDUO_DNI, S.FECHA_SESION FECHA, S.ID_TERAPEUTA, S.FAMILIAR, S.ACTIVA  
            FROM Sesion S
            WHERE S.ID_TERAPEUTA = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setInt(1, terapeuta.idTerapeuta)
        val rs = statement.executeQuery()!!
        val sesiones = ArrayList<Sesion>()
        try {
            while (rs.next()) {
                val sesion = Sesion(rs.getInt("ID_SESION"), rs.getString("INDIVIDUO_DNI"), rs.getInt("ID_TERAPEUTA"), rs.getDate("FECHA"), rs.getInt("FAMILIAR"), rs.getInt("ACTIVA"))
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
    fun modificarDatos(terapeuta: Terapeuta): Boolean {
        val query =  """
            UPDATE Terapeuta
            SET NOMBRE = ?,
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
    fun aniadirFamilia(nombre : String) :Boolean {
        val query =
            """
            INSERT INTO Familia(NOMBRE_FAMILIA)
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
    fun aniadirFamiliar(familia: Familia, cliente: Cliente, parentesco : Int): Boolean {
        val query =
            """
            INSERT INTO Cliente_familia(INDIVIDUO_DNI,ID_FAMILIA,ID_PARENTESCO)
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

    fun obtenerClientes(): ArrayList<Cliente> {
        val query = """
            SELECT * FROM Cliente
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        val rs = statement.executeQuery()
        val clientes = ArrayList<Cliente>()
        try {
            while (rs.next()) {
                val cliente = Cliente(
                    rs.getString("DNI"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getString("CAUSA_CITA"),
                    rs.getDate("FECHA_NACIMIENTO"),
                    rs.getInt("ID_USUARIO")
                )
                clientes.add(cliente)
            }
        }catch (_: SQLException) {

        }finally {
            rs.close()
            statement.close()
        }
        return clientes
    }

    /**
     * Funcion para ver todas las familias
     *
     * @return Retorna un ArrayList con las familias
     */
    fun verFamilias(): ArrayList<Familia> {
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
    fun verFamilia(dni:String) : ArrayList<Familia> {
        val query = """
            SELECT CF.ID_FAMILIA, CF.INDIVIDUO_DNI , F.NOMBRE_FAMILIA, P.NOMBRE_PARENTESCO 
            FROM Cliente_Familia CF 
            INNER JOIN Parentesco P ON CF.ID_PARENTESCO = P.ID_PARENTESCO 
            INNER JOIN Familia F ON CF.ID_FAMILIA = F.ID_FAMILIA 
            WHERE CF.INDIVIDUO_DNI = ?
            """
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, dni)
        val rs = statement.executeQuery()!!
        val familia = ArrayList<Familia>()
        try {
            while(rs.next()) {
                val familiar = Familia (
                    rs.getInt("ID_FAMILIA"),
                    rs.getString("NOMBRE_FAMILIA"),
                    rs.getString("INDIVIDUO_DNI"),
                    rs.getString("NOMBRE_PARENTESCO")
                )
                familia.add(familiar)
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
    fun verFamilia(id: Int): ArrayList<Familia> {
        val query = """
            SELECT CF.ID_FAMILIA, CF.INDIVIDUO_DNI , F.NOMBRE_FAMILIA, P.NOMBRE_PARENTESCO
            FROM Cliente_familia CF
            INNER JOIN Parentesco P INTO CF.ID_PARENTECO = P.PARENTESCO
            INNER JOIN Familia F INTO CF.ID_FAMILIA = C.ID_FAMILIA
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
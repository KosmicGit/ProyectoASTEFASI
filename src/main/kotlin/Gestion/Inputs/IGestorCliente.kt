package es.cifpvirgen.Gestion.Inputs

import es.cifpvirgen.Datos.Sesion
import es.cifpvirgen.Datos.Cliente


interface IGestorCliente {

    fun conseguirClientePorId(idUsuario : Int) : Cliente?
    fun modificarDatos (cliente : Cliente) : Boolean
    fun historicoCitasUsuario (idUsuario : Int) : ArrayList<Sesion>
}
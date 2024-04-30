package es.cifpvirgen.Gestion.Inputs

import es.cifpvirgen.Datos.Cita
import es.cifpvirgen.Datos.Cliente


interface IGestorCliente {

    fun conseguirClientePorId(idUsuario : Int) : Cliente?

    fun historicoCitasUsuario (idUsuario : Int) : ArrayList<Cita>
}
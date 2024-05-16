package es.cifpvirgen.Gestion.Inputs


import es.cifpvirgen.Datos.Cliente
import es.cifpvirgen.Datos.Familia
import es.cifpvirgen.Datos.Sesion
import es.cifpvirgen.Datos.Terapeuta

interface IGestorTerapeuta {

    fun insertarSesion (sesion : Sesion, dni : String, idTerapeuta: Int) : Boolean
    fun modificarSesion (sesion: Sesion) : Boolean
    fun borrarSesion (sesion: Sesion) : Boolean
    fun historicoCitasTerapeuta (terapeuta: Terapeuta) : ArrayList<Sesion>
    fun modificarDatos (terapeuta : Terapeuta) : Boolean
    fun aniadirFamilia(nombre : String) : Boolean
    fun aniadirFamiliar(familia: Familia, cliente: Cliente, parentesco : Int) : Boolean
    fun verFamilias() : ArrayList<Familia>
    fun verFamilia(dni : String) : ArrayList<Familia>
    fun verFamilia(id : Int) : ArrayList<Familia>
}
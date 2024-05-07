package es.cifpvirgen.Gestion.Inputs


import es.cifpvirgen.Datos.Sesion
import es.cifpvirgen.Datos.Terapeuta

interface IGestorTerapeuta {

    fun insertarSesion (sesion : Sesion, dni : String, idTerapeuta: Int) : Boolean
    fun modificarSesion (sesion: Sesion) : Boolean
    fun borrarSesion (sesion: Sesion) : Boolean
    fun historicoCitasTerapeuta (terapeuta: Terapeuta) : ArrayList<Sesion>
    fun modificarDatos (terapeuta : Terapeuta) : Boolean
}
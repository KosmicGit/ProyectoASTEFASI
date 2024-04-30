package es.cifpvirgen.Gestion.Inputs

import es.cifpvirgen.Datos.Sesion
import es.cifpvirgen.Datos.Terapeuta

interface IGestorTerapeuta {

    fun insertarCita (sesion : Sesion, dni : String, idTerapeuta: Int) : Boolean
    fun modificarDatos (terapeuta : Terapeuta) : Boolean
}
package es.cifpvirgen.Datos

import java.sql.Date

data class Sesion (val idSesion: Int, val dniCliente : String , val idTerapeuta : Int, val fecha: Date,val familiar: Int) {
    lateinit var nombre : String
    lateinit var apellido: String

    constructor( idSesion: Int,  fecha : Date,  nombre : String, apellido : String,  familiar : Int) : this(idSesion , "", -1,fecha,familiar) {
        this.nombre = nombre
        this.apellido = apellido
    }

}
package com.astefasi.interfazastefasi.util.data

import java.sql.Date

data class Sesion (val idSesion: Int, val dniCliente : String , val idTerapeuta : Int, val fecha: Date,val familiar: Int, val activa : Int) {
    lateinit var nombre : String
    lateinit var apellido: String

    constructor( idSesion: Int,  fecha : Date,  nombre : String, apellido : String,  familiar : Int, activa : Int) : this(idSesion , "", -1,fecha,familiar, activa) {
        this.nombre = nombre
        this.apellido = apellido
    }

}

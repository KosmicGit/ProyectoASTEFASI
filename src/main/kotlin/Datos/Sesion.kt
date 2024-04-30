package es.cifpvirgen.Datos

import java.sql.Date

data class Sesion (val idSesion: Int, val fecha : Date, val nombre : String, val apellido : String, val familiar : Int) {
}
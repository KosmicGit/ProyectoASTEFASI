package com.astefasi.interfazastefasi.util.data.user

import java.sql.Date

data class Terapeuta (val idTerapeuta : Int, var nombre : String, var apellido : String, val fecha_nacimiento : Date, val anios_experiencia : Int, val idUsuario : Int )

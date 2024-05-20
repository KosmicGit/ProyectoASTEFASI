package com.astefasi.interfazastefasi.util.data.user

import java.sql.Date

data class Cliente (val dni : String, var nombre : String,var apellido : String ,var causa_cita : String,val fecha_nacimiento : Date, val idUsuario : Int )

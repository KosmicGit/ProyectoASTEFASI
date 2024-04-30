package es.cifpvirgen.Datos

data class Cliente (val dni : String, var nombre : String,var apellido : String ,var causa_cita : String,val edad : Int, val idRol : Int ,val idUsuario : Int ) {
}
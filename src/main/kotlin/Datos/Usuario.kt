package es.cifpvirgen.Data


data class Usuario(var idUsuario: Int, var username: String, var email: String, var password: String, var rol: Roles, var verificado: Boolean) {

    fun copy(): Usuario {
        return Usuario(this.idUsuario, this.username, this.email, this.password, this.rol, this.verificado)
    }
}

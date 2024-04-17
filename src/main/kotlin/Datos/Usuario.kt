package es.cifpvirgen.Data

import kweb.state.KVar

data class Usuario(var idUsuario: Int, var username: String, var email: String, var password: String, var rol: Roles) {

    fun copy(): Usuario {
        return Usuario(this.idUsuario, this.username, this.email, this.password, this.rol)
    }

    companion object {
        var username = ""
        var password = ""
    }
}

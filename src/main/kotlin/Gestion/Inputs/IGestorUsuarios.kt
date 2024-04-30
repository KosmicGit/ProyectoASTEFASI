package es.cifpvirgen.Gestion.Inputs

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario

interface IGestorUsuarios {

    fun obtenerUltimoID(): Int?

    fun verificarUsuario(usuario: Usuario)

    fun estaVerificado(usuario: Usuario): Boolean

    fun addUsuario(usuario: Usuario)

    fun borrarUsuario(usuario: Usuario)

    fun obtenerUsuario(username: String): Usuario?

    fun obtenerUsuarioMail(email: String): Usuario?

    fun guardarFoto(imgB64: String, usuario: Usuario)

    fun obtenerFoto(usuario: Usuario): String?

    fun modificarPermisos(usuario: Usuario, rol: Roles)

    fun modificarUsuario(usuarioOriginal: Usuario, datosNuevos: Usuario)

    fun obtenerUsuarios(): ArrayList<Usuario>

    fun comprobarCredenciales(correo : String, pass : String) : Usuario?
}
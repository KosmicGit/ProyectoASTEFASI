package es.cifpvirgen.Paginas.Profile.Settings

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import kweb.*
import kweb.components.Component
import kweb.util.json

fun Component.emailVerify(modificacion: Usuario) {
    val usuario = Gestores.gestorUsuarios.obtenerUsuarioId(modificacion.idUsuario)!!
    if (usuario.email == modificacion.email) {
        browser.callJsFunction("cerrarSesion({})", "sesion".json)
        browser.callJsFunction("mostrarNoti({})", "Este enlace ya no existe.".json)
        browser.url.value = "/login"
    } else {
        Gestores.gestorUsuarios.modificarUsuario(usuario, modificacion)
        browser.callJsFunction("cerrarSesion({})", "sesion".json)
        browser.callJsFunction("guardarCookie({})", Gestores.encriptarUsuario(modificacion).json)
        browser.callJsFunction("mostrarNoti({})", "Usuario actualizado correctamente.".json)
        browser.url.value = "/profile/settings"
    }
}
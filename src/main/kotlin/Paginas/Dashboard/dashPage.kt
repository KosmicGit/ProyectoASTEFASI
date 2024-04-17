package es.cifpvirgen.Paginas.Dashboard

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Paginas.checkUser
import kweb.components.Component

fun Component.dashPage(usuario: Usuario?) {
    if (usuario == null) {

    }
    if (!checkUser()) {

    }
}
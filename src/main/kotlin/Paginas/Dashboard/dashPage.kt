package es.cifpvirgen.Paginas.Dashboard

import es.cifpvirgen.Paginas.checkUser
import kweb.components.Component

fun Component.dashPage() {
    if (!checkUser()) {

    }
}
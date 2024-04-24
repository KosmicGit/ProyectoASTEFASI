package es.cifpvirgen.Paginas.Register

import kweb.br
import kweb.components.Component
import kweb.p

fun Component.registerFinal() {
    p { element.text("Ya puede iniciar sesión y acceder a las descargas, también") }
    p { element.text("puedes acceder al programa mediante su usuario y contraseña.") }
    br()
    p { element.text("Nos alegra tenerte con nosotros {usuario.username}.") }.classes("subtitle")
}
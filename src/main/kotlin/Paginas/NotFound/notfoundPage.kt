package es.cifpvirgen.Paginas.NotFound

import es.cifpvirgen.Paginas.cabezera
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component

import kweb.img

fun Component.notfoundPage() {
    cabezera()
    div {
        element("fieldset") {
            element("legend").text("Error 404")
            h1().text("No Encontrado")
            p().text("La URL que has solicitado no se encuentra en este servidor.")
            img(attributes = mapOf("src" to JsonPrimitive("https://png.pngtree.com/png-vector/20231001/ourmid/pngtree-cavalier-king-charles-spaniel-dog-sad-png-image_10054793.png")))
        }
    }.classes("formulario")
    h4().text("TerapiaWeb Server 1.0")
}
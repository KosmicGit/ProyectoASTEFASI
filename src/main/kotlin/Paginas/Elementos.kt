package es.cifpvirgen.Paginas

import kotlinx.coroutines.launch
import kweb.components.Component
import kweb.div
import kweb.h1
import kweb.h3

//Estilos
fun Component.cabezera() {
    div {
        element("center") {
            //Titulo
            val titulo = h1()
            titulo.text("Asociación de Terapeutas familiares Sistémicos")
            titulo.classes("title")

            //Eslogan
            val eslogan = h3()
            eslogan.text("🌈 Empresa de emociones, sentimientos y terapias.🌤️")
            eslogan.classes("subtitle has-text-gray")
        }
    }.classes("box")
}

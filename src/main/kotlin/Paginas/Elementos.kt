package es.cifpvirgen.Paginas

import kotlinx.coroutines.launch
import kweb.components.Component
import kweb.h1
import kweb.h3

//Estilos
fun Component.cabezera() {
    //Titulo
    val titulo = h1()
    titulo.text("Asociación de Terapeutas familiares Sistémicos")
    titulo.classes("titulo")

    //Eslogan
    val eslogan = h3()
    eslogan.text("🌈 Empresa de emociones, sentimientos y terapias.🌤️")
    eslogan.classes("eslogan")
}

//Funciones
fun Component.checkUser(): Boolean {
    var check = false
    elementScope().launch {
        val resultado = browser.callJsFunctionWithResult("return checkCookie('check')")
        if (resultado.toString() == "true") {
            check = true
        }
    }
    return check
}

fun Component.addCookie() {

}

fun Component.login() {

}
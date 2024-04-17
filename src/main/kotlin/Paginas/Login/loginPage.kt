package es.cifpvirgen.Paginas.Login

import es.cifpvirgen.Paginas.cabezera
import kweb.*
import kweb.components.Component
import kweb.state.KVar

fun Component.loginPage() {
    cabezera()

    val username = kvar("")
    val password = kvar("")

    div {
        //H2 Login
        h2().text("\uD83D\uDD12 Login").classes("formulario")
        //H3 Username
        var usernameh3 = h3()
        usernameh3.text("👤 Username:")
        usernameh3.classes("formulario")
        //Input Username
        var userinput = input(type = InputType.text)
        userinput.value = username
        userinput.classes("inputs")
        //H3 Password
        var passwordh3 = h3()
        passwordh3.text("\uD83D\uDD11 Password:")
        passwordh3.classes("formulario")
        //Input Password
        var passinput = input(type = InputType.password)
        passinput.value = password
        passinput.classes("inputs")
    }.classes("login-container")

    element("br")

    div {
        var botonlogin = button(type = ButtonType.button)
        botonlogin.text("Entrar")
        botonlogin.classes("bigbutton")
        botonlogin.on.click {
            botonlogin.text("Entrando...")
            if (username.value == "" || password.value == "") {
                botonlogin.text("Error")
                var errorlogin = h3()
                errorlogin.text = KVar("Por favor introduzca sus datos.")
                errorlogin.classes("eslogan")
            } else {
//                if (//login(username, password)) {
//                    browser.callJsFunction("setCookie('Check', '1234', 10)")
//                    url.value = "/dashboard"
//                } else {
//                    botonlogin.text("Error")
//                    var errorlogin = h3()
//                    errorlogin.text = KVar("😢No existe el Usuario o Contraseña.😔")
//                    errorlogin.classes("eslogan")
                //}
                }
            }
    }.classes("formulario")
}

//private fun login(user, passwd) {

//}
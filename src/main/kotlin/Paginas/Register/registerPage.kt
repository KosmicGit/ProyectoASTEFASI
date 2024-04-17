package es.cifpvirgen.Paginas.Register

import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Paginas.cabezera
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.registerPage() {
    element.classes("hero is-warning is-fullheight")
    div {
        br()
        cabezera()
        br()
        br()
        val username = kvar("")
        val password = kvar("")
        val email = kvar("")

        div {
            element("center") {
                div {
                    div {
                        div {
                            //H2 Login
                            h2().text("📋 Register").classes("title is-4 has-text-white")
                            br()
                            //H3 Username
                            var usernameh3 = h3()
                            usernameh3.text("👤 Username:")
                            usernameh3.classes("subtitle")
                            //Input Username
                            var userinput = input(type = InputType.text)
                            userinput.value = username
                            userinput.classes("input is-normal")
                            br()
                            br()
                            //H3 Password
                            var passwordh3 = h3()
                            passwordh3.text("\uD83D\uDD11 Password:")
                            passwordh3.classes("subtitle")
                            //Input Password
                            var passinput = input(type = InputType.password)
                            passinput.value = password
                            passinput.classes("input is-normal")

                            element("hr")

                            p().text("¿No estás registrado?")
                            p {
                                element.text("Hazlo ")
                                val registrarse = a()
                                registrarse.text("aquí")
                                registrarse.href = "registrar"
                            }
                        }

                        br()

                        div {
                            var botonlogin = button(type = ButtonType.button)
                            botonlogin.text("Apuntarse")
                            botonlogin.classes("button is-primary")
                            botonlogin.on.click {
                                botonlogin.classes("button is-link is-loading")
                                if (username.value == "" || password.value == "") {
                                    botonlogin.classes("button is-danger")
                                    botonlogin.text("Error")
                                    var errorlogin = h3()
                                    errorlogin.text = KVar("Por favor introduzca sus datos.")
                                    errorlogin.classes("eslogan")
                                } else {
                                    //
                                }
                            }
                        }
                    }.classes("box")
                }.classes("column is-half")
            }
        }
    }.classes("container")
}

private fun registrar() {

}
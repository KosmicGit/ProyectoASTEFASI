package es.cifpvirgen.Paginas.Login

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Paginas.cabezera
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.loginPage() {
        cabezera()
        val username = kvar("")
        val password = kvar("")

        div {
            element("center") {
                div {
                    div {
                        div {
                            //H2 Login
                            h2().text("\uD83D\uDD12 Login").classes("title is-4 has-text-white")
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

                            p { element.classes("formulario") }.text("¿No estás registrado?")
                            p {
                                element.text("Hazlo ")
                                element.classes("formulario")
                                val registrarse = a()
                                registrarse.text("aquí")
                                registrarse.href = "registrar"
                            }
                        }

                        br()

                        div {
                            var botonlogin = button(type = ButtonType.button)
                            botonlogin.text("Entrar")
                            botonlogin.classes("button is-danger")
                            botonlogin.on.click {
                                botonlogin.classes("button is-danger is-loading")
                                if (username.value == "" || password.value == "") {
                                    botonlogin.classes("button is-danger")
                                    botonlogin.text("Error")
                                    var errorlogin = h3()
                                    errorlogin.text = KVar("Por favor introduzca sus datos.")
                                    errorlogin.classes("eslogan")
                                } else {
                                    val usuario = login(username, password)
                                    if (usuario != null) {
                                        browser.callJsFunction("guardarCookie({})", Gestores.encriptarUsuario(usuario).json)
                                        browser.callJsFunction("redirect({})", "/registrar".json)
                                    } else {
                                        botonlogin.classes("button is-danger")
                                        botonlogin.text("Error")
                                        var errorlogin = h3()
                                        errorlogin.text = KVar("😢No existe el Usuario o Contraseña.😔")
                                        errorlogin.classes("eslogan")
                                    }
                                }
                            }
                        }
                    }.classes("box")
                }.classes("column is-half")
            }
        }
}
private fun login(user: KVar<String>, passwd: KVar<String>): Usuario? {
    var usuario: Usuario? = null
    usuario = Gestores.gestorUsuarios.obtenerUsuario(user.value)
    if (usuario != null) {
        if (usuario.password != passwd.value) {
            usuario = null
        }
    }
    return usuario
}
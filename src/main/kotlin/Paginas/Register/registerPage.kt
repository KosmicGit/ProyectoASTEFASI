package es.cifpvirgen.Paginas.Register

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.registerPage() {
    section {
        div {
            element("header") {
                div {
                    div {
                        a {
                            img(attributes = mapOf("src" to JsonPrimitive("https://i.ibb.co/F01PkQv/logo.png")))
                            element.on.click {
                                browser.callJsFunction("redirect({})", "/".json)
                            }
                        }.classes("navbar-item")
                        span {
                            element.setAttributes("data-target" to JsonPrimitive("navbarMenuHeroC"))
                            span {  }
                            span {  }
                            span {  }
                            span {  }
                        }.classes("navbar-burger")
                    }.classes("navbar-brand")
                    div {
                        element.classes("navbar-menu")
                        div {
                            a {
                                element.on.click {
                                    browser.callJsFunction("redirect({})", "/".json)
                                }
                                span {
                                    span {
                                        i().classes("fa-solid fa-house")
                                    }.classes("icon")
                                    span().text("Home")
                                }.classes("icon-text")
                            }.classes("navbar-item is-active")
                            a {
                                element.on.click {
                                    browser.callJsFunction("redirect({})", "/doc".json)
                                }
                                span {
                                    span {
                                        i().classes("fa-solid fa-book")
                                    }.classes("icon")
                                    span().text("Documentation")
                                }.classes("icon-text")
                            }.classes("navbar-item")
                            span {
                                a {
                                    span {
                                        i().classes("fab fa-github")
                                    }.classes("icon")
                                    span().text("Github")
                                    element.href = "https://github.com/KosmicGit/ProyectoASTEFASI"
                                }.classes("button")
                            }.classes("navbar-item")
                        }.classes("navbar-end")
                    }.id = "navbarMenuHeroC"
                }.classes("container")
            }.classes("navbar")
        }.classes("hero-head")

        div {
            div {
                val username = kvar("")
                val email = kvar("")
                val password = kvar("")

                div {
                    element("center") {
                        div {
                            div {
                                div {
                                    //H2 Login
                                    h2().text("📄 Register").classes("title is-4 has-text-white")
                                    br()
                                    //H3 Username
                                    val usernameh3 = h3()
                                    usernameh3.text("👤 Username:")
                                    usernameh3.classes("subtitle")
                                    //Input Username
                                    val userinput = input(type = InputType.text)
                                    userinput.value = username
                                    userinput.classes("input is-small")
                                    br()
                                    br()
                                    //H3 Email
                                    val emailh3 = h3()
                                    emailh3.text("✉️ Email:")
                                    emailh3.classes("subtitle")
                                    //Input Email
                                    val emailinput = input(type = InputType.email)
                                    emailinput.value = email
                                    emailinput.classes("input is-small")
                                    br()
                                    br()
                                    //H3 Password
                                    val passwordh3 = h3()
                                    passwordh3.text("\uD83D\uDD11 Password:")
                                    passwordh3.classes("subtitle")
                                    //Input Password
                                    val passinput = input(type = InputType.password)
                                    passinput.value = password
                                    passinput.classes("input is-small")
                                }

                                br()

                                div {
                                    val botonRegistro = button(type = ButtonType.button)
                                    botonRegistro.text("Registrarse")
                                    botonRegistro.classes("button is-primary")
                                    botonRegistro.on.click {
                                        botonRegistro.classes("button is-link is-loading")
                                        if (username.value == "" || password.value == "" || email.value == "") {
                                            botonRegistro.classes("button is-danger")
                                            botonRegistro.text("Error")
                                            val errorRegistro = h3()
                                            errorRegistro.text = KVar("Por favor introduzca sus datos.")
                                        } else {
                                            when (register(username, email, password)) {
                                                0, 2, 3 -> {
                                                    val errorRegistro = h3()
                                                    errorRegistro.text = KVar("Error al registrarse.")
                                                    botonRegistro.classes("button is-danger")
                                                    botonRegistro.text("Error")
                                                }
                                                1 -> {
                                                    val errorRegistro = h3()
                                                    errorRegistro.text = KVar("El usuario ya existe.")
                                                    botonRegistro.classes("button is-danger")
                                                    botonRegistro.text("Error")
                                                }
                                                4 -> {
                                                    browser.callJsFunction("exitoRegistro()")
                                                    browser.callJsFunction("redirect({})", "/register/success".json)
                                                }
                                            }
                                        }
                                    }
                                }
                            }.classes("box")
                        }.classes("column is-half")
                    }
                }
            }.classes("container has-text-centered")
        }.classes("hero-body")

        div {
            nav {
                element.classes("tabs is-boxed is-fullwidth")
                div {
                    element.classes("container")
                    ul {
                        li {
                            a {
                                element.text("Main")
                                element.on.click {
                                    browser.callJsFunction("redirect({})", "/".json)
                                }
                            }
                        }
                        li {
                            a {
                                span {
                                    span {
                                        i().classes("fa-solid fa-right-to-bracket ")
                                    }.classes("icon")
                                    span().text("Login")
                                }.classes("icon-text")
                                element.on.click {
                                    browser.callJsFunction("redirect({})", "/login".json)
                                }
                            }
                        }
                        li {
                            a {
                                span {
                                    span {
                                        i().classes("fa-solid fa-user-plus")
                                    }.classes("icon")
                                    span().text("Register")
                                }.classes("icon-text")
                            }
                        }.classes("is-active")
                    }
                }
            }
        }.classes("hero-foot")
    }.classes("hero is-link is-fullheight")
}

private fun register(user: KVar<String>, email: KVar<String>, passwd: KVar<String>): Int {
    // Devolverá varias salidas dependiendo el error o el éxito
    /*
    0 -> Error Desconocido
    1 -> Usuario ya existente
    2 -> Error al obtener el último ID de la BD
    3 -> Crea el usuario, pero no lo obtiene a la hora de comprobar si se ha añadido
    4 -> Exito
     */

    var idSalida = 0
    var usuario: Usuario? = Gestores.gestorUsuarios.obtenerUsuario(user.value)

    if (usuario == null) {
        val ultimoID = Gestores.gestorUsuarios.obtenerUltimoID()
        if (ultimoID != null) {
            val nuevoRegistro = Usuario(ultimoID, user.value, email.value, passwd.value, Roles.PACIENTE)
            Gestores.gestorUsuarios.addUsuario(nuevoRegistro)
            if (Gestores.gestorUsuarios.obtenerUsuario(user.value) == null) {
                idSalida = 3
            } else {
                idSalida = 4
            }
        } else {
            idSalida = 2
        }
    } else {
        if (usuario.email == email.value || usuario.username == user.value) {
            idSalida = 1
        }
    }
    return idSalida
}
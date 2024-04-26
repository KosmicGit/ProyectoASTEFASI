package es.cifpvirgen.Paginas.Register

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.Inputs.InputsUsuario
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
                                browser.url.value = "/"
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
                                    browser.url.value = "/"
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
                                    browser.url.value = "/doc"
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
                    div {
                        div {
                            div {
                                //H2 Login
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-address-card")
                                        }.classes("icon")
                                        span().text("Sign up")
                                    }.classes("icon-text")
                                }.classes("title is-4 has-text-white")
                                br()
                                //H3 Username
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-user")
                                        }.classes("icon")
                                        span().text("Username:")
                                    }.classes("icon-text")
                                }.classes("subtitle")
                                //Input Username
                                val userinput = input(type = InputType.text)
                                userinput.value = username
                                userinput.classes("input is-small")
                                userinput.on.input {
                                    userinput.on.focusout {
                                        if (username.value == "") {
                                            userinput.classes("input is-small is-danger")
                                            userinput.on.focusin {
                                                userinput.classes("input is-small")
                                            }
                                        } else {
                                            userinput.classes("input is-small is-success")
                                        }
                                    }
                                }
                                br()
                                br()
                                //H3 Email
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-envelope")
                                        }.classes("icon")
                                        span().text("Email:")
                                    }.classes("icon-text")
                                }.classes("subtitle")
                                //Input Email
                                val emailinput = input(type = InputType.email)
                                emailinput.value = email
                                emailinput.classes("input is-small")
                                emailinput.on.input {
                                    emailinput.on.focusout {
                                        if (!InputsUsuario.comprobarEmail(email.value)) {
                                            emailinput.classes("input is-small is-danger")
                                            emailinput.on.focusin {
                                                emailinput.classes("input is-small")
                                            }
                                        } else {
                                            emailinput.classes("input is-small is-success")
                                        }
                                    }
                                }
                                br()
                                br()
                                //H3 Password
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-key")
                                        }.classes("icon")
                                        span().text("Password:")
                                    }.classes("icon-text")
                                }.classes("subtitle")
                                //Input Password
                                val passinput = input(type = InputType.password)
                                passinput.value = password
                                passinput.classes("input is-small")
                                passinput.on.input {
                                    passinput.on.focusout {
                                        if (password.value == "") {
                                            passinput.classes("input is-small is-danger")
                                            passinput.on.focusin {
                                                passinput.classes("input is-small")
                                            }
                                        } else {
                                            passinput.classes("input is-small is-success")
                                        }
                                    }
                                }
                            }

                            br()

                            div {
                                var botonRegistro = button(type = ButtonType.button) {
                                    span { i().classes("fa-solid fa-user-plus") }.classes("icon")
                                    span().text("Registrarse")
                                }.classes("button is-warning")
                                botonRegistro.on.click {
                                    botonRegistro.classes("button is-link is-loading")
                                    br()
                                    if (username.value == "" || password.value == "" || email.value == "" || !InputsUsuario.comprobarEmail(email.value)) {
                                        botonRegistro.classes("button is-danger")
                                        botonRegistro.text("Error")
                                        browser.callJsFunction("mostrarNoti({})", "Debes rellenar todos los campos".json)
                                    } else {
                                        when (register(username, email, password)) {
                                            0, 2, 3 -> {
                                                browser.callJsFunction("mostrarNoti({})", "Error al registrarse".json)
                                                botonRegistro.classes("button is-danger")
                                                botonRegistro.text("Error")
                                                botonRegistro.on.mouseleave { element.classes("button is-primary") }
                                            }
                                            1 -> {
                                                browser.callJsFunction("mostrarNoti({})", "El usuario ya existe".json)
                                                botonRegistro.classes("button is-danger")
                                                botonRegistro.text("Error")
                                            }
                                            4 -> {
                                                val idUsuario = Gestores.gestorUsuarios.obtenerUsuarioMail(email.value)!!.idUsuario
                                                val userKey = Gestores.encriptarUsuario(Usuario( idUsuario, username.value, email.value, password.value, Roles.PACIENTE, false))
                                                browser.callJsFunction("exitoRegistro({})", userKey.json)
                                                browser.callJsFunction("mostrarNoti({})", "Usuario registrado correctamente, compruebe la bandeja de su correo".json)
                                                Gestores.gestorMail.enviarCorreo(email.value, "AsTeFaSi: Active su cuenta.", "<img src='https://i.ibb.co/ysYXs7D/logo3.png' width='300'><hr>" +
                                                        "<h1>Bienvenido a bordo ${username.value}!</h1>" + "<p>Utilice el siguiente enlace para activar su cuenta:</p>" + "<a href='http://localhost:8080/register/activate/${Gestores.codificarURL(userKey)}'>Activa tu cuenta aquí</a>" +
                                                        "<p>Recuerde que es necesario realizar la activación de la cuenta para poder descargar y acceder a la Aplicación.</p>")
                                                browser.url.value = "/register/success"
                                            }
                                        }
                                    }
                                }
                            }
                        }.classes("box")
                    }.classes("column is-half")
                }.classes("columns is-centered has-text-center")
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
                                span {
                                    span {
                                        i().classes("fa-solid fa-flag-checkered")
                                    }.classes("icon")
                                    span().text("Start")
                                }.classes("icon-text")
                                element.on.click {
                                    browser.url.value = "/"
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
                                    browser.url.value = "/login"
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
            val nuevoRegistro = Usuario(ultimoID + 1, user.value, email.value, passwd.value, Roles.PACIENTE, false)
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
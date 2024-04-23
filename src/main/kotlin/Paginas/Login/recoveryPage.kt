package es.cifpvirgen.Paginas.Login

import es.cifpvirgen.Gestion.Gestores
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.util.json

fun Component.recoveryPage() {
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
                val email = kvar("")

                div {
                    div {
                        div {
                            p {
                                span {
                                    span {
                                        i().classes("fa-solid fa-user-lock")
                                    }.classes("icon")
                                    span().text(" ")
                                    span().text("Recover account")
                                }.classes("icon-text")
                            }.classes("title")
                            element("hr")
                            p { element.text("Vaya, parece que has olvidado tu contraseña.") }.classes("subtitle")
                            p().text("Introduzca su correo a continuación para que le mandemos")
                            p().text("un enlace en el cual poder restablecer su contraseña.")
                            br()
                            p {
                                span {
                                    span {
                                        i().classes("fa-solid fa-envelope")
                                    }.classes("icon")
                                    span().text("Email:")
                                }.classes("icon-text")
                            }.classes("subtitle")
                            val emailinput = input(type = InputType.email)
                            emailinput.value = email
                            emailinput.classes("input is-normal")
                            br()
                            br()
                            var botonrecovery = button(type = ButtonType.button) {
                                span { i().classes("fa-solid fa-share-from-square") }.classes("icon")
                                var textoBoton = span().text("Enviar")
                            }.classes("button is-warning")
                            botonrecovery.on.click {
                                botonrecovery.classes("button is-warning is-loading")

                                if (email.value == "") {
                                    if (botonrecovery.text.value != "Error") {
                                        botonrecovery.text("Error")
                                        botonrecovery.classes("button is-danger")
                                        br()
                                        p().text("El campo no puede estar vacío.").classes("has-text-danger")
                                    }
                                } else {
                                    val usuario = Gestores.gestorUsuarios.obtenerUsuarioMail(email.value)
                                    if (usuario == null) {
                                        botonrecovery.text("Error")
                                        botonrecovery.classes("button is-danger")
                                        br()
                                        p().text("No existe ningún usuario con este correo.").classes("has-text-danger")

                                    } else {
                                        Gestores.gestorMail.enviarCorreo(
                                            email.value,
                                            "AsTeFaSi: Restablece su contraseña.",
                                            "<img src='https://i.ibb.co/ysYXs7D/logo3.png' width='300'><hr>" +
                                                    "<h1>Restablezca su contraseña.</h1>" + "<p>Hola ${usuario.username}." + "<p>Utilice el siguiente enlace para restablecer su contraseña:</p>" + "<a href='http://localhost:8080/login/recovery/${
                                                Gestores.codificarURL(
                                                    Gestores.encriptarUsuario(usuario)
                                                )
                                            }'>Restablece tu contraseña aquí</a>" +
                                                    "<p>Si usted no ha solicitado el cambio de contraseña, ignore este correo.</p>"
                                        )
                                        botonrecovery.text("Enviado")
                                        botonrecovery.classes("button is-primary")
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
                        }.classes("is-active")
                        li {
                            a {
                                span {
                                    span {
                                        i().classes("fa-solid fa-user-plus")
                                    }.classes("icon")
                                    span().text("Register")
                                }.classes("icon-text")
                                element.on.click {
                                    browser.url.value = "/register"
                                }
                            }
                        }
                    }
                }
            }
        }.classes("hero-foot")
    }.classes("hero is-link is-fullheight")
}
package es.cifpvirgen.Paginas.Login

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.recoveryPanel(usuario: Usuario) {
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
                div {
                    div {
                        div {
                            if (usuario.password == Gestores.gestorUsuarios.obtenerUsuario(usuario.username)!!.password) {
                                val password = KVar("")

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
                                div {
                                    div {
                                        li().text("Usuario: ${usuario.username}")
                                        li().text("Correo: ${usuario.email}")
                                    }.classes("columna is-9 has-text-left")
                                    div { }.classes("column is-2")
                                    div {
                                        val fotoPerfil = Gestores.gestorUsuarios.obtenerFoto(usuario)
                                        if (fotoPerfil != null) {
                                            element("figure") {
                                                img(attributes = mapOf("src" to JsonPrimitive("data:image/png;base64,$fotoPerfil")))
                                            }.classes("image is-128x128")
                                        } else {
                                            element("figure") {
                                                img(attributes = mapOf("src" to JsonPrimitive("https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/512x512/plain/user.png")))
                                            }.classes("image is-128x128")
                                        }
                                    }.classes("columna")
                                }.classes("columns is-centered is-vcentered")
                                element("hr")
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-key")
                                        }.classes("icon")
                                        span().text(" ")
                                        span().text("Introduzca su nueva contraseña")
                                    }.classes("icon-text")
                                }
                                br()
                                //Input Password
                                val passinput = input(type = InputType.password)
                                passinput.value = password
                                passinput.classes("input is-normal")
                                br()
                                br()
                                var botonPassword = button(type = ButtonType.button) {
                                    span { i().classes("fa-solid fa-pen-to-square") }.classes("icon")
                                    span().text("Cambiar")
                                }.classes("button is-danger")
                                botonPassword.on.click {
                                    botonPassword.classes("button is-danger is-loading")
                                    if (password.value == "") {
                                        botonPassword.text("Error")
                                        browser.callJsFunction("mostrarNoti({})", "Debes introducir una contraseña".json)
                                        botonPassword.classes("button is-danger")
                                    } else {
                                        val modificacion = usuario.copy()
                                        modificacion.password = password.value
                                        Gestores.gestorUsuarios.modificarUsuario(usuario, modificacion)
                                        browser.url.value = "/login"
                                    }
                                }
                            } else {
                                p { element.text("Este enlace ya no es válido") }.classes("title")
                                element("hr")
                                p { element.text("Parece que ya has cambiado tu contraseña.") }
                                p { element.text("Si ha sido un error, vuelva a solicitar un") }
                                p { element.text("nuevo cambio de contraseña.") }
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
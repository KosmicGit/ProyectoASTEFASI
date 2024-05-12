package es.cifpvirgen.Paginas.Admin.Settings

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.Inputs.InputsUsuario
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.emailEdit(usuario: Usuario) {
    elementScope().launch {
        val comprobarCookie = CookieReceiver(browser).getString("sesion")!!
        val usercheck = es.cifpvirgen.Gestion.Gestores.desencriptarUsuario(comprobarCookie)

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
                            }.classes("navbar-item is-selected")
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
                                    element.on.mouseenter {
                                        element.classes("navbar-item")
                                    }
                                    element.on.mouseleave {
                                        element.classes("navbar-item has-text-black")
                                    }
                                    span {
                                        span {
                                            i().classes("fa-solid fa-house")
                                        }.classes("icon")
                                        span().text("Home")
                                    }.classes("icon-text")
                                }.classes("navbar-item has-text-black")
                                a {
                                    element.on.click {
                                        browser.url.value = "/doc"
                                    }
                                    element.on.mouseenter {
                                        element.classes("navbar-item")
                                    }
                                    element.on.mouseleave {
                                        element.classes("navbar-item has-text-black")
                                    }
                                    span {
                                        span {
                                            i().classes("fa-solid fa-book")
                                        }.classes("icon")
                                        span().text("Documentation")
                                    }.classes("icon-text")
                                }.classes("navbar-item has-text-black")
                                span {
                                    a {
                                        span {
                                            i().classes("fab fa-github")
                                        }.classes("icon")
                                        span().text("Github")
                                        element.href = "https://github.com/KosmicGit/ProyectoASTEFASI"
                                    }.classes("button")
                                }.classes("navbar-item")
                                span {
                                    a {
                                        span {
                                            i().classes("fa-solid fa-user-gear")
                                        }.classes("icon")
                                        span().text(usercheck.username)
                                        element.on.click {
                                            browser.url.value = "/profile/settings"
                                        }
                                    }.classes("button is-info is-inverted")
                                }.classes("navbar-item is-active")
                                span {
                                    a {
                                        span {
                                            i().classes("fa-solid fa-right-from-bracket")
                                        }.classes("icon")
                                        span().text("Log out")
                                        element.on.click {
                                            browser.callJsFunction("cerrarSesion({})", "sesion".json)
                                            browser.url.value = "/"
                                        }
                                    }.classes("button is-danger is-inverted")
                                }.classes("navbar-item")
                            }.classes("navbar-end")
                        }.id = "navbarMenuHeroC"
                    }.classes("container")
                }.classes("navbar is-black")
            }.classes("hero-head")

            div {
                div {
                    div {
                        div {
                            div {
                                val newMail = KVar("")
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-envelopes-bulk")
                                        }.classes("icon")
                                        span().text(" ")
                                        span().text("Mail Settings")
                                    }.classes("icon-text")
                                }.classes("title has-text-white")
                                element("hr")
                                p { element.text("Introduzca el nuevo correo electronico.") }
                                br()
                                input(type = InputType.text) {
                                    element.classes("input is-normal")
                                    element.value = newMail
                                    element.on.input {
                                        element.on.focusout {
                                            if (!InputsUsuario.comprobarEmail(newMail.value)) {
                                                element.classes("input is-danger")
                                                element.on.focusin {
                                                    element.classes("input")
                                                }
                                            } else {
                                                element.classes("input is-success")
                                            }
                                        }
                                    }
                                }
                                br()
                                br()
                                div {
                                    div {
                                        button {
                                            span {
                                                i().classes("fa-solid fa-envelope")
                                            }.classes("icon is-small")
                                            span().text("Change")
                                            element.on.click {
                                                if (newMail.value == "" || !InputsUsuario.comprobarEmail(newMail.value)) {
                                                    browser.callJsFunction("mostrarNoti({})", "Introduzca una dirección de correo".json)
                                                } else {
                                                    if (Gestores.gestorUsuarios.obtenerUsuarioMail(newMail.value) == null) {
                                                        val modificacion = usuario.copy()
                                                        modificacion.email = newMail.value
                                                        Gestores.gestorUsuarios.modificarUsuario(usuario, modificacion)
                                                        browser.callJsFunction("mostrarNoti({})", "Usuario actualizado correctamente.".json)
                                                        browser.url.value = "/admin/info" + Gestores.codificarURL(Gestores.encriptarUsuario(modificacion))
                                                    } else {
                                                        browser.callJsFunction("mostrarNoti({})", "El usuario ya existe.".json)
                                                    }
                                                }
                                            }
                                        }.classes("button is-warning is-inverted")
                                    }.classes("column")
                                }.classes("colums is-centered")
                            }.classes("box")
                        }.classes("column is-half")
                    }.classes("columns is-centered has-text-center")
                }.classes("container has-text-centered hax-text-white")
            }.classes("hero-body")

            div {
                nav {
                    div {
                        element.classes("container")
                        ul {
                            li {
                                a {
                                    val iconos = span {
                                        element.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        span {
                                            i().classes("fa-solid fa-user")
                                        }.classes("icon")
                                        span().text("Profile")
                                    }.classes("icon-text")
                                    element.on.click {
                                        browser.url.value = "/profile"
                                    }
                                    element.on.mouseenter {
                                        iconos.setAttributes(Pair("style", JsonPrimitive("color: White")))
                                        element.on.mouseleave {
                                            iconos.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        }
                                    }
                                }
                            }
                            li {
                                a {
                                    val iconos = span {
                                        element.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        span {
                                            i().classes("fa-solid fa-bars-progress")
                                        }.classes("icon")
                                        span().text("Downloads")
                                    }.classes("icon-text")
                                    element.on.click {
                                        browser.url.value = "/downloads"
                                    }
                                    element.on.mouseenter {
                                        iconos.setAttributes(Pair("style", JsonPrimitive("color: White")))
                                        element.on.mouseleave {
                                            iconos.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        }
                                    }
                                }
                            }
                            li {
                                a {
                                    span {
                                        element.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        span {
                                            i().classes("fa-solid fa-users-gear")
                                        }.classes("icon")
                                        span().text("Admin")
                                    }.classes("icon-text")
                                    element.on.click {
                                        browser.url.value = "/admin"
                                    }
                                }
                            }.classes("is-active")
                        }
                    }
                }.classes("tabs is-boxed is-fullwidth")
            }.classes("hero-foot")
        }.classes("hero is-text is-fullheight")
    }
}
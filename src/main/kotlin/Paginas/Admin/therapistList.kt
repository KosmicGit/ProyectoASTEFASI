package es.cifpvirgen.Paginas.Admin

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.Inputs.InputsUsuario
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.therapistList(usuario: Usuario) {
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
                                    span().text(usuario.username)
                                    element.on.click {
                                        browser.url.value = "/profile/settings"
                                    }
                                }.classes("button is-info is-inverted")
                            }.classes("navbar-item")
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
                            val mailUsuario = KVar("")
                            nav {
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-screwdriver-wrench")
                                        }.classes("icon")
                                        span().text("Admin Panel")
                                    }.classes("icon-text")
                                }.classes("panel-heading")
                                div {
                                    p {
                                        input(type = InputType.text) {
                                            element.setAttributes(Pair("placeholder", JsonPrimitive("Correo del usuario")))
                                            element.value = mailUsuario
                                            element.on.input {
                                                element.on.focusout {
                                                    if (!InputsUsuario.comprobarEmail(mailUsuario.value)) {
                                                        element.classes("input is-small is-danger")
                                                        element.on.focusin {
                                                            element.classes("input is-small")
                                                        }
                                                    } else {
                                                        element.classes("input is-small is-success")
                                                    }
                                                }
                                            }
                                        }.classes("input")
                                        span {
                                            i { element.setAttributes(Pair("aria-hidden", JsonPrimitive("true"))) }.classes("fas fa-search")
                                        }.classes("icon is-left")
                                    }.classes("control has-icons-left")
                                    a{}
                                    button {
                                        element.text("Editar")
                                        element.on.click {
                                            val usuarioBuscado = Gestores.gestorUsuarios.obtenerUsuarioMail(mailUsuario.value)
                                            if (mailUsuario.value == "") {
                                                browser.callJsFunction("mostrarNoti({})", "Introduce un correo válido".json)
                                            } else if (usuarioBuscado == null) {
                                                browser.callJsFunction("mostrarNoti({})", "Usuario no encontrado. Introduce un usuario válido".json)
                                            } else {
                                                browser.url.value = "/admin/info/" + Gestores.codificarURL(Gestores.encriptarUsuario(usuarioBuscado))
                                            }
                                        }
                                    }.classes("button is-warning")
                                }.classes("panel-block")

                                p {
                                    a {
                                        element.text("Todo")
                                        element.on.click {
                                            browser.url.value = "/admin"
                                        }
                                    }
                                    a {
                                        element.text("Administradores")
                                        element.on.click {
                                            browser.url.value = "/admin/admins"
                                        }
                                    }
                                    a { element.text("Terapeutas") }.classes("is-active")
                                    a {
                                        element.text("Pacientes")
                                        element.on.click {
                                            browser.url.value = "/admin/clients"
                                        }
                                    }
                                }.classes("panel-tabs")
                                val usuarios = Gestores.gestorUsuarios.obtenerUsuarios()
                                for (i in usuarios) {
                                    if (i.rol == Roles.TERAPEUTA) {
                                        div {
                                            li().text("ID: " + i.idUsuario.toString())
                                            element.addText("  | ")
                                            element.addText("Username: " + i.username)
                                            element.addText(" | ")
                                            element.addText("Email: " + i.email)
                                        }.classes("panel-block")
                                    }
                                }
                            }.classes("panel is-link")
                        }.classes("box")
                    }.classes("column is-three-fifths")
                }.classes("columns is-centered")
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


package es.cifpvirgen.Paginas.Profile.Settings

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.passwordSettings(usuario: Usuario) {
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
                            val newPasswd = KVar("")
                            p {
                                span {
                                    span {
                                        i().classes("fa-solid fa-lock")
                                    }.classes("icon")
                                    span().text(" ")
                                    span().text("Password Settings")
                                }.classes("icon-text")
                            }.classes("title has-text-white")
                            element("hr")
                            p { element.text("Introduzca su nueva contraseña.") }
                            br()
                            input(type = InputType.password) {
                                element.classes("input is-normal")
                                element.value = newPasswd
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
                                            if (newPasswd.value == ""){
                                                p { element.text("Introduzca una contraseña") }.classes("subtitle has-text-grey-light")
                                            } else {
                                                if (usuario.password == newPasswd.value) {
                                                    p { element.text("Has introducido tu contraseña actual") }.classes("subtitle has-text-grey-light")
                                                } else {
                                                    val modificacion = usuario.copy()
                                                    modificacion.email = newPasswd.value
                                                    Gestores.gestorUsuarios.modificarUsuario(usuario, modificacion)
                                                    browser.callJsFunction("cerrarSesion({})", "sesion".json)
                                                    browser.callJsFunction("guardarCookie({})", Gestores.encriptarUsuario(modificacion).json)
                                                    browser.callJsFunction("mostrarNoti({})", "Contraseña actualizada correctamente.".json)
                                                    browser.url.value = "/profile/settings"
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
                                span {
                                    span {
                                        i().classes("fa-solid fa-user")
                                    }.classes("icon")
                                    span().text("Profile")
                                }.classes("icon-text")
                                element.on.click {
                                    browser.url.value = "/profile"
                                }
                            }
                        }
                        li {
                            a {
                                span {
                                    span {
                                        i().classes("fa-solid fa-bars-progress")
                                    }.classes("icon")
                                    span().text("Downloads")
                                }.classes("icon-text")
                                element.on.click {
                                    browser.url.value = "/downloads"
                                }
                            }
                        }
                        if (usuario.rol == Roles.ADMINISTRADOR) {
                            li {
                                a {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-users-gear")
                                        }.classes("icon")
                                        span().text("Admin")
                                    }.classes("icon-text")
                                    element.on.click {
                                        browser.url.value = "/admin"
                                    }
                                }
                            }
                        }
                    }
                }
            }.classes("tabs is-boxed is-fullwidth")
        }.classes("hero-foot")
    }.classes("hero is-info is-fullheight")
}
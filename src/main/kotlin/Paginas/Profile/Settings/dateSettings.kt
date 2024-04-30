package es.cifpvirgen.Paginas.Profile.Settings

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Inputs.Gestores
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.util.json

fun Component.dateSettings(usuario: Usuario) {
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
                            p {
                                span {
                                    span {
                                        i().classes("fa-solid fa-gear")
                                    }.classes("icon")
                                    span().text(" ")
                                    span().text("Profile Settings")
                                }.classes("icon-text")
                            }.classes("title has-text-white")
                            element("hr")
                            div {
                                div {
                                    li().text("Usuario: ${usuario.username}")
                                    li().text("Correo: ${usuario.email}")
                                    li().text("Rol: ${usuario.rol}")
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
                            div {
                                div {
                                    p {
                                        button {
                                            span {
                                                i().classes("fa-solid fa-image")
                                            }.classes("icon is-small")
                                            span().text("Cambiar Foto")
                                            element.on.click {
                                                browser.url.value = "/profile/settings/image"
                                            }
                                        }.classes("button is-success")
                                        button {
                                            span {
                                                i().classes("fa-solid fa-user-pen")
                                            }.classes("icon is-small")
                                            span().text("Cambiar Usuario")
                                            element.on.click {
                                                browser.url.value = "/profile/settings/user"
                                            }
                                        }.classes("button is-primary")
                                        button {
                                            span {
                                                i().classes("fa-solid fa-calendar-days")
                                            }.classes("icon is-small")
                                            span().text("Cambiar Fecha")
                                            element.on.click {
                                                browser.url.value = "/profile/settings/date"
                                            }
                                        }.classes("button is-info")
                                        button {
                                            span {
                                                i().classes("fa-solid fa-envelope")
                                            }.classes("icon is-small")
                                            span().text("Cambiar Correo")
                                            element.on.click {
                                                browser.url.value = "/profile/settings/email"
                                            }
                                        }.classes("button is-warning")
                                        button {
                                            span {
                                                i().classes("fa-solid fa-key")
                                            }.classes("icon is-small")
                                            span().text("Cambiar Contraseña")
                                            element.on.click {
                                                browser.url.value = "/profile/settings/password"
                                            }
                                        }.classes("button is-danger")
                                    }.classes("buttons is-centered")
                                }.classes("column")
                            }.classes("columns")
                            element("hr")
                            div {
                                div {
                                    button {
                                        span {
                                            i().classes("fa-solid fa-trash")
                                        }.classes("icon is-small")
                                        span().text("Eliminar Cuenta")
                                    }.classes("button is-danger is-inverted")
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
                        if (usuario.rol == es.cifpvirgen.Data.Roles.ADMINISTRADOR) {
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
                        if (usuario.rol == Roles.TERAPEUTA) {
                            li {
                                a {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-user-doctor")
                                        }.classes("icon")
                                        span().text("Patients")
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
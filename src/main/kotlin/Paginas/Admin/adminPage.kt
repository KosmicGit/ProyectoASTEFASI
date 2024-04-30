package es.cifpvirgen.Paginas.Admin

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.adminPage(usuario: Usuario) {
    //TODO("Añadir archivos reales")
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
                            val idUsuario = KVar("")
                            p { element.text("Introduzca un usuario a buscar") }.classes("title")
                            input(type = InputType.text) {
                                element.value = idUsuario
                                element.on.input {
                                    element.on.focusout {
                                        if (element.value.value == "") {
                                            element.classes("input is-danger")
                                        } else {
                                            element.classes("input")
                                        }
                                    }
                                }
                            }.classes("input")
                            element("hr")
                            p { element.text("Todos los usuarios") }.classes("subtitle")
                            div {
                                element.id = "modal-js-example"
                                div {
                                    div {
                                        element("header") {
                                            p { element.text("Todos los usuarios") }.classes("moda-card-title")
                                            button { } }.classes("delete")
                                        }.classes("modal-card-head")
                                        section {
                                            p { element.text("holaa") }
                                        }.classes("modal-card-body")
                                    }.classes("modal-card")
                                }.classes("modal-background")
                            }.classes("modal")
                            button {
                                element.text("Mostar usuarios")
                                element.setAttributes("data-target" to JsonPrimitive("modal-js-example"))
                            }.classes("js-modal-trigger")
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
                            }.classes("is-active")
                        }
                    }
                }
            }.classes("tabs is-boxed is-fullwidth")
        }.classes("hero-foot")
    }.classes("hero is-text is-fullheight")
}
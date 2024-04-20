package es.cifpvirgen.Paginas.Register

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.util.json

fun Component.registerVerify(usuario: Usuario) {
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
                div {
                    element("center") {
                        div {
                            div {
                                if (Gestores.gestorUsuarios.estaVerificado(usuario)) {
                                    p {
                                        element.text("Esta cuenta ya está activada.")
                                    }.classes("title")
                                    element("hr")
                                    p { element.text("La cuenta del usuario ${usuario.username} enlazada a") }
                                    p { element.text("la direccion de correo "); element("u") { element.text(usuario.email) } }
                                    p { element.text("ya está activa y no hace falta volver a activarla.") }
                                } else {
                                    p {
                                        element.text("¡Cuenta activada!")
                                    }.classes("title")
                                    element("hr")
                                    p { element.text("Ya puede iniciar sesión y acceder a las descargas, también") }
                                    p { element.text("puedes acceder al programa mediante su usuario y contraseña.") }
                                    br()
                                    p { element.text("Nos alegra tenerte con nosotros ${usuario.username}.") }.classes("subtitle")
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
package es.cifpvirgen.Paginas.Register

import es.cifpvirgen.Gestion.Gestores
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.util.json

fun Component.registerSuccess() {
    elementScope().launch {
        val result = (browser.callJsFunctionWithResult("return obtenerSuccess()")).toString().replace("\"", "")
        val usuario = Gestores.desencriptarUsuario(result)
        browser.callJsFunction("cerrarSesion({})", "success".json)

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
                                p {
                                    element.text("Bienvenido a bordo ${usuario.username}!.")
                                }.classes("title")
                                element("hr")
                                p { element.text("¿Ahora que?") }.classes("subtitle is-5")
                                p { element.text("Revise en la bandeja de su correo electónico "); element("u") { element.text(usuario.email) } }
                                p { element.text("en busca del correo que le hemos enviado para activar su cuenta") }
                                br()
                                p { element.text("Recuerde:") }.classes("subtitle is-5")
                                p { element.text("No podrá acceder al programa ni a las descargas hasta que no verifique su cuenta.") }
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
                                    element.on.click {
                                        browser.url.value = "/register"
                                    }
                                }
                            }.classes("is-active")
                        }
                    }
                }
            }.classes("hero-foot")
        }.classes("hero is-link is-fullheight")
    }
}
package es.cifpvirgen.Paginas.Documentation.Categories

import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component

fun Component.usedocPage() {
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
                                span {
                                    span {
                                        i().classes("fa-solid fa-book")
                                    }.classes("icon")
                                    span().text("Documentation")
                                }.classes("icon-text")
                            }.classes("navbar-item is-active")
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
            }.classes("navbar is-black")
        }.classes("hero-head")

        div {
            div {
                div {
                    p { element.text("Uso básico")}.classes("title has-text-white")
                    element("hr")
                    div {
                        div {
                            li().text("AsTeFaSi Sessions: Aplicación de gestion de sesiones terapeuticas.")
                            li().text("AsTeFaSi DatMaker: Herramienta que encripta credenciales de Servidor JavaMail o SqlServer en un archivo DAT.")
                        }.classes("column is-8 has-text-left")
                    }.classes("columns is-centered")
                }.classes("box")
            }.classes("container has-text-centered hax-text-white")
        }.classes("hero-body")

        div {
            nav {
                element.classes("tabs is-boxed is-fullwidth")
                div {
                    element.classes("container")
                    ul {
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc"
                                }
                            }.text("Introducción")
                        }
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/install"
                                }
                            }.text("Instalación")
                        }
                        li { a().text("Uso básico") }.classes("is-active")
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/credits"
                                }
                            }.text("Créditos")
                        }
                    }
                }
            }
        }.classes("hero-foot")
    }.classes("hero is-warning is-fullheight")
}
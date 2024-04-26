package es.cifpvirgen.Paginas.Documentation

import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component

fun Component.documentPage() {
    //TODO("Completar Documentación")
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
                    p { element.text("Introducción")}.classes("title has-text-white")
                    element("hr")
                    p { element.text("AsTeFaSi es un proyecto final de curso para 1º DAW.") }
                    br()
                    p { element.text("En él se trata de poner en conocimiento de los docentes, las habilidades adquiridas por los alumnos:") }
                    p { element.text("Desde "); span { element.text("Base de Datos") }.classes("has-text-link"); element.addText((" hasta ")); span { element.text("Administración de Sistemas") }.classes("has-text-primary"); element.addText(", pasando por "); span { element.text("Lenguaje de Marcas") }.classes("has-text-warning"); element.addText((" y por supuesto ")); span { element.text("Programacion") }.classes("has-text-danger"); element.addText(".")}
                    br()
                    p { element.text("Combinamos varias disciplinas y conocimientos en un único proyecto") }
                    p { element.text("con el fin de demostrarnos y demostrar nuestas habilidades al") }
                    p { element.text("realizar un despliegue de tal magnitud.") }
                }.classes("box")
            }.classes("container has-text-centered hax-text-white")
        }.classes("hero-body")

        div {
            nav {
                div {
                    element.classes("container")
                    ul {
                        li { a().text("Introducción") }.classes("is-active")
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/install"
                                }
                            }.text("Instalación")
                        }
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/use"
                                }
                            }.text("Uso básico")
                        }
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/install"
                                }
                            }.text("Overview")
                        }
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/install"
                                }
                            }.text("Overview")
                        }
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/credits"
                                }
                            }.text("Créditos")
                        }
                    }
                }
            }.classes("tabs is-boxed is-fullwidth")
        }.classes("hero-foot")
    }.classes("hero is-warning is-fullheight")
}
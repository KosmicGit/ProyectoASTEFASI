package es.cifpvirgen.Paginas.Documentation.Categories

import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component

fun Component.installdocPage() {
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
                    p { element.text("AsTeFaSi es un proyecto final para 1º DAW.") }
                    p { element.text("En él se trata de poner en conocimiento de los docentes, las habilidades adquiridas por los alumnos.") }
                    p { element.text("Desde Base de Datos hasta Administración de Sistemas, pasando por Lenguajes de Marcas y por supuesto Programación") }
                    p { element.text("\uD83C\uDF08 Empresa de emociones, sentimientos y terapias.\uD83C\uDF24\uFE0F") }
                    p { element.text("\uD83C\uDF08 Empresa de emociones, sentimientos y terapias.\uD83C\uDF24\uFE0F") }
                    p { element.text("\uD83C\uDF08 Empresa de emociones, sentimientos y terapias.\uD83C\uDF24\uFE0F") }
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
                        li { a().text("Instalación") }.classes("is-active")
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/use"
                                }
                            }.text("Uso básico")
                        }
                        li { a().text("Overview") }
                        li { a().text("Overview") }
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
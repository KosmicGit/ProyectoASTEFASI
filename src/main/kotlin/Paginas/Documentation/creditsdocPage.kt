package es.cifpvirgen.Paginas.Documentation

import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.util.json

fun Component.creditsdocPage() {
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
                    p { element.text("Creditos y Agradecimientos") }.classes("title has-text-white")
                    p { element.text("Ha sido posible realizar este proyecto gracias a:") }.classes("subtitle has-text-grey-light")
                    br()
                    div {
                        div {
                            div {
                                div {
                                    p {
                                        li{
                                            element("strong").text("Kweb")
                                            element.addText( ": es un framework de Kotlin que permite desarrollar páginas Web.")
                                        }
                                    }
                                }.classes("column is-9 has-text-left")
                            }.classes("columns is-centered")
                        }.classes("column is-8")
                        div {
                            p {
                                a {
                                    span {
                                        i().classes("fab fa-github")
                                    }.classes("icon")
                                    span().text("Github")
                                    element.href = "https://github.com/kwebio/kweb-core"
                                }.classes("button is-black")
                            }
                        }.classes("column")
                    }.classes("columns is-vcentered")
                    div {
                        div {
                            div {
                                div {
                                    p {
                                        li{
                                            element("strong").text("JavaFX")
                                            element.addText( ": Herramientas y librerias de Java para crear Interfaces Gráficas.")
                                        }
                                    }
                                }.classes("column is-9 has-text-left")
                            }.classes("columns is-centered")
                        }.classes("column is-8")
                        div {
                            p {
                                a {
                                    span {
                                        i().classes("fa-brands fa-java")
                                    }.classes("icon")
                                    span().text("JavaFX")
                                    element.href = "https://openjfx.io/"
                                }.classes("button is-warning is-inverted")
                            }
                        }.classes("column")
                    }.classes("columns is-vcentered")
                    div {
                        div {
                            div {
                                div {
                                    p {
                                        li{
                                            element("strong").text("MySQL")
                                            element.addText( ": Sistema de gestión de bases de datos de código abierto ampliamente utilizado.")
                                        }
                                    }
                                }.classes("column is-9 has-text-left")
                            }.classes("columns is-centered")
                        }.classes("column is-8")
                        div {
                            p {
                                a {
                                    span {
                                        i().classes("fa-solid fa-database")
                                    }.classes("icon")
                                    span().text("MySQL")
                                    element.href = "https://www.mysql.com/"
                                }.classes("button is-info is-inverted")
                            }
                        }.classes("column")
                    }.classes("columns is-vcentered")
                    div {
                        div {
                            div {
                                div {
                                    p {
                                        li{
                                            element("strong").text("Bulma CSS")
                                            element.addText( ": Framework de CSS moderno y ligero, ofrece una amplia gama de componentes y utilidades.")
                                        }
                                    }
                                }.classes("column is-9 has-text-left")
                            }.classes("columns is-centered")
                        }.classes("column is-8")
                        div {
                            p {
                                a {
                                    span {
                                        i().classes("fa-brands fa-css3-alt")
                                    }.classes("icon")
                                    span().text("Bulma CSS")
                                    element.href = "https://bulma.io/"
                                }.classes("button is-primary is-inverted")
                            }
                        }.classes("column")
                    }.classes("columns is-vcentered")
                    div {
                        div {
                            div {
                                div {
                                    p {
                                        li{
                                            element("strong").text("CIFP Virgen de Gracia")
                                            element.addText( ": Agradecimiento por la formación recibida por parte de este centro, y a los docentes por impartirla.")
                                        }
                                    }
                                }.classes("column is-9 has-text-left")
                            }.classes("columns is-centered")
                        }.classes("column is-8")
                        div {
                            p {
                                a {
                                    span {
                                        i().classes("fa-solid fa-graduation-cap")
                                    }.classes("icon")
                                    span().text("CIFP VdG")
                                    element.href = "https://cifpvirgendegracia.com/"
                                }.classes("button is-danger is-inverted")
                            }
                        }.classes("column")
                    }.classes("columns is-vcentered")
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
                        li {
                            a{
                                element.on.click {
                                    browser.url.value = "/doc/use"
                                }
                            }.text("Uso básico")
                        }
                        li { a().text("Overview") }
                        li { a().text("Overview") }
                        li { a().text("Créditos") }.classes("is-active")
                    }
                }
            }
        }.classes("hero-foot")
    }.classes("hero is-warning is-fullheight")
}
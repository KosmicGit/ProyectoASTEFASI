package es.cifpvirgen.Paginas.Downloads

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.util.json

fun Component.downloadsPage(usuario: Usuario) {
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
                            div {
                                div {
                                    div {
                                        div {  }.classes("column is-0")
                                        div {
                                            p {
                                                li{
                                                    element("strong").text("AsTeFasi Sessions")
                                                    element.addText( ": Gestor de sesiones terapeuticas.")
                                                }
                                            }
                                        }.classes("column")
                                    }.classes("columns has-text-left")
                                    div {
                                        div {
                                            div {
                                                div {
                                                    p {
                                                        a {
                                                            span {
                                                                i().classes("fa-solid fa-download")
                                                            }.classes("icon")
                                                            span().text("Download")
                                                            element.href = "https://rawcdn.githack.com/KosmicGit/ProyectoASTEFASI/fc948926d86f973b4c1507d98f1a14818c310470/DatMaker_Setup.exe"
                                                        }.classes("button is-warning is-inverted")
                                                    }
                                                }.classes("column is-4")
                                                div {
                                                    div {
                                                        div {
                                                            p().text("Version: 1.0")
                                                        }.classes("column")
                                                    }.classes("columns")
                                                    div {
                                                        div {
                                                            p {
                                                                element.text("MD5 (")
                                                                element("u").text("b02a6dad79aa82b8528958407c41a1a8")
                                                                element.addText(")")
                                                            }
                                                        }.classes("colum")
                                                    }.classes("columns")
                                                }.classes("column")
                                            }.classes("columns is-vcentered has-text-left")
                                        }.classes("column")

                                    }.classes("columns has-text-left")
                                }.classes("column is-9")
                                div {
                                    element("figure") {
                                        img(attributes = mapOf("src" to JsonPrimitive("https://i.ibb.co/2gHZQfz/download2.png")))
                                    }.classes("image is-128x128")
                                }.classes("column")
                            }.classes("columns is-vcentered")
                            if (usuario.rol == Roles.ADMINISTRADOR) {
                                element("hr")
                                div {
                                    div {
                                        div {
                                            div {  }.classes("column is-0")
                                            div {
                                                p {
                                                    li{
                                                        element("strong").text("DatMaker")
                                                        element.addText( ": Gestor de archivos DAT. ")
                                                        span { element.text("(HERRAMIENTA INTERNA)") }.classes("has-text-warning")
                                                    }
                                                }
                                            }.classes("column")
                                        }.classes("columns has-text-left")
                                        div {
                                            div {
                                                div {
                                                    div {
                                                        p {
                                                            a {
                                                                span {
                                                                    i().classes("fa-solid fa-download")
                                                                }.classes("icon")
                                                                span().text("Download")
                                                                element.href = "https://rawcdn.githack.com/KosmicGit/ProyectoASTEFASI/fc948926d86f973b4c1507d98f1a14818c310470/DatMaker_Setup.exe"
                                                            }.classes("button is-warning is-inverted")
                                                        }
                                                    }.classes("column is-4")
                                                    div {
                                                        div {
                                                            div {
                                                                p().text("Version: 1.0")
                                                            }.classes("column")
                                                        }.classes("columns")
                                                        div {
                                                            div {
                                                                p {
                                                                    element.text("MD5 (")
                                                                    element("u").text("b02a6dad79aa82b8528958407c41a1a8")
                                                                    element.addText(")")
                                                                }
                                                            }.classes("colum")
                                                        }.classes("columns")
                                                    }.classes("column")
                                                }.classes("columns is-vcentered has-text-left")
                                            }.classes("column")

                                        }.classes("columns has-text-left")
                                    }.classes("column is-9")
                                    div {
                                        element("figure") {
                                            img(attributes = mapOf("src" to JsonPrimitive("https://i.ibb.co/KwxRDtt/datmaker.png")))
                                        }.classes("image is-128x128")
                                    }.classes("column")
                                }.classes("columns is-vcentered")
                            }
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
                        }.classes("is-active")
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
    }.classes("hero is-success is-fullheight")
}
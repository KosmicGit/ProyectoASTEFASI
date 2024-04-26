package es.cifpvirgen.Paginas.Profile

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.util.json

fun Component.profilePage(usuario: Usuario) {
    val paciente = Gestores.gestorPacientes.obtenerPacienteIdUsuario(usuario.idUsuario)!!
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
                            p {
                                span {
                                    span {
                                        i().classes("fa-solid fa-address-card")
                                    }.classes("icon")
                                    span().text(" ")
                                    span().text("Profile")
                                }.classes("icon-text")
                            }.classes("title has-text-white")
                            element("hr")
                            div {
                                div {
                                    li().text("Nombre: ${paciente.nombre}")
                                    li().text("Apellidos: ${paciente.apellido}")
                                    li().text("Correo: ${usuario.email}")
                                    li().text("Fecha de Nacimiento: ${Gestores.stringFecha(paciente.fecha_nacimiento)}")
                                    li().text("Tipo: ${usuario.rol}")
                                }.classes("columna is-9 has-text-left")
                                div { }.classes("column is-2")
                                div {
                                    val fotoPerfil = Gestores.gestorUsuarios.obtenerFoto(usuario)
                                    if (fotoPerfil != null) {
                                        element("figure") {
                                            img(attributes = mapOf("src" to JsonPrimitive(fotoPerfil)))
                                        }.classes("image is-128x128")
                                    } else {
                                        element("figure") {
                                            img(attributes = mapOf("src" to JsonPrimitive("https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/512x512/plain/user.png")))
                                        }.classes("image is-128x128")
                                    }
                                }.classes("columna")
                            }.classes("columns is-centered is-vcentered")
                            element("hr")
                            p { element.text("Productos :") }.classes("subtitle has-text-grey-light has-text-left")
                            div {
                                div {  }.classes("column is-0")
                                div {
                                    li{
                                        element("strong").text("AsTeFaSi Sessions")
                                        element.addText( ": Gestor de sesiones terapeuticas.")
                                    }
                                    if (usuario.rol == Roles.ADMINISTRADOR) {
                                        li{
                                            element("strong").text("AsTeFaSi DatMaker")
                                            element.addText( ": Gestor de archivos DAT. ")
                                            span { element.text("(HERRAMIENTA INTERNA)") }.classes("has-text-warning")
                                        }
                                    }
                                }.classes("column")
                            }.classes("columns has-text-left")
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
                            }
                        }.classes("is-active")
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
    }.classes("hero is-danger is-fullheight")

}
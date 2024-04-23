package es.cifpvirgen.Paginas.Profile.Settings

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.html.fileUpload.FileFormInput
import kweb.html.fileUpload.FileUpload
import kweb.state.KVar
import kweb.util.json

fun Component.imageSettings(usuario: Usuario) {
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
                                        i().classes("fa-solid fa-image")
                                    }.classes("icon")
                                    span().text(" ")
                                    span().text("Image Settings")
                                }.classes("icon-text")
                            }.classes("title has-text-white")
                            element("hr")
                            div {
                                var imgB64 = ""
                                var imgName = ""
                                div {  }.classes("column is-0")
                                div {
                                    div {
                                        label {
                                            val inputFile = fileInput(attributes = mapOf("class" to JsonPrimitive("file-input")))
                                            span {
                                                span {
                                                    i().classes("fas fa-upload")
                                                }.classes("file-icon")
                                                span {
                                                    element.text("Elige un archivo")
                                                }.classes("file-label")
                                            }.classes("file-cta")
                                            var nameFileLabel = span { element.text("Ningun archivo seleccionado.") }.classes("file-name")
                                            inputFile.onFileSelect {
                                                inputFile.retrieveFile {
                                                    imgName = it.fileName
                                                    imgB64 = it.base64Content
                                                    nameFileLabel.text.value = imgName
                                                }
                                            }
                                        }.classes("file-label")
                                    }.classes("file has-name")
                                }.classes("column is-9")
                                div {
                                    var botonUpload = button(type = ButtonType.button) {
                                        span { i().classes("fa-solid fa-upload") }.classes("icon")
                                        span().text("Enviar")
                                    }.classes("button is-warning")
                                    botonUpload.on.click {
                                        if (imgB64 == "") {
                                            p { element.text("Por favor seleccione una imagen.") }.classes("subtitle has-text-grey-light")
                                        } else {
                                            Gestores.gestorUsuarios.guardarFoto(imgB64, usuario)
                                            browser.callJsFunction("mostrarNoti({})", "Imagen actualizada correctamente.".json)
                                            browser.url.value = "/profile/settings"
                                        }
                                    }
                                }.classes("column is-2")
                            }.classes("columns is-vcentered")
                            p { element.text("Introduzca una imagen para cambiar su foto de perfil") }.classes("subtitle has-text-grey-light")
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
    }.classes("hero is-info is-fullheight")
}
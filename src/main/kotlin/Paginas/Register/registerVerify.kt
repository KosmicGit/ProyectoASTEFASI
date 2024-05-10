package es.cifpvirgen.Paginas.Register

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Datos.Paciente
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.Inputs.InputsUsuario
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Component.registerVerify(usuario: Usuario) {
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
                            if (Gestores.gestorUsuarios.estaVerificado(usuario)) {
                                p {
                                    element.text("Esta cuenta ya está activada.")
                                }.classes("title")
                                element("hr")
                                p { element.text("La cuenta del usuario ${usuario.username} enlazada a") }
                                p { element.text("la direccion de correo "); element("u") { element.text(usuario.email) } }
                                p { element.text("ya está activa y no hace falta volver a activarla.") }
                            } else {
                                val Nombre = KVar("")
                                val Apellido = KVar("")
                                val FechaNac = KVar("")
                                val DNI = KVar("")

                                p { element.text("¡Antes de Continuar!") }.classes("title")
                                element("hr")
                                p { element.text("Necesitamos sus datos para poder completar su ficha.") }.classes("subtitle is-6 has-text-grey-light")
                                p { element.text("(Necesario para las sesiones)") }.classes("subtitle is-6 has-text-grey-light")
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-user-tag")
                                        }.classes("icon")
                                        span().text("Nombre:")
                                    }.classes("icon-text")
                                }.classes("subtitle")
                                input(type = InputType.text){
                                    element.value = Nombre
                                    element.on.input {
                                        element.on.focusout {
                                            if (Nombre.value == "") {
                                                element.classes("input is-danger")
                                                element.on.focusin {
                                                    element.classes("input")
                                                }
                                            } else {
                                                element.classes("input is-success")
                                            }
                                        }
                                    }
                                }.classes("input")
                                br()
                                br()
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-signature")
                                        }.classes("icon")
                                        span().text("Apellido:")
                                    }.classes("icon-text")
                                }.classes("subtitle")
                                input(type = InputType.text){
                                    element.value = Apellido
                                    element.on.input {
                                        element.on.focusout {
                                            if (Apellido.value == "") {
                                                element.classes("input is-danger")
                                                element.on.focusin {
                                                    element.classes("input")
                                                }
                                            } else {
                                                element.classes("input is-success")
                                            }
                                        }
                                    }
                                }.classes("input")
                                br()
                                br()
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-cake-candles")
                                        }.classes("icon")
                                        span().text("Fecha de Nacimiento:")
                                    }.classes("icon-text")
                                }.classes("subtitle")
                                input(type = InputType.date){
                                    element.value = FechaNac
                                    element.on.input {
                                        element.on.focusout {
                                            if (!InputsUsuario.comprobarFecha(Gestores.parsearFecha(FechaNac.value))) {
                                                element.classes("input is-danger")
                                                element.on.focusin {
                                                    element.classes("input")
                                                }
                                            } else {
                                                element.classes("input is-success")
                                            }
                                        }
                                    }
                                }.classes("input")
                                br()
                                br()
                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-id-card")
                                        }.classes("icon")
                                        span().text("DNI:")
                                    }.classes("icon-text")
                                }.classes("subtitle")
                                input(type = InputType.text){
                                    element.value = DNI
                                    element.on.input {
                                        element.on.focusout {
                                            if (!InputsUsuario.comprobarDNI(DNI.value)) {
                                                element.classes("input is-danger")
                                                element.on.focusin {
                                                    element.classes("input")
                                                }
                                            } else {
                                                element.classes("input is-success")
                                            }
                                        }
                                    }
                                }.classes("input")
                                br()
                                br()
                                button(type = ButtonType.button) {
                                    span { i().classes("fa-solid fa-arrow-right") }.classes("icon")
                                    span().text("Continuar")
                                    element.on.click {
                                        if (Nombre.value == "" || Apellido.value == "" || FechaNac.value == "" || DNI.value == "" || !InputsUsuario.comprobarFecha(Gestores.parsearFecha(FechaNac.value)) || !InputsUsuario.comprobarDNI(DNI.value)) {
                                            element.classes("button is-danger")
                                            element.text("Error")
                                            browser.callJsFunction("mostrarNoti({})", "Debes rellenar todos los campos".json)
                                        } else {
                                            Gestores.gestorUsuarios.verificarUsuario(usuario)
                                            browser.callJsFunction("mostrarNoti({})", "Usuario verificado correctamente.".json)
                                            Gestores.gestorPacientes.addPaciente(Paciente(DNI.value, Nombre.value, Apellido.value, LocalDate.parse(FechaNac.value, DateTimeFormatter.ofPattern("yyyy-MM-dd")), usuario.idUsuario))
                                            browser.url.value = "/login"
                                        }
                                    }
                                }.classes("button is-primary")
                            }
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
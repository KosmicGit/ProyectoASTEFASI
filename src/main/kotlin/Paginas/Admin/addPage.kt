package es.cifpvirgen.Paginas.Admin

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Datos.Paciente
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Gestion.Inputs.InputsUsuario
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Component.addPage(usuario: Usuario) {
    elementScope().launch {
        val comprobarCookie = CookieReceiver(browser).getString("sesion")!!
        val usercheck = Gestores.desencriptarUsuario(comprobarCookie)


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
                                span { }
                                span { }
                                span { }
                                span { }
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
                                        span().text(usercheck.username)
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
                        }.setAttributes(Pair("id", JsonPrimitive("navbarMenuHeroC")))
                    }.classes("container")
                }.classes("navbar is-black")
            }.classes("hero-head")

            div {
                div {
                    div {
                        div {
                            div {
                                val Nombre = KVar("")
                                val Apellido = KVar("")
                                val FechaNac = KVar("")
                                val DNI = KVar("")
                                var ROL = KVar("")
                                val Username = KVar("")
                                val Password = KVar("")

                                p {
                                    span {
                                        span {
                                            i().classes("fa-solid fa-person-circle-plus")
                                        }.classes("icon")
                                        span().text("Add User")
                                    }.classes("icon-text")
                                }.classes("title")
                                element("hr")
                                p { element.text("Usted va a añadir al siguiente usuario con el correo:") }.classes("subtitule has-text-grey-light")
                                div {
                                    div {
                                        li().text(usuario.email)
                                    }.classes("column is-5 has-text-left")
                                }.classes("columns is-centered")
                                element("hr")
                                p { element.text("Rellene la siguiente informarción") }.classes("subtitle is-6")
                                div {
                                    div {
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
                                    }.classes("column")
                                    div {
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
                                    }.classes("column")
                                }.classes("columns is-vcentered")
                                div {
                                    div {
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
                                    }.classes("column")
                                    div {
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
                                    }.classes("column")
                                }.classes("columns is-vcentered")
                                div {
                                    div {
                                        p {
                                            span {
                                                span {
                                                    i().classes("fa-solid fa-id-card")
                                                }.classes("icon")
                                                span().text("Usuario:")
                                            }.classes("icon-text")
                                        }.classes("subtitle")
                                        input(type = InputType.text){
                                            element.value = Username
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
                                    }.classes("column")
                                    div {
                                        p {
                                            span {
                                                span {
                                                    i().classes("fa-solid fa-id-card")
                                                }.classes("icon")
                                                span().text("Contraseña:")
                                            }.classes("icon-text")
                                        }.classes("subtitle")
                                        input(type = InputType.text){
                                            element.value = Username
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
                                    }.classes("column")
                                }.classes("columns is-vcentered")
                                div {
                                    div {
                                        p {
                                            span {
                                                span {
                                                    i().classes("fa-solid fa-id-card")
                                                }.classes("icon")
                                                span().text("Rol:")
                                            }.classes("icon-text")
                                        }.classes("subtitle")
                                        div {
                                            label {
                                                input(type = InputType.radio){
                                                    element.setAttributes(Pair("name", JsonPrimitive("rol")), Pair("value", JsonPrimitive("admin")))
                                                }
                                            }.addText("Administrador")
                                        }
                                        div {
                                            label {
                                                input(type = InputType.radio){
                                                    element.setAttributes(Pair("name", JsonPrimitive("rol")))


                                                }
                                            }.addText("Terapeuta")
                                        }
                                        div {
                                            label {
                                                input(type = InputType.radio){
                                                    element.setAttributes(Pair("name", JsonPrimitive("rol")))


                                                }
                                            }.addText("Paciente")
                                        }
                                    }.classes("column is-3 has-text-left")
                                }.classes("columns is-centered")

                                br()
                                br()
                                button(type = ButtonType.button) {
                                    element.on.click {
                                        h1().text(ROL)
                                    }
                                }.classes("button")
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
                                    val iconos = span {
                                        element.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        span {
                                            i().classes("fa-solid fa-user")
                                        }.classes("icon")
                                        span().text("Profile")
                                    }.classes("icon-text")
                                    element.on.click {
                                        browser.url.value = "/profile"
                                    }
                                    element.on.mouseenter {
                                        iconos.setAttributes(Pair("style", JsonPrimitive("color: White")))
                                        element.on.mouseleave {
                                            iconos.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        }
                                    }
                                }
                            }
                            li {
                                a {
                                    val iconos = span {
                                        element.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        span {
                                            i().classes("fa-solid fa-bars-progress")
                                        }.classes("icon")
                                        span().text("Downloads")
                                    }.classes("icon-text")
                                    element.on.click {
                                        browser.url.value = "/downloads"
                                    }
                                    element.on.mouseenter {
                                        iconos.setAttributes(Pair("style", JsonPrimitive("color: White")))
                                        element.on.mouseleave {
                                            iconos.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        }
                                    }
                                }
                            }
                            li {
                                a {
                                    span {
                                        element.setAttributes(Pair("style", JsonPrimitive("color: Black")))
                                        span {
                                            i().classes("fa-solid fa-users-gear")
                                        }.classes("icon")
                                        span().text("Admin")
                                    }.classes("icon-text")
                                }
                            }.classes("is-active")
                        }
                    }
                }.classes("tabs is-boxed is-fullwidth")
            }.classes("hero-foot")
        }.classes("hero is-text is-fullheight")
    }
}
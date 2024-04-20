package es.cifpvirgen.Paginas.Login

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Gestion.Gestores
import kotlinx.serialization.json.JsonPrimitive
import kweb.*
import kweb.components.Component
import kweb.state.KVar
import kweb.util.json

fun Component.loginPage(){
    section {
        div {
            element("header") {
                div {
                    div {
                        a {
                            img(attributes = mapOf("src" to JsonPrimitive("https://i.ibb.co/F01PkQv/logo.png")))
                            element.on.click {
                                browser.callJsFunction("redirect({})", "/".json)
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
                                    browser.callJsFunction("redirect({})", "/".json)
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
                                    browser.callJsFunction("redirect({})", "/doc".json)
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
                val username = kvar("")
                val password = kvar("")

                div {
                    element("center") {
                        div {
                            div {
                                div {
                                    //H2 Login
                                    h2().text("\uD83D\uDD12 Login").classes("title is-4 has-text-white")
                                    br()
                                    //H3 Username
                                    val usernameh3 = h3()
                                    usernameh3.text("👤 Username:")
                                    usernameh3.classes("subtitle")
                                    //Input Username
                                    val userinput = input(type = InputType.text)
                                    userinput.value = username
                                    userinput.classes("input is-normal")
                                    br()
                                    br()
                                    //H3 Password
                                    val passwordh3 = h3()
                                    passwordh3.text("\uD83D\uDD11 Password:")
                                    passwordh3.classes("subtitle")
                                    //Input Password
                                    val passinput = input(type = InputType.password)
                                    passinput.value = password
                                    passinput.classes("input is-normal")

                                    element("hr")

                                    p().text("¿No estás registrado?")
                                    p {
                                        element.text("Hazlo ")
                                        val registrarse = a()
                                        registrarse.text("aquí")
                                        element.on.click {
                                            browser.callJsFunction("redirect({})", "/register".json)
                                        }
                                    }
                                }

                                br()

                                div {
                                    val botonlogin = button(type = ButtonType.button)
                                    botonlogin.text("Entrar")
                                    botonlogin.classes("button is-primary")
                                    botonlogin.on.click {
                                        botonlogin.classes("button is-link is-loading")
                                        if (username.value == "" || password.value == "") {
                                            botonlogin.classes("button is-danger")
                                            botonlogin.text("Error")
                                            val errorlogin = h3()
                                            errorlogin.text = KVar("Por favor introduzca sus datos.")
                                            errorlogin.classes("eslogan")
                                        } else {
                                            val usuario = login(username, password)
                                            if (usuario != null) {
                                                if (usuario.verificado) {
                                                    browser.callJsFunction("guardarCookie({})", Gestores.encriptarUsuario(usuario).json)
                                                    browser.callJsFunction("redirect({})", "/registrar".json)
                                                } else {
                                                    botonlogin.classes("button is-danger")
                                                    botonlogin.text("Error")
                                                    val errorlogin = h3()
                                                    errorlogin.text = KVar("Usuario no activado. Compruebe su correo")
                                                }

                                            } else {
                                                botonlogin.classes("button is-danger")
                                                botonlogin.text("Error")
                                                val errorlogin = h3()
                                                errorlogin.text = KVar("😢No existe el Usuario o Contraseña.😔")
                                            }
                                        }
                                    }
                                }
                            }.classes("box")
                        }.classes("column is-half")
                    }
                }
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
                                element.text("Main")
                                element.on.click {
                                    browser.callJsFunction("redirect({})", "/".json)
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
                            }
                        }.classes("is-active")
                        li {
                            a {
                                span {
                                    span {
                                        i().classes("fa-solid fa-user-plus")
                                    }.classes("icon")
                                    span().text("Register")
                                }.classes("icon-text")
                                element.on.click {
                                    browser.callJsFunction("redirect({})", "/register".json)
                                }
                            }
                        }
                    }
                }
            }
        }.classes("hero-foot")
    }.classes("hero is-link is-fullheight")
}

private fun login(user: KVar<String>, passwd: KVar<String>): Usuario? {
    var usuario: Usuario?
    usuario = Gestores.gestorUsuarios.obtenerUsuario(user.value)
    if (usuario != null) {
        if (usuario.password != passwd.value) {
            usuario = null
        }
    }
    return usuario
}
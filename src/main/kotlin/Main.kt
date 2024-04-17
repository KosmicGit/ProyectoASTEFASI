package es.cifpvirgen

import es.cifpvirgen.Paginas.Dashboard.dashPage
import es.cifpvirgen.Paginas.Login.loginPage
import es.cifpvirgen.Paginas.checkUser
import es.cifpvirgen.Paginas.NotFound.notfoundPage
import kweb.*
import kweb.plugins.css.CSSPlugin
import kweb.plugins.javascript.JavascriptPlugin


fun main() {
    Kweb(port = 16097, plugins = listOf(CSSPlugin("css", "style.css"), JavascriptPlugin("js", "script.js"))) {
        doc.head {
            title().text("\uD83C\uDF08  Terapia Web. \uD83C\uDF24\uFE0F")
        }
        doc.body {
            route {
                // URL "/"
                path("/") {
                    if (!checkUser()) {
                        url.value = "/login"
                    } else {
                        url.value = "/dashboard"
                    }
                }

                // URL "/login"
                path("/login") {
                    if (checkUser()) {
                        url.value = "/dashboard"
                    } else {
                        loginPage()
                    }
                }

                // URL "/dashboard"
                path("/dashboard") {
                    if (!checkUser()) {
                        url.value = "/login"
                    } else {
                        dashPage()
                    }
                }

                //PARA PRUEBAS
                path("/test") {
                    loginPage()
                }

                notFound {
                    notfoundPage()
                }
            }
        }
    }
}
package es.cifpvirgen

import es.cifpvirgen.Data.Usuario
import es.cifpvirgen.Paginas.Dashboard.dashPage
import es.cifpvirgen.Paginas.Login.loginPage
import es.cifpvirgen.Paginas.checkUser
import es.cifpvirgen.Paginas.NotFound.notfoundPage
import kweb.*
import kweb.plugins.css.CSSPlugin
import kweb.plugins.javascript.JavascriptPlugin
import kweb.state.KVar


fun main() {
    Kweb(port = 16097, plugins = listOf(CSSPlugin("css", "bulma.css"), JavascriptPlugin("js", "script.js"))) {
        doc.head {
            title().text("\uD83C\uDF08  Terapia Web. \uD83C\uDF24\uFE0F")
        }
        doc.body {
            var user: Usuario? = null

            route {
                // URL "/"
                path("/") {

                }

                // URL "/login"
                path("/login") {
                    loginPage()
                }

                // URL "/registrar"
                path("/registrar") {
                    h1().text("HOLAAA")
                }

                // URL "/dashboard"
                path("/dashboard") {
                    if (!checkUser()) {
                        url.value = "/login"
                    } else {
                        dashPage(user)
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
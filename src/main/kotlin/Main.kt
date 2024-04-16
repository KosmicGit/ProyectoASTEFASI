package es.cifpvirgen

import es.cifpvirgen.Pages.Dashboard.dashPage
import es.cifpvirgen.Pages.Login.loginPage
import es.cifpvirgen.Pages.checkUser
import es.cifpvirgen.Paginas.NotFound.notfoundPage
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
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
                    dashPage()
                }

                //PARA PRUEBAS
                path("/test") {

                }

                notFound {
                    notfoundPage()
                }
            }
        }
    }
}
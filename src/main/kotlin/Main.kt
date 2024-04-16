package es.cifpvirgen

import es.cifpvirgen.Pages.Dashboard.dashPage
import es.cifpvirgen.Pages.Login.loginPage
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
                    //url.value = "/login"
                    h1().text("hola")
                }

                // URL "/login"
                path("/login") {
                    loginPage()
                }

                // URL "/dashboard"
                path("/dashboard") {
                    dashPage()
                }

                notFound {
                    h1().text("404 Not Found")
                }
            }
        }
    }
}
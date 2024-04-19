package es.cifpvirgen

import es.cifpvirgen.Paginas.Documentation.documentPage
import es.cifpvirgen.Paginas.Home.homePage
import es.cifpvirgen.Paginas.Login.loginPage
import es.cifpvirgen.Paginas.NotFound.notfoundPage
import es.cifpvirgen.Paginas.Register.registerPage
import kweb.*
import kweb.plugins.css.CSSPlugin
import kweb.plugins.javascript.JavascriptPlugin
import java.net.URI


fun main() {
    Kweb(port = 8080, plugins = listOf(CSSPlugin("css", "bulma.css"), JavascriptPlugin("js", "script.js"))) {
        doc.head {
            title().text("\uD83C\uDF08  Terapia Web. \uD83C\uDF24\uFE0F")
            link(LinkRelationship.stylesheet, URI.create("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css").toURL())
            link(LinkRelationship.icon, URI.create("https://upload.wikimedia.org/wikipedia/commons/d/d1/Favicon.ico.png").toURL())
        }
        doc.body {

            route {
                // URL "/"
                path("/") {
                    homePage()
                }

                // URL "/documentation"
                path("/documentation") {
                    documentPage()
                }

                // URL "/login"
                path("/login") {
                    loginPage()
                }

                // URL "/register"
                path("/register") {
                    registerPage()
                }

                // URL "/register/success"
                path("/register/success") {

                }

                notFound {
                    notfoundPage()
                }
            }
        }
    }
}
package es.cifpvirgen

import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Paginas.Documentation.creditsdocPage
import es.cifpvirgen.Paginas.Documentation.documentPage
import es.cifpvirgen.Paginas.Documentation.installdocPage
import es.cifpvirgen.Paginas.Documentation.usedocPage
import es.cifpvirgen.Paginas.Downloads.downloadsPage
import es.cifpvirgen.Paginas.Home.homePage
import es.cifpvirgen.Paginas.Login.loginPage
import es.cifpvirgen.Paginas.NotFound.notfoundPage
import es.cifpvirgen.Paginas.Register.registerPage
import es.cifpvirgen.Paginas.Register.registerSuccess
import es.cifpvirgen.Paginas.Register.registerVerify
import kotlinx.coroutines.launch
import kweb.*
import kweb.plugins.css.CSSPlugin
import kweb.plugins.javascript.JavascriptPlugin
import java.net.URI


fun main() {
    Kweb(port = 8080, plugins = listOf(CSSPlugin("css", "bulma.css"), JavascriptPlugin("js", "script.js"))) {
        doc.head {
            title().text("\uD83C\uDF08  AsTeFaSi Web. \uD83C\uDF24\uFE0F")
            link(LinkRelationship.stylesheet, URI.create("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css").toURL())
            link(LinkRelationship.icon, URI.create("https://upload.wikimedia.org/wikipedia/commons/d/d1/Favicon.ico.png").toURL())
        }
        doc.body {

            route {
                // URL "/"
                path("/") {
                    homePage()
                }

                // URL "/doc"
                path("/doc") {
                    documentPage()
                }

                path("/doc/install") {
                    installdocPage()
                }

                path("/doc/use") {
                    usedocPage()
                }

                path("/doc/credits") {
                    creditsdocPage()
                }

                // URL "/login"
                path("/login") {
                    loginPage()
                }

                path("/downlodas") {
                    //TODO("Solo se puede acceder si has iniciado sesion")
                    downloadsPage()
                }

                // URL "/register"
                path("/register") {
                    registerPage()
                }

                // URL "/register/success"
                path("/register/success") {
                    elementScope().launch {
                        val comprobarCookie = browser.callJsFunctionWithResult("return comprobarExito()").toString()
                        if (comprobarCookie == "true") {
                            registerSuccess()
                        } else {
                            url.value = "/register"
                        }
                    }
                }

                // URL "/register/activate/{activateID}"
                path("/register/activate/{activateID}") { params ->
                    val activateID = params.getValue("activateID").value
                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(activateID))
                    if (!Gestores.gestorUsuarios.estaVerificado(usuario)) {
                        Gestores.gestorUsuarios.verificarUsuario(usuario)
                    }
                    registerVerify(usuario)
                }

                notFound {
                    notfoundPage()
                }
            }
        }
    }
}
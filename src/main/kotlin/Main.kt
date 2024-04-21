package es.cifpvirgen

import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Paginas.Documentation.creditsdocPage
import es.cifpvirgen.Paginas.Documentation.documentPage
import es.cifpvirgen.Paginas.Documentation.installdocPage
import es.cifpvirgen.Paginas.Documentation.usedocPage
import es.cifpvirgen.Paginas.Downloads.downloadsPage
import es.cifpvirgen.Paginas.Home.homePage
import es.cifpvirgen.Paginas.Login.loginPage
import es.cifpvirgen.Paginas.Login.recoveryPage
import es.cifpvirgen.Paginas.Login.recoveryPanel
import es.cifpvirgen.Paginas.NotFound.notfoundPage
import es.cifpvirgen.Paginas.Register.registerPage
import es.cifpvirgen.Paginas.Register.registerSuccess
import es.cifpvirgen.Paginas.Register.registerVerify
import kotlinx.coroutines.launch
import kweb.*
import kweb.plugins.css.CSSPlugin
import kweb.plugins.javascript.JavascriptPlugin
import kweb.util.json
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

                // URL "/login/recovery"
                path("/login/recovery") {
                    recoveryPage()
                }

                // URL "/login/recovery/{recoveryID}"
                path("/login/recovery/{recoveryID}") { params ->
                    val recoveryID = params.getValue("recoveryID").value
                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(recoveryID))
                    recoveryPanel(usuario)
                }

                path("/downloads") {
                    elementScope().launch {
                        val comprobarCookie = browser.callJsFunctionWithResult("return comprobarCookie({})", "sesion".json).toString()
                        if (comprobarCookie == "true") {
                            val comprobarVerificado = (browser.callJsFunctionWithResult("return obtenerSesion()")).toString().replace("\"", "")
                            val usuario = Gestores.desencriptarUsuario(comprobarVerificado)
                            if (usuario.verificado) {
                                downloadsPage(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/register"
                path("/register") {
                    registerPage()
                }

                // URL "/register/success"
                path("/register/success") {
                    elementScope().launch {
                        val comprobarCookie = browser.callJsFunctionWithResult("return comprobarCookie({})", "success".json).toString()
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
                    registerVerify(usuario)
                }

                notFound {
                    notfoundPage()
                }
            }
        }
    }
}
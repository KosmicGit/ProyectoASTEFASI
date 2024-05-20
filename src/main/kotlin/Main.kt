package es.cifpvirgen

import es.cifpvirgen.Data.Roles
import es.cifpvirgen.Gestion.Gestores
import es.cifpvirgen.Paginas.Admin.*
import es.cifpvirgen.Paginas.Admin.Lists.adminList
import es.cifpvirgen.Paginas.Admin.Lists.clientList
import es.cifpvirgen.Paginas.Admin.Lists.therapistList
import es.cifpvirgen.Paginas.Admin.Settings.*
import es.cifpvirgen.Paginas.Documentation.Categories.creditsdocPage
import es.cifpvirgen.Paginas.Documentation.documentPage
import es.cifpvirgen.Paginas.Documentation.Categories.installdocPage
import es.cifpvirgen.Paginas.Documentation.Categories.usedocPage
import es.cifpvirgen.Paginas.Downloads.downloadsPage
import es.cifpvirgen.Paginas.Home.homePage
import es.cifpvirgen.Paginas.Login.loginPage
import es.cifpvirgen.Paginas.Login.recoveryPage
import es.cifpvirgen.Paginas.Login.recoveryPanel
import es.cifpvirgen.Paginas.NotFound.notfoundPage
import es.cifpvirgen.Paginas.Profile.Settings.*
import es.cifpvirgen.Paginas.Profile.profilePage
import es.cifpvirgen.Paginas.Register.registerPage
import es.cifpvirgen.Paginas.Register.registerSuccess
import es.cifpvirgen.Paginas.Register.registerVerify
import kotlinx.coroutines.launch
import kweb.*
import kweb.plugins.css.CSSPlugin
import kweb.plugins.javascript.JavascriptPlugin
import kweb.plugins.staticFiles.ResourceFolder
import kweb.plugins.staticFiles.StaticFilesPlugin
import java.net.URI


fun main() {
    Kweb(port = 8080, plugins = listOf(CSSPlugin("css", "bulma.css"), JavascriptPlugin("js", "script.js"), StaticFilesPlugin(resourceFolder = ResourceFolder("static"), "/cdn"))) {
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
                    if (recoveryID != "") {
                        val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(recoveryID))
                        recoveryPanel(usuario)
                    } else {
                        url.value = "/login/recovery"
                    }
                }

                // URL "/downloads"
                path("/downloads") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
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

                // URL "/admin"
                path("/admin") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado && usuario.rol == Roles.ADMINISTRADOR) {
                                adminPage(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/admin/add/{user}"
                path("/admin/add/{user}") { params ->
                    val user = params.getValue("user").value
                    if (user != "") {
                        elementScope().launch {
                            val comprobarCookie = CookieReceiver(browser).getString("sesion")
                            if (comprobarCookie != null) {
                                val usercheck = Gestores.desencriptarUsuario(comprobarCookie)
                                if (usercheck.verificado && usercheck.rol == Roles.ADMINISTRADOR) {
                                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(user))
                                    addPage(usuario)
                                } else {
                                    url.value = "/login"
                                }
                            } else {
                                url.value = "/login"
                            }
                        }
                    } else {
                        url.value = "/admin"
                    }
                }

                // URL "/admin/admins"
                path("/admin/admins") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado && usuario.rol == Roles.ADMINISTRADOR) {
                                adminList(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/admin/therapists"
                path("/admin/therapists") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado && usuario.rol == Roles.ADMINISTRADOR) {
                                therapistList(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/admin/clients"
                path("/admin/clients") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado && usuario.rol == Roles.ADMINISTRADOR) {
                                clientList(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/admin/info/{user}"
                path("/admin/info/{user}") { params ->
                    val user = params.getValue("user").value
                    if (user != "") {
                        elementScope().launch {
                            val comprobarCookie = CookieReceiver(browser).getString("sesion")
                            if (comprobarCookie != null) {
                                val usercheck = Gestores.desencriptarUsuario(comprobarCookie)
                                if (usercheck.verificado && usercheck.rol == Roles.ADMINISTRADOR) {
                                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(user))
                                    editPage(usuario)
                                } else {
                                    url.value = "/login"
                                }
                            } else {
                                url.value = "/login"
                            }
                        }
                    } else {
                        url.value = "/admin"
                    }
                }

                // URL "/admin/date/{user}"
                path("/admin/date/{user}") { params ->
                    val user = params.getValue("user").value
                    if (user != "") {
                        elementScope().launch {
                            val comprobarCookie = CookieReceiver(browser).getString("sesion")
                            if (comprobarCookie != null) {
                                val usercheck = Gestores.desencriptarUsuario(comprobarCookie)
                                if (usercheck.verificado && usercheck.rol == Roles.ADMINISTRADOR) {
                                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(user))
                                    dateEdit(usuario)
                                } else {
                                    url.value = "/login"
                                }
                            } else {
                                url.value = "/login"
                            }
                        }
                    } else {
                        url.value = "/admin"
                    }
                }

                // URL "/admin/delete/{user}"
                path("/admin/delete/{user}") { params ->
                    val user = params.getValue("user").value
                    if (user != "") {
                        elementScope().launch {
                            val comprobarCookie = CookieReceiver(browser).getString("sesion")
                            if (comprobarCookie != null) {
                                val usercheck = Gestores.desencriptarUsuario(comprobarCookie)
                                if (usercheck.verificado && usercheck.rol == Roles.ADMINISTRADOR) {
                                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(user))
                                    deleteEdit(usuario)
                                } else {
                                    url.value = "/login"
                                }
                            } else {
                                url.value = "/login"
                            }
                        }
                    } else {
                        url.value = "/admin"
                    }
                }

                // URL "/admin/email/{user}"
                path("/admin/email/{user}") { params ->
                    val user = params.getValue("user").value
                    if (user != "") {
                        elementScope().launch {
                            val comprobarCookie = CookieReceiver(browser).getString("sesion")
                            if (comprobarCookie != null) {
                                val usercheck = Gestores.desencriptarUsuario(comprobarCookie)
                                if (usercheck.verificado && usercheck.rol == Roles.ADMINISTRADOR) {
                                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(user))
                                    emailEdit(usuario)
                                } else {
                                    url.value = "/login"
                                }
                            } else {
                                url.value = "/login"
                            }
                        }
                    } else {
                        url.value = "/admin"
                    }
                }

                // URL "/admin/image/{user}"
                path("/admin/image/{user}") { params ->
                    val user = params.getValue("user").value
                    if (user != "") {
                        elementScope().launch {
                            val comprobarCookie = CookieReceiver(browser).getString("sesion")
                            if (comprobarCookie != null) {
                                val usercheck = Gestores.desencriptarUsuario(comprobarCookie)
                                if (usercheck.verificado && usercheck.rol == Roles.ADMINISTRADOR) {
                                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(user))
                                    imageEdit(usuario)
                                } else {
                                    url.value = "/login"
                                }
                            } else {
                                url.value = "/login"
                            }
                        }
                    } else {
                        url.value = "/admin"
                    }
                }

                // URL "/admin/passwd/{user}"
                path("/admin/passwd/{user}") { params ->
                    val user = params.getValue("user").value
                    if (user != "") {
                        elementScope().launch {
                            val comprobarCookie = CookieReceiver(browser).getString("sesion")
                            if (comprobarCookie != null) {
                                val usercheck = Gestores.desencriptarUsuario(comprobarCookie)
                                if (usercheck.verificado && usercheck.rol == Roles.ADMINISTRADOR) {
                                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(user))
                                    passwordEdit(usuario)
                                } else {
                                    url.value = "/login"
                                }
                            } else {
                                url.value = "/login"
                            }
                        }
                    } else {
                        url.value = "/admin"
                    }
                }

                // URL "/admin/user/{user}"
                path("/admin/user/{user}") { params ->
                    val user = params.getValue("user").value
                    if (user != "") {
                        elementScope().launch {
                            val comprobarCookie = CookieReceiver(browser).getString("sesion")
                            if (comprobarCookie != null) {
                                val usercheck = Gestores.desencriptarUsuario(comprobarCookie)
                                if (usercheck.verificado && usercheck.rol == Roles.ADMINISTRADOR) {
                                    val usuario = Gestores.desencriptarUsuario(Gestores.decodificarURL(user))
                                    userEdit(usuario)
                                } else {
                                    url.value = "/login"
                                }
                            } else {
                                url.value = "/login"
                            }
                        }
                    } else {
                        url.value = "/admin"
                    }
                }

                // URL "/profile"
                path("/profile") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado) {
                                profilePage(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/profile/settings"
                path("/profile/settings") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado) {
                                profileSettings(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/profile/settings/image"
                path("/profile/settings/image") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado) {
                                imageSettings(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/profile/settings/user"
                path("/profile/settings/user") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado) {
                                userSettings(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/profile/settings/date"
                path("/profile/settings/date") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado) {
                                dateSettings(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/profile/settings/password"
                path("/profile/settings/password") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado) {
                                passwordSettings(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/profile/settings/email"
                path("/profile/settings/email") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado) {
                                emailSettings(usuario)
                            } else {
                                url.value = "/login"
                            }
                        } else {
                            url.value = "/login"
                        }
                    }
                }

                // URL "/profile/settings/email/verify"
                path("/profile/settings/email/verify/{activateID}") { params ->
                    val activateID = params.getValue("activateID").value
                    if (activateID != "") {
                        val usuarioModificado = Gestores.desencriptarUsuario(Gestores.decodificarURL(activateID))
                        val checkUser = Gestores.gestorUsuarios.obtenerUsuario(usuarioModificado.username)
                        if (checkUser != null) {
                            emailVerify(usuarioModificado)
                        } else {
                            url.value = "/login"
                        }
                    } else {
                        url.value = "/login"
                    }
                }

                // URL "/profile/settings/delete"
                path("/profile/settings/delete") {
                    elementScope().launch {
                        val comprobarCookie = CookieReceiver(browser).getString("sesion")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            if (usuario.verificado) {
                                deleteSettings(usuario)
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
                        val comprobarCookie = CookieReceiver(browser).getString("success")
                        if (comprobarCookie != null) {
                            val usuario = Gestores.desencriptarUsuario(comprobarCookie)
                            registerSuccess(usuario)
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
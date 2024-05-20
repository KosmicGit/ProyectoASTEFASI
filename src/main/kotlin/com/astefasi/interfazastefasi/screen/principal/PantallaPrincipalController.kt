package com.astefasi.interfazastefasi.screen.principal

import com.astefasi.interfazastefasi.Main
import com.astefasi.interfazastefasi.animation.AnimationHandler
import com.astefasi.interfazastefasi.gestion.bbdd.ConexionBD
import com.astefasi.interfazastefasi.gestion.ficheros.CacheFile
import com.astefasi.interfazastefasi.gestion.ficheros.ConfigFile
import com.astefasi.interfazastefasi.gestion.inputs.Gestores
import com.astefasi.interfazastefasi.util.AjustesAplicacion
import com.astefasi.interfazastefasi.util.data.Sesion
import com.astefasi.interfazastefasi.util.data.configuracion.Resolucion
import com.astefasi.interfazastefasi.util.data.family.Familia
import com.astefasi.interfazastefasi.util.data.user.Cliente
import com.astefasi.interfazastefasi.util.data.user.Roles
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.ScrollPane
import javafx.scene.control.Separator
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import java.net.URL
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList

class PantallaPrincipalController : Initializable {

    //Secciones
    @FXML
    lateinit var loginPage : HBox
    @FXML
    lateinit var mainPage : StackPane

    @FXML
    lateinit var homePage : ScrollPane
    @FXML
    lateinit var sessionsPage : ScrollPane
    @FXML
    lateinit var familyPage : ScrollPane
    @FXML
    lateinit var clientsPage : ScrollPane
    @FXML
    lateinit var adminPage : ScrollPane
    @FXML
    lateinit var configurationPage : ScrollPane

    @FXML
    lateinit var profilePage : HBox
    @FXML
    lateinit var profileSettings : ScrollPane
    @FXML
    lateinit var privateSettings : ScrollPane

    //Paginas individuales
    @FXML
    lateinit var historialSesiones : VBox
    @FXML
    lateinit var historialFamilias : VBox
    @FXML
    lateinit var historialClientes : VBox

    //Botones
    //Barra superior
    @FXML
    lateinit var exitButton : Button
    @FXML
    lateinit var resolucionConfig : ComboBox<String>
    //Navegador
    @FXML
    lateinit var homeButton : Button
    @FXML
    lateinit var sessionButton : Button
    @FXML
    lateinit var familyButton : Button
    @FXML
    lateinit var configButton : Button
    //Perfil
    @FXML
    lateinit var imageProfile : ImageView
    @FXML
    lateinit var configImage : ImageView

    //Inputs fields
    @FXML
    lateinit var emailInput : TextField
    @FXML
    lateinit var correoConfigField : TextField
    @FXML
    lateinit var nameConfigField : TextField
    @FXML
    lateinit var subnameConfigField : TextField
    @FXML
    lateinit var passwordConfigField : PasswordField
    @FXML
    lateinit var passwordInput : PasswordField
    @FXML
    lateinit var keepLoggedIn : CheckBox
    @FXML
    lateinit var savePassword : CheckBox

    //Componentes
    @FXML
    lateinit var errorLabel : Label
    @FXML
    lateinit var imageLogin1 : ImageView
    @FXML
    lateinit var imageLogin2 : ImageView

    override fun initialize(location : URL?, resources : ResourceBundle?) {
        imageLogin1.fitWidthProperty().bind(AjustesAplicacion.mainStage.widthProperty().multiply(0.9))
        imageLogin1.fitHeightProperty().bind(AjustesAplicacion.mainStage.heightProperty())
        imageLogin2.fitWidthProperty().bind(AjustesAplicacion.mainStage.widthProperty().multiply(0.9))
        imageLogin2.fitHeightProperty().bind(AjustesAplicacion.mainStage.heightProperty())

        resolucionConfig.items.addAll("Adaptada (Recomendable)", "1920x1080", "1080x720")

        emailInput.text = CacheFile.cacheUsuario.email
        passwordInput.text = CacheFile.cacheUsuario.password

        if (CacheFile.cacheUsuario.mantenerSesion) {
            val gestor = Gestores.gestorUsuarios
            Main.usuario = gestor.obtenerUsuarioMail(CacheFile.cacheUsuario.email)

            if (Main.usuario!!.rol == Roles.ADMINISTRADOR) {
                familyButton.text = "Administracion"
            }else if (Main.usuario!!.rol == Roles.TERAPEUTA) {
                familyButton.text = "Clientes"
            }

            loginPage.isVisible = false
            loginPage.isDisable = true
            loginPage.opacity = 0.0
            mainPage.isVisible = true
            mainPage.isDisable = false
            mainPage.opacity = 1.0
        }

        AnimationHandler.imageTransition(imageLogin1, imageLogin2)
    }

    @FXML
    fun checkAccount() {
        val email = emailInput.text
        var password = passwordInput.text
        if ((email == null || email.isEmpty()) || (password == null || password.isEmpty())) {
            errorLabel.text = "Debes rellenar ambos campos."
        }else {
            val mantenerSesion = keepLoggedIn.isSelected
            val guardarContraseña = savePassword.isSelected

            val gestor = Gestores.gestorUsuarios

            val usuario = gestor.obtenerUsuarioMail(email)
            if (usuario == null || usuario.password != password) {
                println(usuario!!.password)
                errorLabel.text = "Has introducido mal algun campo."
            }else {
                errorLabel.text = ""
                Main.usuario = usuario
                if (!guardarContraseña) password = ""

                correoConfigField.text = usuario.email
                passwordConfigField.text = usuario.password

                if (usuario.rol == Roles.ADMINISTRADOR) {
                    familyButton.text = "Administracion"
                    val admin = Cliente("xxxxx", "Administrador", "", "Ninguna", Date(0), usuario.idUsuario)
                    Main.cliente = admin
                    nameConfigField.text = Main.cliente!!.nombre
                    subnameConfigField.text = Main.cliente!!.apellido
                }else if (usuario.rol == Roles.TERAPEUTA) {
                    familyButton.text = "Clientes"
                    val terapeuta = Gestores.gestorTerapeuta.conseguirTerapeutaPorIdUsuario(usuario.idUsuario)
                    Main.terapeuta = terapeuta
                    nameConfigField.text = Main.terapeuta!!.nombre
                    subnameConfigField.text = Main.terapeuta!!.apellido
                }else {
                    val cliente = Gestores.gestorClientes.conseguirClientePorId(usuario.idUsuario)
                    Main.cliente = cliente
                    nameConfigField.text = Main.cliente!!.nombre
                    subnameConfigField.text = Main.cliente!!.apellido
                }

                Main.cache.cambiarEmail(email)
                Main.cache.cambiarContrasenia(password)
                Main.cache.cambiarMantenerSesion(mantenerSesion)
                if (Gestores.gestorUsuarios.obtenerFoto(usuario) != null) {
                    imageProfile.image = Image(Gestores.gestorUsuarios.obtenerFoto(usuario))
                    configImage.image = Image(Gestores.gestorUsuarios.obtenerFoto(usuario))
                }
                AnimationHandler.fadeAnimation(loginPage, mainPage)
            }
        }
    }

    @FXML
    fun cambiarResolucion() {
        when (resolucionConfig.selectionModel.selectedIndex) {
            0 -> {
                AjustesAplicacion.mainStage.width = Resolucion.ADAPTADA.anchura
                AjustesAplicacion.mainStage.height = Resolucion.ADAPTADA.altura
                Main.configuracion.cambiarResolucion(Resolucion.ADAPTADA)
            }
            1 -> {
                AjustesAplicacion.mainStage.width = Resolucion.FULLHD.anchura
                AjustesAplicacion.mainStage.height = Resolucion.FULLHD.altura
                Main.configuracion.cambiarResolucion(Resolucion.FULLHD)
            }
            2 -> {
                AjustesAplicacion.mainStage.width = Resolucion.HD.anchura
                AjustesAplicacion.mainStage.height = Resolucion.HD.altura
                Main.configuracion.cambiarResolucion(Resolucion.HD)
            }
        }
    }

    @FXML
    fun onExitButton() {
        ConexionBD.connection!!.close()
        Platform.exit()
    }

    @FXML
    fun onHideButton() {
        AjustesAplicacion.mainStage.isIconified = true
    }

    @FXML
    fun onProfileButton() {
        AnimationHandler.fadeAnimation(mainPage, profilePage)
    }

    @FXML
    fun onHomeButton() {
        this.switchSection(homeButton)
    }

    @FXML
    fun onSessionButton() {
        this.switchSection(sessionButton)
        this.updateSessions()
    }

    @FXML
    fun onFamilyButton() {
        this.switchSection(familyButton)
    }

    @FXML
    fun onConfigButton() {
        this.switchSection(configButton)
    }

    @FXML
    fun onProfileSettingsButton() {
        profileSettings.isDisable = false
        profileSettings.isVisible = true

        privateSettings.isDisable = true
        privateSettings.isVisible = false
    }

    @FXML
    fun onPrivateInfoButton() {
        profileSettings.isDisable = true
        profileSettings.isVisible = false

        privateSettings.isDisable = false
        privateSettings.isVisible = true
    }

    @FXML
    fun onDisconnectButton() {
        AnimationHandler.fadeAnimation(profilePage, loginPage)
        Main.cliente = null
        Main.terapeuta = null
    }

    @FXML
    fun onBackProfileButton() {
        AnimationHandler.fadeAnimation(profilePage, mainPage)
    }

    private var xOffset = 0.0
    private var yOffset = 0.0

    @FXML
    fun onMousePressed(event : MouseEvent) {
        xOffset = event.sceneX
        yOffset = event.sceneY
    }

    @FXML
    fun onMouseDragged(event : MouseEvent) {
        AjustesAplicacion.mainStage.x = event.screenX - xOffset
        AjustesAplicacion.mainStage.y = event.screenY - yOffset
    }

    @FXML
    fun onHoverExit() {
        exitButton.style = "-fx-background-color: red; -fx-background-radius: 0;"
    }

    @FXML
    fun onUnhoverExit() {
        exitButton.style = "-fx-background-color: transparent"
    }

    private fun updateClients() {
        historialClientes.children.clear()
        val clientes = Gestores.gestorTerapeuta.obtenerClientes()
        clientes.forEach { cliente ->
            val hbox = createClient(cliente)
            historialClientes.children.add(hbox)
        }
    }

    private fun updateFamilies() {
        historialFamilias.children.clear()
        val familia = Gestores.gestorTerapeuta.verFamilia(Main.cliente!!.dni)
        if (familia.isNotEmpty()) {
            val vbox = createFamily(familia)
            historialFamilias.children.add(vbox)
        }
    }

    private fun updateSessions() {
        historialSesiones.children.clear()
        if (Main.cliente != null) {
            Gestores.gestorClientes.historicoCitasCliente(Main.cliente!!).forEach { sesion ->
                val fecha = "${sesion.fecha.toLocalDate().year}-${sesion.fecha.toLocalDate().month.value}-${sesion.fecha.toLocalDate().dayOfMonth}"
                var activa = "Realizada"
                if (sesion.activa == 1) {
                    activa = "Pendiente"
                }
                val terapeuta = Gestores.gestorTerapeuta.conseguirTerapeutaPorIdTerapeuta(sesion.idTerapeuta)
                val miembros = ArrayList<String>()
                if (sesion.familiar == 1) {
                    Gestores.gestorTerapeuta.verFamilia(sesion.dniCliente).forEach {fam ->
                        miembros.add(Gestores.gestorClientes.conseguirClientePorDNI(fam.dni)!!.nombre)
                    }
                }else {
                    miembros.add(Gestores.gestorClientes.conseguirClientePorDNI(sesion.dniCliente)!!.nombre)
                }
                historialSesiones.children.add(createVisualSession(fecha, activa, terapeuta!!.nombre, miembros))
            }
        }else {
            Gestores.gestorTerapeuta.historicoCitasTerapeuta(Main.terapeuta!!).forEach { sesion ->
                val fecha = "${sesion.fecha.toLocalDate().year}-${sesion.fecha.toLocalDate().month.value}-${sesion.fecha.toLocalDate().dayOfMonth}"
                var activa = "Realizada"
                if (sesion.activa == 1) {
                    activa = "Pendiente"
                }
                val terapeuta = Gestores.gestorTerapeuta.conseguirTerapeutaPorIdTerapeuta(sesion.idTerapeuta)
                val miembros = ArrayList<String>()
                if (sesion.familiar == 1) {
                    Gestores.gestorTerapeuta.verFamilia(sesion.dniCliente).forEach {fam ->
                        miembros.add(Gestores.gestorClientes.conseguirClientePorDNI(fam.dni)!!.nombre)
                    }
                }else {
                    miembros.add(Gestores.gestorClientes.conseguirClientePorDNI(sesion.dniCliente)!!.nombre)
                }
                historialSesiones.children.add(createVisualSessionTerapeuta(fecha, activa, terapeuta!!.nombre, miembros, sesion.idSesion))
            }
        }
    }

    private fun createClient(cliente : Cliente) : HBox {
        val hbox = HBox()
        hbox.spacing = 30.0
        hbox.alignment = Pos.CENTER
        hbox.style = "-fx-background-color: #0cf574; -fx-background-radius: 50;"

        val clienteInfo = Label()
        clienteInfo.font = Font("System Bold", 21.0)
        clienteInfo.text = "${cliente.nombre} ${cliente.apellido} - ${cliente.dni} - "

        val darCita = Button()
        darCita.style = "-fx-background-color: #FFFFFF; -fx-background-radius: 50;"
        darCita.text = "Dar Cita"
        darCita.setOnAction {
            val currentTime = System.currentTimeMillis()
            val tiempoAniadir = 7 * 86400000L

            val sesion = Sesion(0, cliente.dni, Main.terapeuta!!.idTerapeuta, Date(currentTime + tiempoAniadir), 0, 1)
            Gestores.gestorTerapeuta.insertarSesion(sesion, cliente.dni, Main.terapeuta!!.idTerapeuta)
        }

        hbox.children.addAll(clienteInfo, darCita)
        return hbox
    }

    private fun createFamily(miembros : ArrayList<Familia>): VBox {
        val vbox = VBox()
        vbox.spacing = 10.0
        vbox.alignment = Pos.CENTER
        vbox.style = "-fx-background-color: #0cf574; -fx-background-radius: 50;"

        val labelFamilia = Label()
        labelFamilia.font = Font("System Bold", 21.0)
        labelFamilia.text = "Miembros"

        vbox.children.add(labelFamilia)

        miembros.forEach { miembros ->
            val miembro = Gestores.gestorClientes.conseguirClientePorDNI(miembros.dni)

            val labelMiembro = Label()
            labelMiembro.font = Font("System Bold", 18.0)
            labelMiembro.text = "${miembro!!.nombre} ${miembro!!.apellido} - ${miembros.parentesco}"

            vbox.children.add(labelMiembro)
        }

        return vbox
    }

    private fun createVisualSession(fecha : String, estado : String, terapeuta : String, miembros : ArrayList<String>) : HBox {
        val hbox = HBox()
        hbox.spacing = 30.0
        hbox.alignment = Pos.CENTER

        if (estado == "Pendiente") {
            hbox.style = "-fx-background-color: #0cf574; -fx-background-radius: 50;"
        }else {
            hbox.style = "-fx-background-color: #a83832; -fx-background-radius: 50;"
        }

        val labelFecha = Label()
        labelFecha.font = Font("System Bold", 21.0)
        labelFecha.text = fecha

        val labelEstado = Label()
        labelEstado.font = Font("System Bold", 21.0)
        labelEstado.text = estado

        val labelTerapeuta = Label()
        labelTerapeuta.font = Font("System Bold", 21.0)
        labelTerapeuta.text = terapeuta

        val labelMiembros = Label()
        labelMiembros.font = Font("System Bold", 21.0)
        var miembrosText = ""
        miembros.forEach { m ->
            miembrosText += "- $m\n"
        }
        labelMiembros.text = miembrosText

        val separator1 = Separator()
        val separator2 = Separator()
        val separator3 = Separator()
        separator1.orientation = Orientation.VERTICAL
        separator2.orientation = Orientation.VERTICAL
        separator3.orientation = Orientation.VERTICAL

        hbox.children.addAll(labelFecha, separator1, labelEstado, separator2, labelTerapeuta, separator3, labelMiembros)

        return hbox
    }

    private fun createVisualSessionTerapeuta(fecha : String, estado : String, terapeuta : String, miembros : ArrayList<String>, id : Int) : HBox {
        val hbox = HBox()
        hbox.spacing = 30.0
        hbox.alignment = Pos.CENTER

        if (estado == "Pendiente") {
            hbox.style = "-fx-background-color: #0cf574; -fx-background-radius: 50;"
        }else {
            hbox.style = "-fx-background-color: #a83832; -fx-background-radius: 50;"
        }

        val labelFecha = Label()
        labelFecha.font = Font("System Bold", 21.0)
        labelFecha.text = fecha

        val labelEstado = Label()
        labelEstado.font = Font("System Bold", 21.0)
        labelEstado.text = estado

        val labelTerapeuta = Label()
        labelTerapeuta.font = Font("System Bold", 21.0)
        labelTerapeuta.text = terapeuta

        val labelMiembros = Label()
        labelMiembros.font = Font("System Bold", 21.0)
        var miembrosText = ""
        miembros.forEach { m ->
            miembrosText += "- $m\n"
        }
        labelMiembros.text = miembrosText

        val anularCita = Button()
        anularCita.style = "-fx-background-color: #FFFFFF; -fx-background-radius: 50;"
        anularCita.text = "Anular Cita"
        anularCita.setOnAction {
            Gestores.gestorTerapeuta.borrarSesion(id)
            this.updateSessions()
        }

        val separator1 = Separator()
        val separator2 = Separator()
        val separator3 = Separator()
        val separator4 = Separator()
        separator1.orientation = Orientation.VERTICAL
        separator2.orientation = Orientation.VERTICAL
        separator3.orientation = Orientation.VERTICAL
        separator4.orientation = Orientation.VERTICAL

        hbox.children.addAll(labelFecha, separator1, labelEstado, separator2, labelTerapeuta, separator3, labelMiembros, separator4, anularCita)

        return hbox
    }

    private fun switchSection(selected : Button) {
        val botones = arrayListOf(homeButton, sessionButton, familyButton, configButton)

        botones.forEach {button ->
            if (button == selected) {
                button.style = "-fx-background-color: #22a2ff; -fx-background-radius: 50;"
            }else {
                button.style = "-fx-background-color: transparent; -fx-background-radius: 50;"
            }
        }

        homePage.isVisible = false
        homePage.isDisable = true
        sessionsPage.isVisible = false
        sessionsPage.isDisable = true
        familyPage.isVisible = false
        familyPage.isDisable = true
        clientsPage.isVisible = false
        clientsPage.isDisable = true
        adminPage.isVisible = false
        adminPage.isDisable = true
        configurationPage.isVisible = false
        configurationPage.isDisable = true

        when (selected) {
            homeButton -> {
                homePage.isVisible = true
                homePage.isDisable = false
            }
            sessionButton -> {
                sessionsPage.isVisible = true
                sessionsPage.isDisable = false
            }
            familyButton -> {
                when (Main.usuario!!.rol) {
                    Roles.PACIENTE -> {
                        familyPage.isVisible = true
                        familyPage.isDisable = false
                        this.updateFamilies()
                    }
                    Roles.TERAPEUTA -> {
                        clientsPage.isVisible = true
                        clientsPage.isDisable = false
                        this.updateClients()
                    }
                    Roles.ADMINISTRADOR -> {
                        adminPage.isVisible = true
                        adminPage.isDisable = false
                    }
                }
            }
            configButton -> {
                configurationPage.isVisible = true
                configurationPage.isDisable = false
            }
        }
    }
}
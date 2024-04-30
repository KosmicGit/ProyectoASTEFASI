package es.cifpvirgen.datmaker

import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File

class DatMakerController {

    @FXML
    lateinit var hideKey: CheckBox

    @FXML
    lateinit var keyField: PasswordField

    @FXML
    lateinit var textField: TextField

    // Mail Tab
    @FXML
    lateinit var openFileMail: Button

    @FXML
    lateinit var fileNameMail: Label

    @FXML
    lateinit var createFileMail: Button

    @FXML
    lateinit var newFileNameMail: TextField

    @FXML
    lateinit var smtpField: TextField

    @FXML
    lateinit var portField: TextField

    @FXML
    lateinit var mailUserField: TextField

    @FXML
    lateinit var mailPasswdField: TextField

    @FXML
    lateinit var leerDatMail: Button

    @FXML
    lateinit var escribirDatMail: Button

    // DB Tab
    @FXML
    lateinit var openFileDB: Button

    @FXML
    lateinit var fileNameDB: Label

    @FXML
    lateinit var createFileDB: Button

    @FXML
    lateinit var newFileNameDB: TextField

    @FXML
    lateinit var serverField: TextField

    @FXML
    lateinit var dbUserField: TextField

    @FXML
    lateinit var dbPasswdField: TextField

    @FXML
    lateinit var leerDatDB: Button

    @FXML
    lateinit var escribirDatDB: Button

    @FXML
    lateinit var systemUser: Label

    private lateinit var encrypt: Encriptacion

    @FXML
    private fun initialize() {
        systemUser.text = System.getProperty("user.name")
        showWarning("¡ATENCION!", "El acceso a esta herramienta está restringida exclusivamente a administradores autorizados. Cualquier acceso no autorizado puede resultar en consecuencias legales, incluyendo penalizaciones o multas. Le instamos a abstenerse de continuar si no cuenta con los permisos adecuados.", "AsTeFaSi Internals - CONFIDENCIAL")
        textField.textProperty().addListener { _, _, newValue ->
            keyField.text = newValue
        }
        keyField.textProperty().addListener { _, _, newValue ->
            textField.text = newValue
        }

        val showKey = Bindings.not(hideKey.selectedProperty())
        keyField.visibleProperty().bind(showKey)
        keyField.managedProperty().bind(showKey)
        textField.visibleProperty().bind(hideKey.selectedProperty())
        textField.managedProperty().bind(hideKey.selectedProperty())

        // Tab Mail
        openFileMail.setOnAction {
            val stage = openFileMail.scene.window as Stage
            val fileChooser = FileChooser()
            fileChooser.title = "Seleccionar archivo .dat"
            fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Archivos DAT", "*.dat"))

            val currentDirectory = System.getProperty("user.dir")
            val initialDir = File(currentDirectory)
            fileChooser.initialDirectory = initialDir

            val selectedFile = fileChooser.showOpenDialog(stage)
            selectedFile?.let {
                fileNameMail.text = it.name
                leerDatMail.setOnAction {
                    if (keyField.text == "") {
                        showError("Error", "Debe introducir una clave de cifrado AES")
                    } else {
                        encrypt = Encriptacion(keyField.text)
                        val contenido = leerDat(selectedFile.absolutePath)
                        smtpField.text = contenido[0]
                        portField.text = contenido[1]
                        mailUserField.text = contenido[2]
                        mailPasswdField.text = contenido[3]
                    }

                }
                escribirDatMail.setOnAction {
                    if (keyField.text == "") {
                        showError("Error", "Debe introducir una clave de cifrado AES")
                    } else {
                        encrypt = Encriptacion(keyField.text)
                        val smtp = smtpField.text
                        val puerto = portField.text
                        val usuario = mailUserField.text
                        val contraseña = mailPasswdField.text
                        crearDatMail(selectedFile.absolutePath, smtp, puerto, usuario, contraseña)
                        showInfo("Cambios Realizados", "Información guardada en el archivo")
                    }
                }
            }
        }

        createFileMail.setOnAction {
            if (newFileNameMail.text == "") {
                showError("Error", "Debe introducir un nombre para el archivo")
            } else {
                if (smtpField.text == "" || portField.text == "" || mailUserField.text == "" || mailPasswdField.text == "") {
                    showError("Error", "Debe rellenar todos los datos")
                } else {
                    if (keyField.text == "") {
                        showError("Error", "Debe introducir una clave de cifrado AES")
                    } else {
                        encrypt = Encriptacion(keyField.text)
                        val stage = createFileMail.scene.window as Stage
                        val fileChooser = FileChooser()
                        fileChooser.title = "Guardar archivo .dat"
                        val defaultFileName = newFileNameMail.text + ".dat"
                        fileChooser.initialFileName = defaultFileName

                        val currentDirectory = System.getProperty("user.dir")
                        val initialDir = File(currentDirectory)
                        fileChooser.initialDirectory = initialDir

                        val selectedFile = fileChooser.showSaveDialog(stage)
                        selectedFile?.let {
                            val archivo = it.absolutePath
                            val smtp = smtpField.text
                            val puerto = portField.text
                            val usuario = mailUserField.text
                            val contraseña = mailPasswdField.text
                            crearDatMail(archivo, smtp, puerto, usuario, contraseña)
                            showInfo("Archivo guardado: ${it.name}", "Archivo ${it.name} guardado en la ubicación ${it.path}")
                        }
                    }
                }
            }
        }

        // Tab DB
        openFileDB.setOnAction {
            val stage = openFileDB.scene.window as Stage
            val fileChooser = FileChooser()
            fileChooser.title = "Seleccionar archivo .dat"
            fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Archivos DAT", "*.dat"))

            val currentDirectory = System.getProperty("user.dir")
            val initialDir = File(currentDirectory)
            fileChooser.initialDirectory = initialDir

            val selectedFile = fileChooser.showOpenDialog(stage)
            selectedFile?.let {
                fileNameDB.text = it.name
                leerDatDB.setOnAction {
                    if (keyField.text == "") {
                        showError("Error", "Debe introducir una clave de cifrado AES")
                    } else {
                        encrypt = Encriptacion(keyField.text)
                        val contenido = leerDat(selectedFile.absolutePath)
                        serverField.text = contenido[2]
                        dbUserField.text = contenido[0]
                        dbPasswdField.text = contenido[1]
                    }

                }
                escribirDatDB.setOnAction {
                    if (keyField.text == "") {
                        showError("Error", "Debe introducir una clave de cifrado AES")
                    } else {
                        encrypt = Encriptacion(keyField.text)
                        val server = serverField.text
                        val usuario = dbUserField.text
                        val contraseña = dbPasswdField.text
                        crearDatDB(selectedFile.absolutePath, usuario, contraseña, server)
                        showInfo("Cambios Realizados", "Información guardada en el archivo")
                    }
                }
            }
        }

        createFileDB.setOnAction {
            if (newFileNameDB.text == "") {
                showError("Error", "Debe introducir un nombre para el archivo")
            } else {
                if (serverField.text == "" || dbUserField.text == "" || dbPasswdField.text == "") {
                    showError("Error", "Debe rellenar todos los datos")
                } else {
                    if (keyField.text == "") {
                        showError("Error", "Debe introducir una clave de cifrado AES")
                    } else {
                        encrypt = Encriptacion(keyField.text)
                        val stage = createFileDB.scene.window as Stage
                        val fileChooser = FileChooser()
                        fileChooser.title = "Guardar archivo .dat"
                        val defaultFileName = newFileNameDB.text + ".dat"
                        fileChooser.initialFileName = defaultFileName

                        val currentDirectory = System.getProperty("user.dir")
                        val initialDir = File(currentDirectory)
                        fileChooser.initialDirectory = initialDir

                        val selectedFile = fileChooser.showSaveDialog(stage)
                        selectedFile?.let {
                            val archivo = it.absolutePath
                            val server = serverField.text
                            val usuario = dbUserField.text
                            val contraseña = dbPasswdField.text
                            crearDatDB(archivo, usuario, contraseña, server)
                            showInfo(
                                "Archivo guardado: ${it.name}",
                                "Archivo ${it.name} guardado en la ubicación ${it.path}"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun crearDatMail(archivo: String, host: String, port: String, username: String, passwd: String) {
        encrypt.encriptar(archivo, "$host\n$port\n$username\n$passwd")
    }

    private fun crearDatDB(archivo: String, username: String, passwd: String, server: String) {
        encrypt.encriptar(archivo, "$username\n$passwd\n$server")
    }

    private fun leerDat(archivo: String): ArrayList<String> {
        return encrypt.desencriptar(archivo) as ArrayList<String>
    }

    private fun showInfo(title: String, message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }

    private fun showError(title: String, message: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }

    private fun showWarning(title: String, message: String, header: String? =null) {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = title
        alert.headerText = header
        alert.contentText = message
        alert.showAndWait()
    }
}
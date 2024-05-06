package com.astefasi.interfazastefasi.gestion.ficheros

import com.astefasi.interfazastefasi.util.Ficheros
import com.astefasi.interfazastefasi.util.data.configuracion.Configuracion
import com.astefasi.interfazastefasi.util.data.configuracion.Idioma
import com.astefasi.interfazastefasi.util.data.configuracion.Resolucion
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException
import java.nio.file.Files
import java.nio.file.Paths

class ConfigFile {

    companion object {
        var confUsuario = Configuracion(Idioma.ESP, Resolucion.ADAPTADA)
    }

    val CONFIG_PATH = Paths.get("${Ficheros.APLICATION_DOC}Config/")
    val FILE_PATH = Paths.get("${Ficheros.APLICATION_DOC}Config/", "config.dat")
    constructor() {
        if (Files.notExists(CONFIG_PATH)) {
            Files.createDirectories(CONFIG_PATH)
        }

        this.obtenerConfiguracion()
    }

    fun cambiarIdioma(idioma : Idioma) {
        confUsuario.idioma = idioma
        guardarConfiguracion()
    }

    fun cambiarResolucion(resolucion : Resolucion) {
        confUsuario.resolucion = resolucion
        guardarConfiguracion()
    }

    private fun obtenerConfiguracion() {
        val fichero = FILE_PATH.toFile()
        if (fichero.exists()) {
            var objectInputStream : ObjectInputStream? = null
            try {
                val fileInputStream = FileInputStream(fichero)
                objectInputStream = ObjectInputStream(fileInputStream)
                confUsuario = objectInputStream.readObject() as Configuracion
            }catch (exception : StreamCorruptedException) {
                //TODO
            }finally {
                objectInputStream?.close()
            }
        }else {
            guardarConfiguracion()
        }
    }

    private fun guardarConfiguracion() {
        val fileStream = FileOutputStream(FILE_PATH.toFile())
        val objectStream = ObjectOutputStream(fileStream)
        objectStream.writeObject(confUsuario)
        objectStream.close()
    }
}
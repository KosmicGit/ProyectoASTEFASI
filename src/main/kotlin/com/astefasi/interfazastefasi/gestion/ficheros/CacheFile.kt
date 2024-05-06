package com.astefasi.interfazastefasi.gestion.ficheros

import com.astefasi.interfazastefasi.util.Ficheros
import com.astefasi.interfazastefasi.util.data.cache.Cache
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

class CacheFile {
    companion object {
        var cacheUsuario = Cache("", "", false)
    }

    val CACHE_PATH = Paths.get("${Ficheros.APLICATION_DOC}Cache/")
    val FILE_PATH = Paths.get("${Ficheros.APLICATION_DOC}Cache/", "cache.dat")

    constructor() {
        if (Files.notExists(CACHE_PATH)) {
            Files.createDirectories(CACHE_PATH)
        }

        this.obtenerCache()
    }

    fun cambiarEmail(email : String) {
        cacheUsuario.email = email
        guardarCache()
    }

    fun cambiarContrasenia(contrasenia : String) {
        cacheUsuario.password = contrasenia
        guardarCache()
    }

    fun cambiarMantenerSesion(mantener : Boolean) {
        cacheUsuario.mantenerSesion = mantener
        guardarCache()
    }

    private fun obtenerCache() {
        val fichero = FILE_PATH.toFile()
        if (fichero.exists()) {
            var objectInputStream : ObjectInputStream? = null
            try {
                val fileInputStream = FileInputStream(fichero)
                objectInputStream = ObjectInputStream(fileInputStream)
                cacheUsuario = objectInputStream.readObject() as Cache
            }catch (exception : StreamCorruptedException) {
                //TODO
            }finally {
                objectInputStream?.close()
            }
        }else {
            guardarCache()
        }
    }

    private fun guardarCache() {
        val fileStream = FileOutputStream(FILE_PATH.toFile())
        val objectStream = ObjectOutputStream(fileStream)
        objectStream.writeObject(cacheUsuario)
        objectStream.close()
    }
}
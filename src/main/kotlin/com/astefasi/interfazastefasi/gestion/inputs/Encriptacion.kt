package com.astefasi.interfazastefasi.gestion.inputs

import com.astefasi.interfazastefasi.Main
import com.astefasi.interfazastefasi.util.Ficheros
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Encriptacion {
    private val cipher : Cipher
    private val secretKeySpec : SecretKeySpec

    constructor(secretKey : String) {
        this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        this.secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "AES")
    }

    fun encriptar(data : String): String {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun desencriptar(data : String) : String {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val encryptedBytes = Base64.getDecoder().decode(data)
        val decryptedString = String(cipher.doFinal(encryptedBytes))
        return decryptedString
    }

    fun desencriptarBD(archivo : String) : List<String> {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val uri = javaClass.getResource("${Ficheros.RESOURCES_PATH}$archivo")!!.toURI()
        val encryptedBytes = Base64.getDecoder().decode(Files.readAllBytes(Paths.get(uri)))
        val decryptedString = String(cipher.doFinal(encryptedBytes))
        return decryptedString.split("\n")
    }
}
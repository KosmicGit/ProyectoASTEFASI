package com.astefasi.interfazastefasi.gestion.actualizador

import io.github.cdimascio.dotenv.Dotenv
import org.kohsuke.github.GitHub
import java.io.FileOutputStream
import java.net.URI
import java.net.URL
import java.nio.channels.Channel
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

class UpdaterHandler {

    val OWNER = "KosmicGit"
    val REPOSITORY_NAME = "ProyectoASTEFASI"
    val PERSONAL_TOKEN = Dotenv.load().get("personalToken")

    val github : GitHub = GitHub.connectUsingOAuth(PERSONAL_TOKEN)
    val repositorio = github.getRepository("$OWNER/$REPOSITORY_NAME")

    fun obtenerVersion() : String {
        val ultimoRelease = repositorio.latestRelease
        return ultimoRelease.tagName
    }

    fun actualizar() {
        val release = repositorio.latestRelease
        var appUrl = release.assets.get(0).browserDownloadUrl

        val url = URI(appUrl).toURL()
        val readableByteChannel = Channels.newChannel(url.openStream())
        val fileOutputStream = FileOutputStream("astefasi.exe")
        fileOutputStream.channel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE)
        fileOutputStream.close()
    }
}
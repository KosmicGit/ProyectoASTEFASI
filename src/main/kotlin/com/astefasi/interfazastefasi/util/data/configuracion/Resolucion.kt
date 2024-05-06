package com.astefasi.interfazastefasi.util.data.configuracion

import com.astefasi.interfazastefasi.util.AjustesAplicacion

enum class Resolucion {
    ADAPTADA(AjustesAplicacion.tamanioPantalla.getWidth() * 0.7, AjustesAplicacion.tamanioPantalla.getHeight() * 0.7),
    FULLHD(1920.0, 1080.0),
    HD(1080.0, 720.0);

    val anchura : Double
    val altura : Double
    constructor(anchura : Double, altura : Double) {
        this.anchura = anchura
        this.altura = altura
    }
}
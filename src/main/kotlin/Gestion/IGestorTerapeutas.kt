package es.cifpvirgen.Gestion

import es.cifpvirgen.Datos.Terapeuta

interface IGestorTerapeutas {

    fun borrarTerapeuta(terapeuta: Terapeuta)

    fun obtenerTerapeuta(id: Int): Terapeuta?

    fun obtenerTerapeutaIdUsuario(idUsuario: Int): Terapeuta?

    fun modificarTerapeuta(terapeutaOriginal: Terapeuta, datosNuevos: Terapeuta)

    fun obtenerTerapeutas(): ArrayList<Terapeuta>
}
package com.astefasi.interfazastefasi.util.data.user

enum class Roles {
    PACIENTE,
    TERAPEUTA,
    ADMINISTRADOR;

    override fun toString(): String {
        return name.lowercase()
    }
}
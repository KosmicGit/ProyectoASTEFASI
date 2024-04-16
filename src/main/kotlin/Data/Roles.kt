package es.cifpvirgen.Data

enum class Roles {
    PACIENTE,
    TERAPEUTA,
    ADMINISTRADOR;

    override fun toString(): String {
        return name.lowercase()
    }
}
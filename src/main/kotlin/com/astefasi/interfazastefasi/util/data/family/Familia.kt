package com.astefasi.interfazastefasi.util.data.family

data class Familia(val  id : Int, var nombreF : String ) {
    lateinit var dni : String
    lateinit var  parentesco: String

    constructor(id: Int, nombreF: String,dni :String, parentesco : String) :this(id,nombreF) {
        this.dni = dni
        this.parentesco = parentesco
    }
}

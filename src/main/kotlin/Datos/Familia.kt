package es.cifpvirgen.Datos

data class Familia(val  id : Int, var nombreF : String ) {
    constructor(id: Int, nombreF: String,dni :String, parentesco : String) :this(id,nombreF)

}
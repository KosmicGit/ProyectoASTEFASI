package com.astefasi.interfazastefasi.util.data.cache

import java.io.Serializable

data class Cache(var email : String, var password : String, var mantenerSesion : Boolean) : Serializable

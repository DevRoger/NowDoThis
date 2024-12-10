package com.example.nowdothis_test2.Clases

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Clase Usuario adaptada para manejar ambos casos
data class Usuario(
    @SerializedName("id") // Usado en "asignadoA" del JSON
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellido") // Campos adicionales, opcionales en este caso
    val apellido: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("contrasena")
    val contrasena: String? = null,

    @SerializedName("rol")
    val rol: String? = null,

    @SerializedName("telefono")
    val telefono: String? = null,

    @SerializedName("fechaIngreso")
    val fechaIngreso: String? = null
) : Serializable

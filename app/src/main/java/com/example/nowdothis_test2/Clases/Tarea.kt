package com.example.nowdothis_test2.Clases

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tarea(
    @SerializedName("id")
    val id: Int,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("estadoTarea")
    val estadoTarea: String,

    @SerializedName("asignadoA")
    val asignadoA: List<UsuarioAsignado>,

    @SerializedName("fechaInicio")
    val fechaInicio: String? = null,

    @SerializedName("fechaFin")
    val fechaFin: String? = null,

    @SerializedName("fechaFinEstimada")
    val fechaFinEstimada: String? = null,

    @SerializedName("prioridad")
    val prioridad: String,

    @SerializedName("tipoTarea")
    val tipoTarea: String
) : Serializable


data class UsuarioAsignado(
    @SerializedName("idUsuario")
    val idUsuario: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("horasTrabajadas")
    val horasTrabajadas: Double
) : Serializable
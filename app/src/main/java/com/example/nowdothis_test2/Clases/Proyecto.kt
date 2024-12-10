package com.example.nowdothis_test2.Clases

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Proyecto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("fechaInicio")
    val fechaInicio: String? = null,

    @SerializedName("fechaFinEstimada")
    val fechaFinEstimada: String? = null,

    @SerializedName("tareas")
    val tareas: List<Tarea> = emptyList()
) : Serializable

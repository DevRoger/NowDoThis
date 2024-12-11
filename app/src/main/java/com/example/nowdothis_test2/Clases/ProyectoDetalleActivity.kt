package com.example.nowdothis_test2.Clases

import TareasAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nowdothis_test2.R

class ProyectoDetalleActivity : AppCompatActivity() {

    private lateinit var tareasSinHacer: MutableList<Tarea>
    private lateinit var tareasEnProceso: MutableList<Tarea>
    private lateinit var tareasCompletadas: MutableList<Tarea>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proyecto_detalle)

        // Inicializamos las listas de tareas
        tareasSinHacer = mutableListOf()
        tareasEnProceso = mutableListOf()
        tareasCompletadas = mutableListOf()

        // Recupera el Proyecto del Intent
        val proyecto = intent.getSerializableExtra("proyecto") as Proyecto

        // Organizamos las tareas según su estado
        for (tarea in proyecto.tareas) {
            when (tarea.estadoTarea) {
                "Sin hacer" -> tareasSinHacer.add(tarea)
                "En proceso" -> tareasEnProceso.add(tarea)
                "Completado" -> tareasCompletadas.add(tarea)
            }
        }

        // Log para depurar
        Log.d("ProyectoDetalleActivity", "Tareas: ${proyecto.tareas}")

        // Configura los RecyclerViews
        val recyclerViewSinHacer: RecyclerView = findViewById(R.id.rvSinHacer)
        val recyclerViewEnProceso: RecyclerView = findViewById(R.id.rvEnProceso)
        val recyclerViewCompletado: RecyclerView = findViewById(R.id.rvCompletado)

        recyclerViewSinHacer.layoutManager = LinearLayoutManager(this)
        recyclerViewEnProceso.layoutManager = LinearLayoutManager(this)
        recyclerViewCompletado.layoutManager = LinearLayoutManager(this)


        // Establece los adaptadores
        recyclerViewSinHacer.adapter = TareasAdapter(tareasSinHacer)
        recyclerViewEnProceso.adapter = TareasAdapter(tareasEnProceso)
        recyclerViewCompletado.adapter = TareasAdapter(tareasCompletadas)

        setupDragAndDrop(recyclerViewSinHacer, tareasSinHacer, "Sin hacer")
        setupDragAndDrop(recyclerViewEnProceso, tareasEnProceso, "En proceso")
        setupDragAndDrop(recyclerViewCompletado, tareasCompletadas, "Completado")



        // Log para depuracion
        Log.d("ProyectoDetalleActivity", "Tareas Sin Hacer: $tareasSinHacer")
        Log.d("ProyectoDetalleActivity", "Tareas En Proceso: $tareasEnProceso")
        Log.d("ProyectoDetalleActivity", "Tareas Completadas: $tareasCompletadas")
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setupDragAndDrop(recyclerView: RecyclerView, listaDestino: MutableList<Tarea>, estado: String) {
        recyclerView.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> true
                DragEvent.ACTION_DRAG_ENTERED -> true
                DragEvent.ACTION_DRAG_LOCATION -> true
                DragEvent.ACTION_DRAG_EXITED -> true
                DragEvent.ACTION_DROP -> {
                    val tareaArrastrada = event.localState as Tarea

                    // Encuentra la lista origen
                    val listaOrigen = when (tareaArrastrada.estadoTarea) {
                        "Sin hacer" -> tareasSinHacer
                        "En proceso" -> tareasEnProceso
                        "Completado" -> tareasCompletadas
                        else -> null
                    }

                    if (listaOrigen != null && listaOrigen.contains(tareaArrastrada)) {
                        // Mueve la tarea entre listas
                        listaOrigen.remove(tareaArrastrada)
                        listaDestino.add(tareaArrastrada)

                        // Actualiza el estado de la tarea
                        tareaArrastrada.estadoTarea = estado

                        // Notifica a los adaptadores
                        recyclerView.adapter?.notifyDataSetChanged()
                        when (listaOrigen) {
                            tareasSinHacer -> findViewById<RecyclerView>(R.id.rvSinHacer).adapter?.notifyDataSetChanged()
                            tareasEnProceso -> findViewById<RecyclerView>(R.id.rvEnProceso).adapter?.notifyDataSetChanged()
                            tareasCompletadas -> findViewById<RecyclerView>(R.id.rvCompletado).adapter?.notifyDataSetChanged()
                        }
                    }
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> true
                else -> false
            }
        }
    }



}


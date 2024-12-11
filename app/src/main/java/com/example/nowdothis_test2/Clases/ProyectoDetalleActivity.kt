package com.example.nowdothis_test2.Clases

import TareasAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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

        // Asocia el ItemTouchHelper para permitir el arrastre entre columnas
        itemTouchHelper.attachToRecyclerView(recyclerViewSinHacer)
        itemTouchHelper.attachToRecyclerView(recyclerViewEnProceso)
        itemTouchHelper.attachToRecyclerView(recyclerViewCompletado)

        // Log para depuracion
        Log.d("ProyectoDetalleActivity", "Tareas Sin Hacer: $tareasSinHacer")
        Log.d("ProyectoDetalleActivity", "Tareas En Proceso: $tareasEnProceso")
        Log.d("ProyectoDetalleActivity", "Tareas Completadas: $tareasCompletadas")
    }


    // Aquí va el ItemTouchHelper que permite mover las tareas entre columnas
    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, // Permitimos mover las tareas de arriba a abajo
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // Permitimos arrastrar las tareas entre las columnas (de izquierda a derecha)
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            // Mover la tarea dentro de la lista de tareas
            val tarea = when (recyclerView.id) {
                R.id.rvSinHacer -> tareasSinHacer
                R.id.rvEnProceso -> tareasEnProceso
                R.id.rvCompletado -> tareasCompletadas
                else -> mutableListOf()
            }

            val tareaMovida = tarea[fromPosition]
            tarea.removeAt(fromPosition)
            tarea.add(toPosition, tareaMovida)

            // Cambiar el estado de la tarea si es necesario
            when (recyclerView.id) {
                R.id.rvSinHacer -> tareaMovida.estadoTarea = "Sin hacer"
                R.id.rvEnProceso -> tareaMovida.estadoTarea = "En proceso"
                R.id.rvCompletado -> tareaMovida.estadoTarea = "Completado"
            }

            // Notificar al Adapter que los datos han cambiado
            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // No lo necesitamos para este caso
        }
    })
}


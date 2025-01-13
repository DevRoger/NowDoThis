import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.nowdothis_test2.Clases.Proyecto
import com.example.nowdothis_test2.Clases.ProyectoDetalleActivity
import com.example.nowdothis_test2.R

class ProyectoAdapter(private val context: Context, private val proyectos: List<Proyecto>) :
    BaseAdapter() {

    override fun getCount(): Int = proyectos.size

    override fun getItem(position: Int): Proyecto = proyectos[position]

    override fun getItemId(position: Int): Long = proyectos[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_proyecto, parent, false)

        val proyecto = getItem(position)

        // Vistazos a los TextViews en el layout item_proyecto
        val tvNombreProyecto = view.findViewById<TextView>(R.id.tvNombreProyecto)
        val tvDescripcionProyecto = view.findViewById<TextView>(R.id.tvDescripcionProyecto)
        val tvFechasProyecto = view.findViewById<TextView>(R.id.tvFechasProyecto)
        val tvTareasProyecto = view.findViewById<TextView>(R.id.tvTareasProyecto)
        val tvTareasProyectoAdicionales =
            view.findViewById<TextView>(R.id.tvTareasProyectoAdicionales)

        // Asignando los valores al TextView
        tvNombreProyecto.text = proyecto.nombre
        tvDescripcionProyecto.text = proyecto.descripcion

        // Fechas
        val fechaInicio =
            proyecto.fechaInicio // Asumiendo que 'fechaInicio' es una propiedad de tipo String
        val fechaFinEstimada =
            proyecto.fechaFinEstimada // Asumiendo que 'fechaFinEstimada' es una propiedad de tipo String
        tvFechasProyecto.text =
            "Inicio: $fechaInicio - Fin estimado: $fechaFinEstimada"


        // Mostrar las primeras 5 tareas
        val tareasTextoLimitadas = proyecto.tareas.take(5).joinToString("\n") { tarea ->
            "- ${tarea.descripcion} (${tarea.estadoTarea})"
        }
        tvTareasProyecto.text = tareasTextoLimitadas

        // Mostrar las tareas adicionales si hay más de 5
        if (proyecto.tareas.size > 5) {
            val tareasAdicionales = "+ ${proyecto.tareas.size - 5} tareas adicionales"
            tvTareasProyectoAdicionales.text = tareasAdicionales
        } else {
            tvTareasProyectoAdicionales.text = ""  // Vacío si no hay tareas adicionales
        }


        view.setOnClickListener {
            // Aquí es donde se inicia la actividad
            val intent = Intent(context, ProyectoDetalleActivity::class.java)
            intent.putExtra("proyecto", proyecto)  // Pasamos el proyecto

            // Asegúrate de que el contexto no sea null
            if (context is Activity) {
                context.startActivity(intent)  // Usar el contexto para iniciar la actividad
            } else {
                Log.e("ProyectoAdapter", "Error: El contexto no es una actividad.")
            }
        }

        return view
    }
}

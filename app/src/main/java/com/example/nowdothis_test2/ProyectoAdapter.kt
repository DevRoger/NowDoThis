import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.nowdothis_test2.Clases.Proyecto
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

        // Asignando los valores al TextView
        tvNombreProyecto.text = proyecto.nombre
        tvDescripcionProyecto.text = proyecto.descripcion

        // Fechas
        val fechaInicio =
            proyecto.fechaInicio // Asumiendo que 'fechaInicio' es una propiedad de tipo String
        val fechaFinEstimada =
            proyecto.fechaFinEstimada // Asumiendo que 'fechaFinEstimada' es una propiedad de tipo String
        tvFechasProyecto.text =
            "Fecha de inicio: $fechaInicio - Fecha de fin estimada: $fechaFinEstimada"


        // Lista de tareas (puedes modificar esto si tienes más detalles de las tareas)
        val tareas = proyecto.tareas.joinToString("\n") { tarea ->
            "${tarea.descripcion} (${tarea.estadoTarea})"
        }
        tvTareasProyecto.text = "Tareas:\n$tareas"

        return view
    }
}

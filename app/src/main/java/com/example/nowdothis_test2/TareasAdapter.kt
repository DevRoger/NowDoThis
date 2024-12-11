import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nowdothis_test2.Clases.Tarea
import com.example.nowdothis_test2.R

class TareasAdapter(private val tareas: MutableList<Tarea>) : RecyclerView.Adapter<TareasAdapter.TareaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = tareas[position]
        holder.bind(tarea)
    }

    override fun getItemCount(): Int = tareas.size

    fun updateTareas(newTareas: List<Tarea>) {
        tareas.clear()
        tareas.addAll(newTareas)
        notifyDataSetChanged()
    }

    inner class TareaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionTarea)
        private val tvEstado: TextView = itemView.findViewById(R.id.tvEstadoTarea)

        fun bind(tarea: Tarea) {
            tvDescripcion.text = tarea.descripcion ?: "No hay descripción"
            tvEstado.text = tarea.estadoTarea ?: "No hay descripción"

            // Configura el arrastre
            itemView.setOnLongClickListener {
                val clipData = ClipData.newPlainText("id", adapterPosition.toString())
                val shadow = View.DragShadowBuilder(itemView)

                // Inicia el arrastre
                itemView.startDragAndDrop(clipData, shadow, tarea, 0)
                true
            }
        }
    }

}

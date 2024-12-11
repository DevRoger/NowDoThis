import android.content.ClipData
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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
            actualizarEstadoTarea(tvEstado, tarea.estadoTarea ?: "No hay descripción", tarea)

            // Configura el arrastre
            itemView.setOnLongClickListener {
                val clipData = ClipData.newPlainText("id", adapterPosition.toString())
                val shadow = View.DragShadowBuilder(itemView)

                // Inicia el arrastre
                itemView.startDragAndDrop(clipData, shadow, tarea, 0)
                true
            }
        }

        private fun actualizarEstadoTarea(textView: TextView, estado: String, tarea: Tarea) {

            // Establece el texto
            textView.text = tarea.estadoTarea ?: "No hay descripción"

            // Crea el fondo redondeado y cambia el color según el estado
            val shapeDrawable = GradientDrawable().apply {
                cornerRadius = 40f // Radio de las esquinas
            }

            shapeDrawable.setStroke(2, Color.BLACK) // Grosor de 2dp y color negro

            when (estado) {
                "Completado" -> {
                    shapeDrawable.setColor(Color.parseColor("#4CAF50")) // Fondo verde
                    textView.setTextColor(Color.WHITE) // Texto blanco
                }
                "En proceso" -> {
                    shapeDrawable.setColor(Color.parseColor("#FFD300")) // Fondo amarillo
                    textView.setTextColor(Color.BLACK) // Texto negro
                }
                "Sin hacer" -> {
                    shapeDrawable.setColor(Color.parseColor("#F44336")) // Fondo rojo
                    textView.setTextColor(Color.WHITE) // Texto blanco
                }
                else -> {
                    shapeDrawable.setColor(Color.parseColor("#9E9E9E")) // Fondo gris
                    textView.setTextColor(Color.BLACK) // Texto negro
                }
            }

            // Aplica el fondo redondeado al TextView
            textView.background = shapeDrawable
        }

    }

}

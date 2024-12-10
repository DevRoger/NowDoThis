import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.nowdothis_test2.Proyecto
import com.example.nowdothis_test2.R

class ProyectoAdapter(private val context: Context, private val proyectos: List<Proyecto>) : BaseAdapter() {

    override fun getCount(): Int = proyectos.size

    override fun getItem(position: Int): Proyecto = proyectos[position]

    override fun getItemId(position: Int): Long = proyectos[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_proyecto, parent, false)

        val proyecto = getItem(position)

        val tvNombreProyecto = view.findViewById<TextView>(R.id.tvNombreProyecto)
        val tvDescripcionProyecto = view.findViewById<TextView>(R.id.tvDescripcionProyecto)

        tvNombreProyecto.text = proyecto.nombre
        tvDescripcionProyecto.text = proyecto.descripcion

        return view
    }
}

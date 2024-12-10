package com.example.nowdothis_test2.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.nowdothis_test2.R
import com.example.nowdothis_test2.Usuario

class UserFragment : Fragment(R.layout.fragment_user) {
    private var user: Usuario? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Recuperamos el objeto 'user' desde los argumentos del fragmento
        user = arguments?.getSerializable("user") as? Usuario

        // Inflamos el layout para el fragmento
        return inflater.inflate(R.layout.fragment_user, container, false)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val txtUser: TextView = view.findViewById(R.id.txtUser)
        val txtId: TextView = view.findViewById(R.id.txtId)
        val txtEmail: TextView = view.findViewById(R.id.txtEmail)
        val txtRol: TextView = view.findViewById(R.id.txtRol)
        val txtTlf: TextView = view.findViewById(R.id.txtTlf)
        val txtFechaIngreso: TextView = view.findViewById(R.id.txtFechaIngreso)



        user?.let {
            txtUser.text = it.nombre
            txtId.text = "Id de usuario: " + it.id.toString()
            txtEmail.text = "Correo: " + it.email
            txtRol.text = "Rol: " + it.rol
            txtTlf.text = "Telefono: " + it.telefono
            txtFechaIngreso.text = "Fecha de ingreso: " + it.fechaIngreso
        }
    }
}
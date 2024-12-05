package com.example.nowdothis_test2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.nowdothis_test2.R
import com.example.nowdothis_test2.Usuario

class UserFragment: Fragment(R.layout.fragment_user) {
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var txtUser: TextView = view.findViewById(R.id.txtUser)

        user?.let {
            txtUser.text = it.nombre
        }
    }
}
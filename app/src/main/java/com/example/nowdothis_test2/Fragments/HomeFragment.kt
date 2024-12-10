package com.example.nowdothis_test2.Fragments

import ProyectoAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.nowdothis_test2.Clases.Proyecto
import com.example.nowdothis_test2.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val proyectos = arguments?.getSerializable("proyectos") as? List<Proyecto>

        proyectos?.let {
            val listView = rootView.findViewById<ListView>(R.id.listView)
            val adapter = ProyectoAdapter(requireContext(), it)
            listView.adapter = adapter
        }

        return rootView
    }
}
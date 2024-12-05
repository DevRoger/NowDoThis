package com.example.nowdothis_test2.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nowdothis_test2.GetStarted
import com.example.nowdothis_test2.R

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private var previousPosition = -1 // Variable para almacenar la opción previamente seleccionada
    private var isSpinnerTouched = false // Bandera para saber si el usuario ha tocado el Spinner
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtSobre: TextView = view.findViewById(R.id.txtSobre)

        txtSobre.text = Html.fromHtml("- <b>Versión:</b> 1.01.01<br>" +
                "- <b>Licencia:</b> Licenza Apache 2.0 (open source)<br>" +
                "- <b>Compartir NowDoThis:</b> Condividi la tua esperienza con gli amici e aiutaci a far crescere la community!<br>" +
                "- <b>Califica NowDoThis:</b> Lascia una recensione sul Play Store per supportarci e aiutarci a migliorare!")

        val btnSingOut: Button = view.findViewById(R.id.btnSignOut)
        btnSingOut.setOnClickListener() {
            val intent = Intent(requireActivity(), GetStarted::class.java)
            startActivity(intent)
        }


        // Configuración del Spinner usando findViewById
        val spinner = view.findViewById<Spinner>(R.id.spinnerLanguage) // Encuentra el Spinner usando el ID
        val idiomas: Array<String> = arrayOf("Español", "Inglés", "Catalán")

        // Creamos un adapter para el Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, idiomas)
        spinner.adapter = adapter

        // Configuramos el listener para el Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                // Verificar si el usuario ha tocado el spinner y si la posición ha cambiado
                if (isSpinnerTouched && position != previousPosition) {
                    val opcionSeleccionada = parentView?.getItemAtPosition(position).toString()
                    Toast.makeText(
                        requireContext(),
                        "Seleccionaste: $opcionSeleccionada",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Actualizamos la posición anterior
                    previousPosition = position
                } else {
                    // Marcamos que el spinner ha sido tocado
                    isSpinnerTouched = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}
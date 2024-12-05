package com.example.nowdothis_test2.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.nowdothis_test2.GetStarted
import com.example.nowdothis_test2.R

class SettingsFragment: Fragment(R.layout.fragment_settings) {
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
    }
}
package com.example.nowdothis_test2

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class GetStarted : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started)

        // Lectura de archivos JSON
        val usuarios = leerUsuariosJson(this)


        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)

        val edtxtUser = findViewById<EditText>(R.id.edtxtUser)
        val edtxtPassword = findViewById<EditText>(R.id.edtxtPassword)
        val btnSigIn = findViewById<Button>(R.id.btnSignIn)
        val imgFondo = findViewById<ImageView>(R.id.imageFondo)


        btnGetStarted.setOnClickListener() {
            // Animación para que el botón desaparezca lentamente (desvanezca) durante 5 segundos
            btnGetStarted.animate()
                .alpha(0f) // Establece la opacidad a 0 (totalmente invisible)
                .setDuration(1000) // Duración de 5 segundos
                .withEndAction {
                    // Aquí puedes hacer cualquier acción después de la animación, por ejemplo, ocultar el botón
                    btnGetStarted.visibility = View.GONE

                    // Animación de aparición de los EditText (fade-in)
                    edtxtUser.visibility = View.VISIBLE
                    edtxtPassword.visibility = View.VISIBLE
                    btnSigIn.visibility = View.VISIBLE
                    ObjectAnimator.ofFloat(edtxtUser, "alpha", 0f, 1f).apply {
                        duration = 800
                        start()
                    }

                    ObjectAnimator.ofFloat(edtxtPassword, "alpha", 0f, 1f).apply {
                        duration = 800
                        start()
                    }

                    ObjectAnimator.ofFloat(btnSigIn, "alpha", 0f, 1f).apply {
                        duration = 800
                        start()
                    }
                    // Cambiar los colores de la animación aquí
                    ObjectAnimator.ofObject(
                        imgFondo, "backgroundColor", ArgbEvaluator(),
                        Color.parseColor("#D1A7D6"), Color.parseColor("#F2E7F3")
                    ).apply {
                        duration = 800
                        start()
                    }
                }
        }


        btnSigIn.setOnClickListener {
            val username = edtxtUser.text.toString()
            val password = edtxtPassword.text.toString()

            // Primero verificamos si el usuario es "admin" con la contraseña "123456"
            if (username == "admin" && password == "123456") {
                Toast.makeText(
                    this,
                    "Inicio de sesión como administrador correcto!",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                return@setOnClickListener // Salimos del listener para evitar verificar la lista de usuarios
            }

            // Si no es el caso de "admin", buscamos en la lista de usuarios
            val user = usuarios.find { it.nombre == username && it.contrasena == password }

            if (user != null) {
                Toast.makeText(this, "Inicio de sesión correcto!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Nombre de usuario o contraseña incorrectos!",
                    Toast.LENGTH_SHORT
                ).show()
                edtxtUser.setBackgroundColor(Color.parseColor("#FFAAAA"))
                edtxtPassword.setBackgroundColor(Color.parseColor("#FFAAAA"))
            }
        }
    }

    private fun leerUsuariosJson(context: Context): List<Usuario> {
        try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.usuarios)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.readText()

            val gson = Gson()
            return gson.fromJson(jsonString, Array<Usuario>::class.java).toList()
        } catch (e: Exception) {
            return emptyList()
        }
    }
}

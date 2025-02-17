package com.example.nowdothis_test2

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nowdothis_test2.Clases.Usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class GetStarted : AppCompatActivity() {
    private val AES_KEY = "1234567890123456" // Clave de cifrado

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started)

        // Lectura de archivos JSON con contraseñas encriptadas
        val usuarios = leerUsuariosJson(this)

        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        val edtxtUser = findViewById<EditText>(R.id.edtxtUser)
        val edtxtPassword = findViewById<EditText>(R.id.edtxtPassword)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val imgFondo = findViewById<ImageView>(R.id.imageFondo)

        btnGetStarted.setOnClickListener {
            // Animaciones de transición
            val translateAnimation = TranslateAnimation(0f, 0f, 0f, -imgFondo.height.toFloat())
            translateAnimation.duration = 2000
            val alphaAnimation = AlphaAnimation(1f, 0f)
            alphaAnimation.duration = 2000
            val animationSet = AnimationSet(true).apply {
                addAnimation(translateAnimation)
                addAnimation(alphaAnimation)
            }
            imgFondo.startAnimation(animationSet)

            // Animación de fade-out del botón
            btnGetStarted.animate().alpha(0f).setDuration(1000).withEndAction {
                btnGetStarted.visibility = View.GONE
                imgFondo.visibility = View.GONE

                // Fade-in para los campos de usuario y contraseña
                listOf(edtxtUser, edtxtPassword, btnSignIn).forEach {
                    it.visibility = View.VISIBLE
                    ObjectAnimator.ofFloat(it, "alpha", 0f, 1f).apply {
                        duration = 800
                        start()
                    }
                }
            }
        }

        btnSignIn.setOnClickListener {
            val username = edtxtUser.text.toString()
            val password = edtxtPassword.text.toString()

            // Comprobación para el usuario "admin"
            if (username == "admin" && password == "123456") {
                Toast.makeText(this, "Inicio de sesión como administrador correcto!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // Verificar usuarios desencriptando la contraseña
            val user = usuarios.find { it.nombre == username && it.contrasena?.let { pass -> desencriptarAES(pass) } == password }

            if (user != null) {
                Toast.makeText(this, "Inicio de sesión correcto!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Nombre de usuario o contraseña incorrectos!", Toast.LENGTH_SHORT).show()
                edtxtUser.setBackgroundResource(R.drawable.rounded_edit_text_background_wrong)
                edtxtPassword.setBackgroundResource(R.drawable.rounded_edit_text_background_wrong)
            }
        }
    }

    // Metodo para leer y parsear el JSON encriptado
    private fun leerUsuariosJson(context: Context): List<Usuario> {
        return try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.usuarios_encriptado)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.readText()
            val gson = Gson()
            val type = object : TypeToken<List<Usuario>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Metodo para desencriptar la contraseña con AES
    private fun desencriptarAES(encryptedText: String): String {
        return try {
            val keySpec = SecretKeySpec(AES_KEY.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)

            val decodedValue = Base64.decode(encryptedText, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(decodedValue)
            String(decryptedBytes).trim()
        } catch (e: Exception) {
            ""
        }
    }
}
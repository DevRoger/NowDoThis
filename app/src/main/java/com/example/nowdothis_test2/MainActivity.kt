package com.example.nowdothis_test2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nowdothis_test2.Clases.Proyecto
import com.example.nowdothis_test2.Clases.Usuario
import com.example.nowdothis_test2.Fragments.HomeFragment
import com.example.nowdothis_test2.Fragments.SettingsFragment
import com.example.nowdothis_test2.Fragments.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recuperamos user
        val user = intent.getSerializableExtra("user") as? Usuario

        // Leemos el JSON proyectos
        val proyectos: List<Proyecto> = leerProyectosDesdeJson()

        Log.i("Proyectos", proyectos.toString()) // Verificación de la lectura de proyectos.json

        // Configura el BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Quitar tinte predeterminado a los icons
        bottomNavigationView.itemIconTintList = null

        // Configura el comportamiento de los clics en los ítems
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    user?.let {
                        openFragment(HomeFragment(), it, proyectos)  // Pasamos 'user' al fragmento
                    }
                    true
                }

                R.id.nav_user -> {
                    user?.let {
                        openFragment(UserFragment(), it, proyectos)  // Pasamos 'user' al fragmento
                    }
                    true
                }

                R.id.nav_settings -> {
                    user?.let {
                        openFragment(
                            SettingsFragment(),
                            it,
                            proyectos
                        )  // Pasamos 'user' al fragmento
                    }
                    true
                }

                else -> false
            }
        }

        // Cargar el fragmento por defecto (HomeFragment)
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_home
        }
    }

    // Método para cargar fragmentos
    private fun openFragment(fragment: Fragment, user: Usuario, proyectos: List<Proyecto>) {
        val bundle = Bundle()
        bundle.putSerializable("user", user)  // Pasa el objeto 'user' al Bundle
        bundle.putSerializable("proyectos", ArrayList(proyectos))
        fragment.arguments = bundle  // Establece el Bundle en el fragmento

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun leerProyectosDesdeJson(): List<Proyecto> {
        val inputStream: InputStream = resources.openRawResource(R.raw.proyectos)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val json = bufferedReader.use { it.readText() }
        val gson = Gson()
        val tipoListaProyectos = object : TypeToken<List<Proyecto>>() {}.type
        return gson.fromJson(json, tipoListaProyectos)
    }
}

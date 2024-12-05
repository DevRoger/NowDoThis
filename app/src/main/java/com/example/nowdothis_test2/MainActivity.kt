package com.example.nowdothis_test2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nowdothis_test2.Fragments.HomeFragment
import com.example.nowdothis_test2.Fragments.SettingsFragment
import com.example.nowdothis_test2.Fragments.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recuperamos user
        val user = intent.getSerializableExtra("user") as? Usuario

        // Configura el BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Configura el comportamiento de los clics en los ítems
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    user?.let {
                        openFragment(HomeFragment(), it)  // Pasamos 'user' al fragmento
                    }
                    true
                }
                R.id.nav_user -> {
                    user?.let {
                        openFragment(UserFragment(), it)  // Pasamos 'user' al fragmento
                    }
                    true
                }
                R.id.nav_settings -> {
                    user?.let {
                        openFragment(SettingsFragment(), it)  // Pasamos 'user' al fragmento
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
    private fun openFragment(fragment: Fragment, user: Usuario) {
        val bundle = Bundle()
        bundle.putSerializable("user", user)  // Pasa el objeto 'user' al Bundle
        fragment.arguments = bundle  // Establece el Bundle en el fragmento

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

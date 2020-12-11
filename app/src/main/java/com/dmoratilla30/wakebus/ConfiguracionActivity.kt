package com.dmoratilla30.wakebus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ConfiguracionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animationPlayer.anim.setAnimationRight(this)
        setContentView(R.layout.activity_configuracion)

        //supportFragmentManager.beginTransaction().replace(R.id.settings_container, MySettingsFragment()).commit()


        //val values = resources.getStringArray(R.array.settings_list_preference_values)




        val botonHome: FloatingActionButton = findViewById(R.id.cambiarHome)
        botonHome.setOnClickListener{
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }
}
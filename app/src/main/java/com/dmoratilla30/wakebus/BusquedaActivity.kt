package com.dmoratilla30.wakebus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class BusquedaActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       animationPlayer.anim.setAnimationRight(this)
       setContentView(R.layout.activity_busqueda)
       alarmPlayer.alarma.stopAudio()


       val botonBuscar: Button = findViewById(R.id.buscarButton)
       botonBuscar.setOnClickListener {
           Toast.makeText(this, "Buscando...", Toast.LENGTH_SHORT).show()

           val paradaRecibida: TextView = findViewById(R.id.inputParada)
           val parada = paradaRecibida.getText().toString()

           val intent = Intent(this, ParadasActivity::class.java)
           intent.putExtra("numParada", parada)
           startActivity(intent)

       }

       val botonHome: FloatingActionButton = findViewById(R.id.cambiarHome)
       botonHome.setOnClickListener{
           val intentHome = Intent(this, HomeActivity::class.java)
           startActivity(intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
       }
   }
}




package com.dmoratilla30.wakebus

import android.app.ActivityOptions
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

open class DestinoActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       animationPlayer.anim.setAnimationRight(this)
       setContentView(R.layout.activity_destino)
       alarmPlayer.alarma.stopAudio()

       val botonEstablecerAlarma: Button = findViewById(R.id.establecerAlarmaButton)
       botonEstablecerAlarma.setOnClickListener {

           val paradaRecibida: TextView = findViewById(R.id.inputParada)
           val parada = paradaRecibida.getText().toString()

           val databaseAccess = DatabaseAccess.getInstance(this)
           databaseAccess.open()
           val list: MutableList<String> = ArrayList()
           val cursor: Cursor = databaseAccess.database.rawQuery("SELECT * FROM paradas where cod_parada='${parada}'", null)
           cursor.moveToFirst()
           while (!cursor.isAfterLast) {
               list.add(cursor.getString(0))
               list.add(cursor.getString(1))
               list.add(cursor.getString(2))
               Log.d("Añadiendo destino:", cursor.getString(1) + " " + cursor.getString(2))
               cursor.moveToNext()
           }
           cursor.close()
           databaseAccess.close()

           val miPara = list.elementAtOrNull(0)
           val miLati = list.elementAtOrNull(1)
           val miLong = list.elementAtOrNull(2)

           if (parada != ""){
               Toast.makeText(this, "Seleccionando destino...", Toast.LENGTH_SHORT).show()
               val intentLocal = Intent(this, LocalizacionActivity::class.java)
               intentLocal.putExtra("numParada", miPara)
               intentLocal.putExtra("latitud", miLati)
               intentLocal.putExtra("longitud", miLong)
               startActivity(intentLocal, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
           } else {
               Toast.makeText(this, "Introduzca una parada.", Toast.LENGTH_SHORT).show()
           }
       }

       val botonHome: FloatingActionButton = findViewById(R.id.cambiarHome)
       botonHome.setOnClickListener{
           val intentHome = Intent(this, HomeActivity::class.java)
           startActivity(intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
       }
   }
}




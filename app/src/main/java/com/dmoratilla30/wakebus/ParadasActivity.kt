package com.dmoratilla30.wakebus

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ParadasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animationPlayer.anim.setAnimationRight(this)
        setContentView(R.layout.activity_paradas)

        val botonHome: FloatingActionButton = findViewById(R.id.cambiarHome)
        botonHome.setOnClickListener{
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        val objetoIntent = intent
        val resultadoBusqueda = objetoIntent.getStringExtra("numParada")

        val listView = findViewById<ListView>(R.id.listView)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()

        val list: MutableList<String> = ArrayList()

        val cursor: Cursor =
            if(resultadoBusqueda != null && resultadoBusqueda.length > 0)
                databaseAccess.database.rawQuery("SELECT * FROM paradas where cod_parada='${resultadoBusqueda}'", null)
            else databaseAccess.database.rawQuery("SELECT * FROM paradas", null)

        val cursor2: Cursor = databaseAccess.database.rawQuery("SELECT cod_linea FROM correspondencia where cod_parada='${resultadoBusqueda}'", null)
        var misLineas = ""

        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            if(resultadoBusqueda != null && resultadoBusqueda.length > 0) {
                list.add("Nº Parada: " + cursor.getString(0))
                list.add("Latitud: " + cursor.getString(1))
                list.add("Longitud: " + cursor.getString(2))
                list.add("Sentido: " + cursor.getString(3))
                cursor2.moveToFirst()
                while (!cursor2.isAfterLast) {
                    misLineas=misLineas + ", " + cursor2.getString(0)
                    cursor2.moveToNext()
                }
                cursor2.close()
                list.add("Líneas: " + misLineas.substring(2))
            }
            else list.add(cursor.getString(0) + ": " + cursor.getString(1) + ", " + cursor.getString(2) + "  (" + cursor.getString(3) + ")")

            cursor.moveToNext()
        }
        cursor.close()

        databaseAccess.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter
    }
}
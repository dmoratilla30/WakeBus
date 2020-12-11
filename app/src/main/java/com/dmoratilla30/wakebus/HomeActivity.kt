package com.dmoratilla30.wakebus

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }
    private var clicked = false

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       //animationPlayer.anim.setAnimationLeft(this)
       setTitle(R.string.home)
       setContentView(R.layout.activity_home)
       alarmPlayer.alarma.stopAudio()

       val botonConfiguracion: FloatingActionButton = findViewById(R.id.cambiarConfiguracion)
       val botonLocalizacion: FloatingActionButton = findViewById(R.id.cambiarLocalizacion)
       val botonParadas: FloatingActionButton = findViewById(R.id.cambiarParadas)
       val botonBusqueda: FloatingActionButton = findViewById(R.id.cambiarBusqueda)
       val botonMas: FloatingActionButton = findViewById(R.id.mostrarMas)

       botonConfiguracion.setOnClickListener {
           val intentConfiguracion = Intent(this, ConfiguracionActivity::class.java)
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               startActivity(intentConfiguracion, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
           } else {
               startActivity(intentConfiguracion)
           }
       }
       botonLocalizacion.setOnClickListener {
           val intentLocalizacion = Intent(this, DestinoActivity::class.java)
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               startActivity(intentLocalizacion, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
           } else {
               startActivity(intentLocalizacion)
           }
       }
       botonParadas.setOnClickListener {
           val intentParadas = Intent(this, ParadasActivity::class.java)
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               startActivity(intentParadas, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
           } else {
               startActivity(intentParadas)
           }
       }
       botonBusqueda.setOnClickListener {
           val intentBusqueda = Intent(this, BusquedaActivity::class.java)
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               startActivity(intentBusqueda, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
           } else {
               startActivity(intentBusqueda)
           }
       }
       botonMas.setOnClickListener {
           onAddButtonClicked()
       }
   }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            cambiarConfiguracion.visibility = View.VISIBLE; textConfiguracion.visibility = View.VISIBLE
            cambiarLocalizacion.visibility = View.VISIBLE; textLocalizacion.visibility = View.VISIBLE
            cambiarParadas.visibility = View.VISIBLE; textParadas.visibility = View.VISIBLE
            cambiarBusqueda.visibility = View.VISIBLE; textBusqueda.visibility = View.VISIBLE
        } else{
            cambiarConfiguracion.visibility = View.INVISIBLE; textConfiguracion.visibility = View.INVISIBLE
            cambiarLocalizacion.visibility = View.INVISIBLE; textLocalizacion.visibility = View.INVISIBLE
            cambiarParadas.visibility = View.INVISIBLE; textParadas.visibility = View.INVISIBLE
            cambiarBusqueda.visibility = View.INVISIBLE; textBusqueda.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            cambiarConfiguracion.startAnimation(fromBottom); textConfiguracion.startAnimation(fromBottom)
            cambiarLocalizacion.startAnimation(fromBottom); textLocalizacion.startAnimation(fromBottom)
            cambiarParadas.startAnimation(fromBottom); textParadas.startAnimation(fromBottom)
            cambiarBusqueda.startAnimation(fromBottom); textBusqueda.startAnimation(fromBottom)
            mostrarMas.startAnimation(rotateOpen)
        } else{
            cambiarConfiguracion.startAnimation(toBottom); textConfiguracion.startAnimation(toBottom)
            cambiarLocalizacion.startAnimation(toBottom); textLocalizacion.startAnimation(toBottom)
            cambiarParadas.startAnimation(toBottom); textParadas.startAnimation(toBottom)
            cambiarBusqueda.startAnimation(toBottom); textBusqueda.startAnimation(toBottom)
            mostrarMas.startAnimation(rotateClose)
        }
    }
}




package com.dmoratilla30.wakebus

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AlarmaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animationPlayer.anim.setAnimationTop(this)
        setContentView(R.layout.activity_alarma)

        alarmPlayer.alarma.playAudio(applicationContext)
        vibratePhone()

        val botonParar: Button = findViewById(R.id.pararButton)
        botonParar.setOnClickListener {
            alarmPlayer.alarma.stopAudio()
            Toast.makeText(this, "Se ha detenido la alarma.", Toast.LENGTH_SHORT).show()
        }

        val botonHome: FloatingActionButton = findViewById(R.id.cambiarHome)
        botonHome.setOnClickListener{
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }
    fun vibratePhone() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(400)
        }
    }
}
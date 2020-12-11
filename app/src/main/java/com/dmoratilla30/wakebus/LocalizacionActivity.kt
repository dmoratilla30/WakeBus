package com.dmoratilla30.wakebus

import android.Manifest
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit


class LocalizacionActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animationPlayer.anim.setAnimationTop(this)
        setContentView(R.layout.activity_localizacion)
        alarmPlayer.alarma.stopAudio()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        Log.d("Debug:", CheckPermission().toString())
        Log.d("Debug:", isLocationEnabled().toString())

        val titulo1: TextView = findViewById(R.id.textDestino1)
        val titulo2: TextView = findViewById(R.id.textDestino2)
        val objetoIntent = intent
        val destPara = objetoIntent.getStringExtra("numParada")
        val destLati = objetoIntent.getStringExtra("latitud")
        val destLong = objetoIntent.getStringExtra("longitud")
        titulo1.text = "Se dirige a la parada " + destPara
        titulo2.text = "Destino: " + destLati + ", " + destLong

    }

    override fun onResume() {
        super.onResume()
        Log.d("Debug:", "onResume")
        val botonHome: FloatingActionButton = findViewById(R.id.cambiarHome)
        botonHome.setOnClickListener{
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        RequestPermission()
        getLastLocation()
    }

    fun getLastLocation(){
        if (CheckPermission()) {
            if (isLocationEnabled()) {
                Log.d("Debug:", "Location set")
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        NewLocationData()
                    } else {
                        val misCoord: TextView = findViewById(R.id.misCoordenadas)
                        val resultadoMetros: TextView = findViewById(R.id.id_metros)
                        Log.d("Debug:", "Your Location:" + location.latitude + location.longitude)
                        misCoord.text = "" + location.latitude + ", " + location.longitude

                        val objetoIntent = intent
                        val destLati = objetoIntent.getStringExtra("latitud")!!.toDouble()
                        val destLong = objetoIntent.getStringExtra("longitud")!!.toDouble()

                        val metros = distance_in_meter(location.latitude, location.longitude, destLati, destLong) //parada 3071
                        resultadoMetros.text = "Faltan " + metros.toInt().toString() + "m"

                        if ( metros < 100.00) {
                            Log.d("Proximidad:", "Estás cerca")
                            alarmPlayer.alarma.playAudio(applicationContext)

                            val intentAlarma = Intent(this, AlarmaActivity::class.java)
                            startActivity(intentAlarma, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

                        } else {
                            Log.d("Proximidad:", "Estás lejos")
                        }

                        NewLocationData()
                    }
                }
            } else {
                //Toast.makeText(this, "Por favor, active la localización en su dispositivo.", Toast.LENGTH_SHORT).show()
                val misCoord: TextView = findViewById(R.id.misCoordenadas)
                val resultadoMetros: TextView = findViewById(R.id.id_metros)
                Log.d("Debug:", "Location not set")
                misCoord.text = "Por favor, active la localización en su dispositivo."
                resultadoMetros.background=null
                NewLocationData()
            }
        } else {
            RequestPermission()
        }
    }

    fun NewLocationData(){

        val botonHome: FloatingActionButton = findViewById(R.id.cambiarHome)
        botonHome.setOnClickListener{
            val intentHome = Intent(this, BusquedaActivity::class.java)
            startActivity(intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = TimeUnit.SECONDS.toMillis(60)
        locationRequest.fastestInterval = TimeUnit.SECONDS.toMillis(30)
        //locationRequest.numUpdates = 1
        locationRequest.maxWaitTime = TimeUnit.MINUTES.toMillis(2)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            val misCoord: TextView = findViewById(R.id.misCoordenadas)
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.latitude + lastLocation.longitude)
            misCoord.text = "" + lastLocation.latitude + ", " + lastLocation.longitude
        }
    }

    fun CheckPermission():Boolean{
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if(
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    fun RequestPermission(){
        //this function will allows us to tell the user to request the necessary permission if they are not garented
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }


    fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:", "You have the Permission")
            }
        }
    }

    private fun distance_in_meter(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371000.0 // Radius of the earth in m
        val dLat = (lat1 - lat2) * Math.PI / 180f
        val dLon = (lon1 - lon2) * Math.PI / 180f
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1 * Math.PI / 180f) * Math.cos(lat2 * Math.PI / 180f) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2f * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c
    }

}
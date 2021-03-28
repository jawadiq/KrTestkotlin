package com.example.krtestkotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.krtestkotlin.apis.apis
import com.example.krtestkotlin.models.Main
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
  val URL : String = "https://api.darksky.net/"
    val API_KEY : String = "2bb07c3bece89caf533ac9a5d23d8417"
   var lati : Double = 59.337239
    var longi : Double = 18.062381
 lateinit var  weatherResponse:TextView
    lateinit var  city:TextView
 private var locationManager : LocationManager? = null
    lateinit var location: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    https://api.darksky.net/forecast/2bb07c3bece89caf533ac9a5d23d8417/59.337239,18.062381
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
   weatherResponse= findViewById(R.id.weather)
    city = findViewById(R.id.city)
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )

            return
        }else {
location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
            if (location!=null){
                lati = location.latitude
                longi = location.longitude

            }

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        lati = location.latitude
                        longi = location.longitude
                        city.append(lati.toString())

                    }

                }
        }
val retrofit:Retrofit = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()
        val service:apis = retrofit.create(apis::class.java)
        val call: Call<Main> = service.getModels(API_KEY, lati, longi)
       call.enqueue(object : Callback<Main> {
           override fun onResponse(call: Call<Main>?, response: Response<Main>?) {
               if (response!!.isSuccessful) {
                   val abc: Main = response.body()
                   weatherResponse.append("wind speed "+abc.currently.windSpeed.toString())
                   weatherResponse.append(System.getProperty("line.separator"))
                   weatherResponse.append("Cloud Cover   " + abc.currently.cloudCover)
                   weatherResponse.append(System.getProperty("line.separator"))
                   weatherResponse.append(" Dew Point   " + abc.currently.dewPoint)
                   weatherResponse.append(System.getProperty("line.separator"))
                   weatherResponse.append(" Humidity" + abc.currently.humidity)
                   weatherResponse.append(System.getProperty("line.separator"))
                   weatherResponse.append(" Summary   " + abc.currently.summary)

               }

           }

           override fun onFailure(call: Call<Main>?, t: Throwable?) {
               TODO("Not yet implemented")
           }

       })
    }



}
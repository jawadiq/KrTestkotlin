package com.example.krtestkotlin.models

import java.io.Serializable

data class Main (
    var latitude: Double,
    var longitude : Double,
    var timezone : String,
    var currently: Currently

) : Serializable
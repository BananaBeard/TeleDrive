package com.kovalenko.teledrive.annealing

import android.location.Location

class City(var lat: Double, var lng: Double) {

    fun distanceTo(city: City): Float {
        var locationA: Location = Location("point A")
        var locationB: Location = Location("point B")

        return locationA.distanceTo(locationB)
    }
}
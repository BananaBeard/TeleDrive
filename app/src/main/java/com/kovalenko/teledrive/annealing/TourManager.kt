package com.kovalenko.teledrive.annealing

class TourManager {

    companion object {
        private var destinationCities = ArrayList<City>()

        fun addCity(city: City) {
            destinationCities.add(city)
        }

        fun getCity(index: Int) = destinationCities[index]

        fun numberOfCities() = destinationCities.size
    }
}
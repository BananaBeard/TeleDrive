package com.kovalenko.teledrive.annealing

class Tour {

    private var tour = ArrayList<City?>()

    private var distance = 0.0

    init {
        for (i in 0..TourManager.numberOfCities()) {
            tour.add(null)
        }
    }

    constructor(tour: ArrayList<City?>) {
        this.tour = tour.clone() as ArrayList<City?>
    }

    fun getTour() = tour

    fun generateIndividual() {

        for (i in 0..TourManager.numberOfCities()) {

        }
    }
}
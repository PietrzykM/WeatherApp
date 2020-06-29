package edu.psmw.weatherapp.data.provider

import edu.psmw.weatherapp.internal.UnitSystem



interface UnitProvider {
        fun getUnitSystem(): UnitSystem
}
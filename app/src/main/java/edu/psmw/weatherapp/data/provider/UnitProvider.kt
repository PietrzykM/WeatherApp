package edu.psmw.weatherapp.data.provider

import edu.psmw.weatherapp.internal.UnitSystem

/**
 * UnitProvider Interface
 * has one function which is getting Unit system that is set by user
 */

interface UnitProvider {
        fun getUnitSystem(): UnitSystem
}
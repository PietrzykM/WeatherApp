package edu.psmw.weatherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import edu.psmw.weatherapp.internal.UnitSystem

/**
 *  Iplementacja interfejsu UnitProvider
 * pobiera dane o wyborze jednostek przez użytkownika.
 */

const val UNIT_SYSTEM = "UNIT_SYSTEM" //key from preferences.xml

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {


    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return  UnitSystem.valueOf(selectedName!!)
    }
}
package edu.psmw.weatherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import edu.psmw.weatherapp.internal.UnitSystem

/**
 * UnitProvider implementation
 * has one function which is getting Unit system that is set by user
 */

const val UNIT_SYSTEM = "UNIT_SYSTEM" //key from preferences.xml

class UnitProviderImpl(context: Context) : UnitProvider {
    private val appContext = context.applicationContext

    private val preferences :SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return  UnitSystem.valueOf(selectedName!!)
    }
}
package edu.psmw.weatherapp.internal

/**
 * Definicja wyjątków dla aplikacji
 */

import java.io.IOException

class NoConnectivityException: IOException()
class LocationPermissionNotGrantedException: Exception()
class DateNotFoundException: Exception()
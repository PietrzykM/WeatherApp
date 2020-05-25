package edu.psmw.weatherapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import edu.psmw.weatherapp.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response


class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

//    fun isNetworkConnected(): Boolean {
//        val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (cm != null) {
//            if (Build.VERSION.SDK_INT < 23) {
//                val ni = cm.activeNetworkInfo
//                if (ni != null) {
//                    return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
//                }
//            } else {
//                val n = cm.activeNetwork
//                if (n != null) {
//                    val nc = cm.getNetworkCapabilities(n)
//                    return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
//                        NetworkCapabilities.TRANSPORT_WIFI
//                    )
//                }
//            }
//        }
//        return false
//    }
    private fun isOnline():Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
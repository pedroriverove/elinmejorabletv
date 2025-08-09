package com.elinmejorabletv.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    fun getNetworkType(context: Context): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return "Sin conexión"
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return "Sin conexión"

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Datos móviles"
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> "Bluetooth"
            else -> "Desconocido"
        }
    }
}
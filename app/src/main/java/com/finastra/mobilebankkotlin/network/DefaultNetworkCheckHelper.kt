package com.finastra.mobilebankkotlin.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import androidx.annotation.NonNull
import com.finastra.mobilebankkotlin.application.MyApplication
import javax.inject.Singleton
import com.finastra.mobilebankkotlin.network.NetworkCheckHelper.ConnectionType
import javax.inject.Inject

/**
 * Helper singleton class for the network check functionality. s.g. for checking if the device is connected to the
 * internet, or get the network provider name from SIM or mobile network, etc.
 *
 * @author martin
 */
class DefaultNetworkCheckHelper constructor(application: Application) : NetworkCheckHelper {

    /**
     * A connectivity manager.
     */
    private val connectivityManager: ConnectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val currentNetworkInfo = connectivityManager.activeNetworkInfo
    /**
     * A telephony manager.
     */
    private val telephonyManager: TelephonyManager = application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    override val isNetworkConnected: Boolean get () = currentNetworkInfo != null && currentNetworkInfo.isConnected

    override fun networkConnectionTypeAsEnum(): ConnectionType {
        val currentNetworkInfo = connectivityManager.activeNetworkInfo
        if (!isNetworkConnected || currentNetworkInfo == null) {
            return ConnectionType.DISCONNECTED
        }

        when (currentNetworkInfo.type) {

            ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_WIMAX -> return ConnectionType.WIFI

            ConnectivityManager.TYPE_MOBILE -> return ConnectionType.MOBILE

            ConnectivityManager.TYPE_ETHERNET, ConnectivityManager.TYPE_BLUETOOTH -> return ConnectionType.OTHER

            else ->
                /*	When the active connection is only for the provider's inner communication. These cases are the following:
				ConnectivityManager.TYPE_MOBILE_DUN, TYPE_MOBILE_HIPRI, TYPE_MOBILE_MMS, TYPE_MOBILE_SUPL, TYPE_DUMMY */
                return ConnectionType.DISCONNECTED
        }
    }

    /**
     * Returns a constant indicating the state of the SIM card.
     *
     * @return The constant of the SIM card's state.
     */
    private fun getSimState(): Int {
        return telephonyManager.simState
    }

    override fun simStateAsString(): String {
        when (telephonyManager.simState) {
            TelephonyManager.SIM_STATE_READY -> return "SIM_STATE_READY"
            TelephonyManager.SIM_STATE_ABSENT -> return "SIM_STATE_ABSENT"
            TelephonyManager.SIM_STATE_NETWORK_LOCKED -> return "SIM_STATE_NETWORK_LOCKED"
            TelephonyManager.SIM_STATE_PIN_REQUIRED -> return "SIM_STATE_PIN_REQUIRED"
            TelephonyManager.SIM_STATE_PUK_REQUIRED -> return "SIM_STATE_PUK_REQUIRED"
        }
        return "SIM_STATE_UNKNOWN"
    }

    override fun simOperatorName(): String? {
        if (getSimState() == TelephonyManager.SIM_STATE_READY) {
            return telephonyManager.simOperatorName
        }
        return null
    }

    override fun simOperatorCode(): String? {
        if (getSimState() == TelephonyManager.SIM_STATE_READY) {
            return telephonyManager.simOperator
        }
        return null
    }

    override fun networkOperatorName(): String? {
        if (isOnCdma()){
            return telephonyManager.networkOperatorName
        }
        return null
    }

    override fun networkOperatorCode(): String? {
        if (isOnCdma()) {
            return telephonyManager.networkOperator
        }
        return null
    }

    /**
     * Determines if the phone is on a CDMA network.
     *
     * @return `true` if the device is connected to a CDMA network, `false` otherwise.
     */
    private fun isOnCdma(): Boolean {
        return telephonyManager.phoneType == TelephonyManager.PHONE_TYPE_CDMA
    }
}
package com.finastra.mobilebankkotlin.network

import android.net.ConnectivityManager
import android.telephony.TelephonyManager

/**
 * Helper singleton class for the network check functionality. s.g. for checking if the device is connected to the
 * internet, or get the network provider name from SIM or mobile network, etc.
 *
 * @author martin
 */
interface NetworkCheckHelper {

    /**
     * Determines if an active network connection is available.
     *
     * @return `true` if the device is already connected to the Internet, `false` otherwise.
     */
    val isNetworkConnected: Boolean

    /**
     * Get the [ConnectionType] equivalent enum of the currently active internet connection.
     *
     * @return Type of the currently connection.
     */
    fun networkConnectionTypeAsEnum(): ConnectionType


    /**
     * Get the state of the SIM card as a string.
     *
     * @return The SIM state
     *
     * @see TelephonyManager
     */
    fun simStateAsString(): String

    /**
     * Return the Service Provider Name (SPN). This is the name of the SIM card's service provider which is inserted in
     * the device.
     *
     * @return The mobile network operator code the device connected to. `null` if the device has not
     * connected to any mobile networks or this field of the SIM card is empty.
     */
    fun simOperatorName(): String?

    /**
     * Return code of the mobile service provider of the SIM as MCC+MNC format (mobile country code + mobile network ,
     * 5 or 6 decimal digits).
     *
     * @return The code of the SIM card's network provider.
     */
    fun simOperatorCode(): String?

    /**
     * Return the Service Provider Name (SPN). This is the name of the mobile network provider which the device is
     * currently connected to.
     *
     *
     * Availability: Only when user is registered to a network. Result will be `null` on CDMA networks.
     *
     * @return The name of the currently service provider. Result may be unreliable on CDMA networks, returns `null` in this case.
     */
    fun networkOperatorName(): String?

    /**
     * Return code of the mobile service provider which the device is currently connected to as MCC+MNC format (mobile
     * country code + mobile network , 5 or 6 decimal digits).
     *
     *
     * Availability: Only when user is registered to a network. Result may be unreliable on CDMA networks, returns
     * `null` in this case.
     *
     * @return The code of the SIM card's network provider.
     */
    fun networkOperatorCode(): String?

    /**
     * Enum class for all checked network connectivity states.
     *
     *
     * `OTHER` means that there is a live network connection, bot not `WIFI` neither `MOBILE`
     * type.
     * See [ConnectivityManager]'s attributes beginning with `TYPE_`.
     */
    enum class ConnectionType {
        /**
         * WiFi connection type.
         */
        WIFI,
        /**
         * Mobile data network connection type.
         */
        MOBILE,
        /**
         * Neither Wifi nor mobile connection, but connected.
         */
        OTHER,
        /**
         * Not connected to the internet.
         */
        DISCONNECTED
    }

}

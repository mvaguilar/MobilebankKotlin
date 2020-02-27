package com.finastra.mobilebankkotlin.application

import android.app.Application
import com.finastra.mobilebankkotlin.network.DefaultNetworkCheckHelper
import javax.inject.Inject

class NetworkCheckHelperImpl @Inject constructor() {
    private fun getNetworkHelper(application: Application): DefaultNetworkCheckHelper {
        return DefaultNetworkCheckHelper(application)
    }

    fun isNetworkConnectedResult(application: Application): String {
        if (getNetworkHelper(application).isNetworkConnected) {
            return "You are currently Online! "
        }
        return "You are currently Offline... "
    }

    fun getSimState(application: Application): String {
        return getNetworkHelper(application).simStateAsString()
    }
}
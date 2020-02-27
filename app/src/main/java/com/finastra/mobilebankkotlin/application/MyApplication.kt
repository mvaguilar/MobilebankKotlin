package com.finastra.mobilebankkotlin.application

import android.app.Activity
import android.app.Application
import com.finastra.mobilebankkotlin.application.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()

        var appComponent = DaggerAppComponent.create()
        appComponent.inject(this)
    }
}
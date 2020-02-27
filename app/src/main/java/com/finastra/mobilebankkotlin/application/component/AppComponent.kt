package com.finastra.mobilebankkotlin.application.component

import com.finastra.mobilebankkotlin.application.MyApplication
import com.finastra.mobilebankkotlin.application.module.ActivitiesModule
import dagger.Component

@Component(
    modules = [
        ActivitiesModule::class
    ]
)
interface AppComponent {
    fun inject(myApplication: MyApplication)
}
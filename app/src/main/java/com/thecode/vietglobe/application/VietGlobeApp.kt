package com.thecode.vietglobe.application

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VietGlobeApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}

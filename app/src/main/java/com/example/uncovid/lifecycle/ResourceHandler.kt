package com.example.uncovid.lifecycle

import android.content.ContentValues.TAG
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.uncovid.MainActivity

class ResourceHandler : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {

    }
}
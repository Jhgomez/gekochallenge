package com.geko.challenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChallengeApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
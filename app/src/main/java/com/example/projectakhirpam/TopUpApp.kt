package com.example.projectakhirpam

import android.app.Application
import com.example.projectakhirpam.repositori.ContainerApp
import com.example.projectakhirpam.repositori.ContainerDataApp

class TopUpApp : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}
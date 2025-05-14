package org.example.project

import android.app.Application
import org.example.project.di.initiKoin
import org.koin.android.ext.koin.androidContext


class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initiKoin {
            androidContext(this@MyApplication)

        }
    }
}
package com.mayank

import android.app.Application

class App : Application() {
    companion object {
        private var app: App? = null
        fun getInstance(): App {
            return app!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

}
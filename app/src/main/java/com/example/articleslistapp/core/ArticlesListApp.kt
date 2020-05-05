package com.example.articleslistapp.core

import android.app.Application
import com.example.articleslistapp.di.appModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ArticlesListApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //  Koin
        startKoin {
            androidContext(this@ArticlesListApp)
            modules(appModule)
        }

        //  Stetho
        Stetho.initializeWithDefaults(this)

    }
}
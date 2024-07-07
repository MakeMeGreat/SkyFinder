package com.example.skyfinder.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }
}
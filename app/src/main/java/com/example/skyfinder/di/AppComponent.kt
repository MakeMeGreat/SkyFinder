package com.example.skyfinder.di

import com.example.skyfinder.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class, RetrofitModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}
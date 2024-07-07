package com.example.skyfinder.di

import com.example.skyfinder.presentation.ui.MainActivity
import com.example.skyfinder.presentation.ui.fragment.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class, RetrofitModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
}
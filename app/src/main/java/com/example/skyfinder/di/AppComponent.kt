package com.example.skyfinder.di

import com.example.skyfinder.presentation.ui.MainActivity
import com.example.skyfinder.presentation.ui.fragment.AllTicketsFragment
import com.example.skyfinder.presentation.ui.fragment.MainFragment
import com.example.skyfinder.presentation.ui.fragment.TicketsPreviewFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class, RetrofitModule::class, CacheModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(ticketsPreviewFragment: TicketsPreviewFragment)
    fun inject(allTicketsFragment: AllTicketsFragment)
}
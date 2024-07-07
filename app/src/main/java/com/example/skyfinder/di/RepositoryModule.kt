package com.example.skyfinder.di

import com.example.data.RepositoryImpl
import com.example.domain.Repository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}
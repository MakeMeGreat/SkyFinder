package com.example.skyfinder.di


import com.example.data.network.FirstAPI
import com.example.data.network.SecondAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val FIRST_API = "https://drive.usercontent.google.com"
    private val SECOND_API = "https://drive.google.com"

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @Named("FIRST_API")
    fun provideFirstRetrofitService(moshi: Moshi): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(FIRST_API)
            .build()
    }

    @Singleton
    @Provides
    @Named("SECOND_API")
    fun provideSecondRetrofitService(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(SECOND_API)
            .build()
    }

    @Singleton
    @Provides
    fun provideFirstRetrofitApi(@Named("FIRST_API") retrofit: Retrofit): FirstAPI {
        return retrofit.create(FirstAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideSecondRetrofitApi(@Named("SECOND_API") retrofit: Retrofit): SecondAPI {
        return retrofit.create(SecondAPI::class.java)
    }
}
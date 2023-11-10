package com.myu.informationaboutbrands.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.myu.informationaboutbrands.data.repository.PreferenceStorage
import com.myu.informationaboutbrands.data.repository.ProductInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideProductInfoRepository(
        @ApplicationContext context: Context,
        preferenceStorage: PreferenceStorage,
    ): ProductInfoRepository {
        return ProductInfoRepository(context, preferenceStorage)
    }

}
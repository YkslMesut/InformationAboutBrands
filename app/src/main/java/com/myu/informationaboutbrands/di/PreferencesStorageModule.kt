package com.myu.informationaboutbrands.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.myu.informationaboutbrands.data.repository.DataStorePreferenceStorage
import com.myu.informationaboutbrands.data.repository.PreferenceStorage
import com.myu.informationaboutbrands.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PreferencesStorageModule {

    @Provides
    fun provideDataStorePreferencesName() = Constants.DATA_STORE_PREFERENCES_NAME

    @Singleton
    @Provides
    fun providePreferenceDataStore(
        @ApplicationContext context: Context,
        dataStorePreferencesName: String
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(
                SharedPreferencesMigration(
                    context,
                    dataStorePreferencesName
                )
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(dataStorePreferencesName) }
        )
    }

    @Singleton
    @Provides
    fun providePreferenceStorage(dataStore: DataStore<Preferences>): PreferenceStorage =
        DataStorePreferenceStorage(dataStore)

}
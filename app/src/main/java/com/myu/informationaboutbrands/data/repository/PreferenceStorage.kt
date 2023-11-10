package com.myu.informationaboutbrands.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.myu.informationaboutbrands.data.model.ProductItem
import com.myu.informationaboutbrands.data.repository.DataStorePreferenceStorage.DataStorePreferenceKeys.PRODUCT_LIST
import com.myu.informationaboutbrands.data.repository.DataStorePreferenceStorage.DataStorePreferenceKeys.PRODUCT_LIST_TR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type
import javax.inject.Inject


interface PreferenceStorage {

    suspend fun writeProductListTr(value: List<ProductItem>)
    val productListTr: Flow<List<ProductItem>?>

    suspend fun writeProductList(value: List<ProductItem>)
    val productList: Flow<List<ProductItem>?>

}

class DataStorePreferenceStorage @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferenceStorage {


    override val productListTr: Flow<List<ProductItem>?>
        get() {
            val productItemType = object : TypeToken<List<ProductItem>>() {}.type
            return getAnyValue(
                preferencesKey = PRODUCT_LIST_TR,
                clazz = productItemType,
                mutableListOf()
            )
        }

    override suspend fun writeProductListTr(value: List<ProductItem>) {
        setAnyValue(PRODUCT_LIST_TR, value)
    }

    override val productList: Flow<List<ProductItem>?>
        get() {
            val productItemType = object : TypeToken<List<ProductItem>>() {}.type
            return getAnyValue(
                preferencesKey = PRODUCT_LIST,
                clazz = productItemType,
                mutableListOf()
            )
        }

    override suspend fun writeProductList(value: List<ProductItem>) {
        setAnyValue(PRODUCT_LIST, value)
    }


    private fun <T> getAnyValue(
        preferencesKey: Preferences.Key<String>,
        clazz: Type,
        defaultValue: T? = null
    ): Flow<T?> {
        return dataStore.data.map { preferences ->
            val jsonObjectString = preferences[preferencesKey]
            try {
                Gson().fromJson<T>(jsonObjectString, clazz)
            } catch (e: JsonParseException) {
                defaultValue
            }
        }
    }

    private suspend fun <T> setAnyValue(
        preferencesKey: Preferences.Key<String>,
        value: T
    ) {
        dataStore.edit { preferences ->
            val data = Gson().toJson(value)
            preferences[preferencesKey] = data
        }
    }


    companion object {
        const val PREFERENCES_NAME = "data_store_preferences_name"

    }

    object DataStorePreferenceKeys {
        val PRODUCT_LIST = stringPreferencesKey("product_list")
        val PRODUCT_LIST_TR = stringPreferencesKey("product_list_tr")
    }

}
package com.myu.informationaboutbrands.data.repository

import android.content.Context
import com.myu.informationaboutbrands.data.model.ProductItem
import com.myu.informationaboutbrands.utils.Constants.LANGUAGE_TR
import com.myu.informationaboutbrands.utils.Constants.PRODUCTS_JSON
import com.myu.informationaboutbrands.utils.Constants.PRODUCTS_JSON_TR
import com.myu.informationaboutbrands.utils.fromJsonToList
import com.myu.informationaboutbrands.utils.readFromFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class ProductInfoRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferenceStorage: PreferenceStorage,
) : BaseRepository() {

    fun getProductList(): Flow<Resource<List<ProductItem>>> {
        return flow {
            emit(Resource.Loading)

            val isLanguageTr = Locale.getDefault().language == LANGUAGE_TR
            val preferenceStorageProductList =
                if (isLanguageTr) preferenceStorage.productListTr.firstOrNull() else preferenceStorage.productList.firstOrNull()

            if (preferenceStorageProductList != null) {
                emit(Resource.Success(value = preferenceStorageProductList))
            } else {
                val productListJsonFileName = if (isLanguageTr) PRODUCTS_JSON_TR else PRODUCTS_JSON
                val productListJson = context.readFromFile(productListJsonFileName)
                val productList: List<ProductItem> = productListJson.fromJsonToList()

                if (isLanguageTr) preferenceStorage.writeProductListTr(productList) else preferenceStorage.writeProductList(
                    productList
                )
                emit(Resource.Success(value = productList))

            }
        }
    }

    fun getProductDetail(id: String): Flow<Resource<ProductItem>> {
        return flow {
            emit(Resource.Loading)

            val isLanguageTr = Locale.getDefault().language == LANGUAGE_TR
            val preferenceStorageProductList =
                if (isLanguageTr) preferenceStorage.productListTr.firstOrNull() else preferenceStorage.productList.firstOrNull()

            val productItem = preferenceStorageProductList?.find { it.id == id }
            emit(Resource.Success(value = productItem!!))

        }
    }
}
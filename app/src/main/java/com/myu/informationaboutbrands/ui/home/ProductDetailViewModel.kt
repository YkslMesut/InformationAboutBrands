package com.myu.informationaboutbrands.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myu.informationaboutbrands.data.model.ProductItem
import com.myu.informationaboutbrands.data.repository.ProductInfoRepository
import com.myu.informationaboutbrands.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productInfoRepository: ProductInfoRepository
) : ViewModel() {

    private val _productResponse = MutableStateFlow<Resource<ProductItem>>(Resource.Empty)
    val productResponse: StateFlow<Resource<ProductItem>>
        get() = _productResponse


    fun getProductItemList(productId: String) = viewModelScope.launch {
        productInfoRepository.getProductDetail(id = productId).collect { resource ->
            _productResponse.value = resource
        }
    }
}
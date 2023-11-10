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
class ProductViewModel @Inject constructor(
    private val productInfoRepository: ProductInfoRepository
) : ViewModel() {

    private val _productResponse = MutableStateFlow<Resource<List<ProductItem>>>(Resource.Empty)
    val productResponse: StateFlow<Resource<List<ProductItem>>>
        get() = _productResponse


    init {
        getProductItemList()
    }

    fun getProductItemList() = viewModelScope.launch {
        productInfoRepository.getProductList().collect { resource ->
            _productResponse.value = resource
        }
    }
}
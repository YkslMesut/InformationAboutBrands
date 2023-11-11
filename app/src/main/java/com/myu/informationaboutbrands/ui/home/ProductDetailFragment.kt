package com.myu.informationaboutbrands.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.myu.informationaboutbrands.data.repository.Resource
import com.myu.informationaboutbrands.databinding.FragmentProductDetailBinding
import com.myu.informationaboutbrands.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    private val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun getViewBinding(container: ViewGroup?) =
        FragmentProductDetailBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeProductData()
        viewModel.getProductItemList(productId = args.productId)
    }

    private fun initViews() {
        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeProductData() {
        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.productResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            val item = it.value
                            binding.shimmerFrameLayout.isVisible = false
                            binding.progressBar.isVisible = false
                            binding.containerItemInfo.isVisible = true

                            createWebView(item.source)
                            binding.imageProduct.load(item.logo) {
                                transformations(CircleCropTransformation())
                            }
                            binding.txtProductTitle.text = item.name
                            binding.txtProductDescription.text = item.description
                            binding.txtProductInfoReason.text = item.reason
                        }

                        is Resource.Loading -> {
                            binding.shimmerFrameLayout.isVisible = true
                            binding.progressBar.isVisible = true
                            binding.containerItemInfo.isVisible = false
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun createWebView(url: String) {
        binding.webViewSource.webViewClient = WebViewClient()
        binding.webViewSource.loadUrl(url)
        binding.webViewSource.settings.javaScriptEnabled = true
        binding.webViewSource.settings.setSupportZoom(true)
    }
}
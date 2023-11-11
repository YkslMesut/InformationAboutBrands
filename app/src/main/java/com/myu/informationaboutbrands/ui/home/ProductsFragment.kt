package com.myu.informationaboutbrands.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myu.informationaboutbrands.R
import com.myu.informationaboutbrands.adapter.ProductAdapter
import com.myu.informationaboutbrands.customview.SearchBar
import com.myu.informationaboutbrands.data.model.ProductItem
import com.myu.informationaboutbrands.data.repository.Resource
import com.myu.informationaboutbrands.databinding.FragmentProductsBinding
import com.myu.informationaboutbrands.ui.BaseFragment
import com.myu.informationaboutbrands.utils.DividerItemDecorator
import com.myu.informationaboutbrands.utils.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {

    private val viewModel: ProductViewModel by viewModels()

    private lateinit var productAdapter: ProductAdapter


    override fun getViewBinding(container: ViewGroup?) =
        FragmentProductsBinding.inflate(layoutInflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupRecyclerView()
        observeProductList()
        observeSearchBar()
    }

    private fun initViews() {
        binding.searchBar.editTextChangeListener(object : SearchBar.EditTextChangeListener {
            override fun onTextChangeListener(text: String) {
                if (text.isNotEmpty())
                    productAdapter.filter.filter(text)
            }
        })
    }


    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(object : ProductAdapter.ProductSelectListener {
            override fun onItemClick(product: ProductItem) {
                val action =
                    ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(
                        productId = product.id
                    )
                findNavController().navigate(action)
            }

        })
        binding.rvProducts.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                DividerItemDecorator(
                    ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!
                ),
            )
            addItemDecoration(
                MarginItemDecoration(
                    marginVertical = resources.getDimensionPixelSize(R.dimen.margin_medium)
                )
            )
            setHasFixedSize(true)
        }
    }

    private fun observeProductList() {
        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.productListResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            binding.shimmerFrameLayout.isVisible = false
                            binding.progressBar.isVisible = false
                            binding.rvProducts.isVisible = true
                            productAdapter.setProductList(it.value)
                            Log.d("observeProductList", "observeProductList: " + it.toString())
                        }

                        is Resource.Loading -> {
                            binding.shimmerFrameLayout.isVisible = true
                            binding.progressBar.isVisible = true
                            binding.rvProducts.isVisible = false
                        }

                        else -> {}
                    }
                }
            }
        }
    }


    private fun observeSearchBar() {
        launchAndRepeatWithViewLifecycle {
            launch {

            }
        }
    }
}
package com.myu.informationaboutbrands.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.myu.informationaboutbrands.data.model.ProductItem
import com.myu.informationaboutbrands.databinding.RowProductBinding

class ProductAdapter(private val listener: ProductSelectListener) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>(),
    Filterable {

    private var productItemList: ArrayList<ProductItem> = ArrayList()
    private var productItemFilteredList: ArrayList<ProductItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(productList: List<ProductItem>) {
        this.productItemList = productList as ArrayList<ProductItem>
        productItemFilteredList = productList
        notifyDataSetChanged()
    }

    interface ProductSelectListener {
        fun onItemClick(
            product: ProductItem,
        )
    }

    inner class MyViewHolder(
        val binding: RowProductBinding,
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = productItemFilteredList[position]

        holder.binding.apply {
            imgProduct.load(item.logo) {
                transformations(CircleCropTransformation())
            }
            txtProductTitle.text = item.name
            txtProductDescription.text = item.description

            root.setOnClickListener {
                listener.onItemClick(product = item)
            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""

                productItemFilteredList = if (charString.isEmpty()) productItemList else {
                    val filteredList = ArrayList<ProductItem>()
                    for (basket in productItemList) {
                        if (basket.name.contains(charString, true) || basket.description.contains(
                                charString,
                                true
                            )
                        )
                            filteredList.add(basket)

                    }
                    productItemFilteredList = filteredList
                    return FilterResults().apply { values = productItemFilteredList }
                }
                return FilterResults().apply { values = productItemFilteredList }
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                productItemFilteredList = results?.values as ArrayList<ProductItem>
                notifyDataSetChanged()
            }

        }
    }

    override fun getItemCount(): Int {
        return productItemFilteredList.size
    }
}
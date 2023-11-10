package com.myu.informationaboutbrands.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.widget.addTextChangedListener
import com.myu.informationaboutbrands.R


class SearchBar(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    interface EditTextChangeListener {
        fun onTextChangeListener(text: String)
    }

    interface ActionClickListener {
        fun onActionClick()
    }

    private var bbIEditTextChangeListener: EditTextChangeListener? = null
    private var bbIActionClickListener: ActionClickListener? = null
    private var searchText: EditText? = null
    private var endIcon: ImageView? = null

    fun editTextChangeListener(listener: EditTextChangeListener) {
        this.bbIEditTextChangeListener = listener
    }

    fun setActionIconClickListener(listener: ActionClickListener) {
        this.bbIActionClickListener = listener
    }

    fun setEndIconImage(icon: Drawable) {
        this.endIcon?.setImageDrawable(icon)
    }

    fun setEditTextValue(text: String?) {
        if (!text.isNullOrEmpty()) {
            searchText?.setText(text)
            searchText?.setSelection(text.length)
        } else {
            searchText?.setText("")
        }
    }

    fun clearEditText() {
        val searchTextValue = searchText?.text.toString()
        if (!searchTextValue.isNullOrEmpty()) {
            searchText?.setText("")
            searchText?.setSelection(0)
        }
    }


    init {
        inflate(context, R.layout.search_bar, this)
        searchText = findViewById(R.id.search_text)
        endIcon = findViewById(R.id.end_icon)

        context.withStyledAttributes(attrs, R.styleable.SearchBar) {

            val isEndButtonVisible = getBoolean(R.styleable.SearchBar_isEndIconVisible, false)
            val icon = getDrawable(R.styleable.SearchBar_endIcon)


            endIcon.apply {
                visibility = if (isEndButtonVisible) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
            endIcon?.setImageDrawable(icon)

            searchText?.addTextChangedListener {
                bbIEditTextChangeListener?.onTextChangeListener(it.toString())
            }

            endIcon?.setOnClickListener {
                bbIActionClickListener?.onActionClick()
            }
        }
    }

}
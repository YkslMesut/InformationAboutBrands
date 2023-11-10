package com.myu.informationaboutbrands.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.myu.informationaboutbrands.utils.AnalyticsHelper
import com.myu.informationaboutbrands.utils.FirebaseAnalyticsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding> : Fragment(),
    AnalyticsHelper by FirebaseAnalyticsHelper() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract fun getViewBinding(container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        earlierOnViewBinding()
        _binding = getViewBinding(container)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sendScreenView(this::class.java.simpleName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun validateActivityIsBaseActivity(block: (baseActivity: BaseActivity<*>) -> Unit) {
        val activity = requireActivity()
        if (activity is BaseActivity<*>) {
            block(activity)
        } else {
            throw IllegalArgumentException("Activity must be instance of BaseActivity")
        }
    }

    open fun earlierOnViewBinding() {}

    fun showError(
        errorMessage: String
    ) {
        validateActivityIsBaseActivity {
            it.showError(errorMessage)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    inline fun launchAndRepeatWithViewLifecycle(
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline block: suspend CoroutineScope.() -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
                block()
            }
        }
    }
}
package com.myu.informationaboutbrands.ui.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.myu.informationaboutbrands.R
import com.myu.informationaboutbrands.databinding.ActivityHomeBinding
import com.myu.informationaboutbrands.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    private fun navController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeContainerView) as NavHostFragment
        return navHostFragment.navController
    }
}
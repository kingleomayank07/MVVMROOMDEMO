package com.mayank.mvvmroomdemo.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mayank.mvvmroomdemo.R
import com.mayank.mvvmroomdemo.utils.ExtFunc.get
import com.mayank.mvvmroomdemo.utils.ExtFunc.getSharedPreference

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        val isLoggedIn = requireContext().getSharedPreference.get("isLoggedIn", false)
        when {
            isLoggedIn -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }, 3000)
            }
            else -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                }, 3000)
            }
        }
    }

}
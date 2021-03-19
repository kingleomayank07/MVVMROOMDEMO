package com.mayank.mvvmroomdemo.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mayank.mvvmroomdemo.data.UserRepository
import com.mayank.mvvmroomdemo.viewmodel.UserViewModel

class ViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(UserRepository(app)) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown class name")
            }
        }
    }
}
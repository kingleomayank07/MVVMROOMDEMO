package com.mayank.mvvmroomdemo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayank.mvvmroomdemo.data.User
import com.mayank.mvvmroomdemo.data.UserRepository
import com.mayank.mvvmroomdemo.utils.Resource
import kotlinx.coroutines.launch


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val mUser = MutableLiveData<Resource<User>>()

    fun insertUser(user: User) {
        try {
            viewModelScope.launch {
                userRepository.insertUser(user)!!
            }
        } catch (e: Exception) {
            Log.d("TAG", "insertUser: ${e.message}")
        }
    }

    fun getUser(username: String): MutableLiveData<Resource<User>> {
        mUser.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val user = userRepository.getUser(username)
                mUser.postValue(Resource.success(user))
            } catch (e: Exception) {
                mUser.postValue(Resource.error(e.message!!, null))
            }
        }
        return mUser
    }

}
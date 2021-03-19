package com.mayank.mvvmroomdemo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mayank.App
import com.mayank.mvvmroomdemo.R
import com.mayank.mvvmroomdemo.data.User
import com.mayank.mvvmroomdemo.utils.ExtFunc.getSharedPreference
import com.mayank.mvvmroomdemo.utils.ExtFunc.hideShowView
import com.mayank.mvvmroomdemo.utils.ExtFunc.set
import com.mayank.mvvmroomdemo.utils.Resource
import com.mayank.mvvmroomdemo.utils.Status
import com.mayank.mvvmroomdemo.utils.ViewModelFactory
import com.mayank.mvvmroomdemo.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login), View.OnClickListener {

    private val _loginViewModel: UserViewModel by
    viewModels(factoryProducer = { ViewModelFactory(App.getInstance()) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_signup.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    private fun observeUser(user: MutableLiveData<Resource<User>>) {
        user.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    pg.hideShowView(false)
                    if (edit_password.text.toString() == it.data?.password) {
                        requireContext().getSharedPreference["isLoggedIn"] = true
                        requireContext().getSharedPreference["username"] =
                            edt_username.text.toString()
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    } else {
                        Snackbar.make(pg, "Incorrect username or password", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
                Status.ERROR -> {
                    pg.hideShowView(false)
                }
                Status.LOADING -> {
                    pg.hideShowView(true)
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Unexpected error occurred!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt_signup -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }
            R.id.btn_login -> {
                val userName = edt_username.text.toString()
                val user = _loginViewModel.getUser(userName)
                observeUser(user)
            }
        }
    }

}
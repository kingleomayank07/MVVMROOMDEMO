package com.mayank.mvvmroomdemo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mayank.App
import com.mayank.mvvmroomdemo.R
import com.mayank.mvvmroomdemo.data.User
import com.mayank.mvvmroomdemo.utils.Status
import com.mayank.mvvmroomdemo.utils.ViewModelFactory
import com.mayank.mvvmroomdemo.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : Fragment(R.layout.fragment_signup), View.OnClickListener {

    private val _userViewModel: UserViewModel by
    viewModels(factoryProducer = { ViewModelFactory(App.getInstance()) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_signup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_signup -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        val username = edt_username.text.toString()
        val password = edit_password.text.toString()
        val age = age.text.toString().toInt()
        val existingUser = _userViewModel.getUser(username)
        existingUser.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    when {
                        it.data?.userName != username -> {
                            if (username.isNotEmpty() && password.isNotEmpty()) {
                                val user =
                                    User(
                                        0,
                                        age,
                                        username,
                                        password
                                    )
                                _userViewModel.insertUser(user)
                            }
                        }
                        else -> {
                            Snackbar.make(
                                edt_username,
                                "User already exists!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.loginFragment)
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
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

}
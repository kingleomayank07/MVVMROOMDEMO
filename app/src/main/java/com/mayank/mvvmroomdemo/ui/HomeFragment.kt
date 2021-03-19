package com.mayank.mvvmroomdemo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.mayank.App
import com.mayank.mvvmroomdemo.R
import com.mayank.mvvmroomdemo.data.User
import com.mayank.mvvmroomdemo.utils.ExtFunc.get
import com.mayank.mvvmroomdemo.utils.ExtFunc.getSharedPreference
import com.mayank.mvvmroomdemo.utils.ExtFunc.set
import com.mayank.mvvmroomdemo.utils.Resource
import com.mayank.mvvmroomdemo.utils.Status
import com.mayank.mvvmroomdemo.utils.ViewModelFactory
import com.mayank.mvvmroomdemo.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private val _loginViewModel: UserViewModel by
    viewModels(factoryProducer = { ViewModelFactory(App.getInstance()) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = requireContext().getSharedPreference.get("username", "")
        if (username.isNotEmpty()) {
            val user = _loginViewModel.getUser(username)
            setUI(user)
        }
    }

    private fun setUI(user: MutableLiveData<Resource<User>>) {
        user.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    edt_username.text = it.data?.userName?.prependIndent("USERNAME : ")
                    edit_password.text = it?.data?.password?.prependIndent(("PASSWORD : "))
                    edt_UID.text = it?.data?.age.toString().prependIndent("AGE : ")
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
        logout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.logout -> {
                requireContext().getSharedPreference["isLoggedIn"] = false
                requireContext().getSharedPreference["username"] = ""
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
    }

}
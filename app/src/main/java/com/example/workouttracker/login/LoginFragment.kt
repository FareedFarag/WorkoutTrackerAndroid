package com.example.workouttracker.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.workouttracker.R
import com.example.workouttracker.SharedPreference
import com.example.workouttracker.SharedViewModel
import com.example.workouttracker.database.UserDatabase
import com.example.workouttracker.database.UserRepository
import com.example.workouttracker.databinding.FragmentLoginBinding
import kotlin.system.exitProcess

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UserRepository(dao)
        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            var doubleBackToExitPressedOnce = false
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    exitProcess(0)
//                    super.onBackPressed()
                    Log.d("pref", "back")
                    return
                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(activity, "Click back again to exit", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            }
        })

        loginViewModel.navigateToRegister.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                displayUsersList()
                loginViewModel.doneNavigatingRegister()
            }
        })

        loginViewModel.errorToast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.doneToast()
            }
        })

        loginViewModel.errorToastUsername.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Username doesn't exist!", Toast.LENGTH_SHORT).show()
                loginViewModel.doneToastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show()
                loginViewModel.doneToastInvalidPassword()
            }
        })

        loginViewModel.navigateToUserDetails.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                navigateUserDetails()
                loginViewModel.doneNavigatingUserDetails()
            }
        })

        return binding.root
    }

    private fun displayUsersList() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

    private fun navigateUserDetails() {
        val action = LoginFragmentDirections.actionLoginFragmentToUserDetailsFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

}
package com.example.workouttracker.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.workouttracker.R
import com.example.workouttracker.database.UserDatabase
import com.example.workouttracker.database.UserRepository
import com.example.workouttracker.databinding.FragmentRegisterBinding
import com.example.workouttracker.register.RegisterViewModel
import com.example.workouttracker.register.RegisterViewModelFactory


class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UserRepository(dao)
        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.registerViewModel = registerViewModel
        binding.lifecycleOwner = this

        registerViewModel.navigateTo.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                displayUsersList()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.navigateToLogin.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                displayUsersList()
                registerViewModel.doneNavigatingLogin()
            }
        })

//        registerViewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
//        })


        registerViewModel.errorToast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError == true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                registerViewModel.doneToast()
            }
        })

        registerViewModel.errorToastUsername.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Username Already taken", Toast.LENGTH_SHORT).show()
                registerViewModel.doneToastUserName()
            }
        })

        return binding.root
    }

    private fun displayUsersList() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}
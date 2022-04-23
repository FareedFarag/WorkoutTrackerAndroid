package com.example.workouttracker.userDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.workouttracker.R
import com.example.workouttracker.SharedPreference
import com.example.workouttracker.database.UserDatabase
import com.example.workouttracker.database.UserRepository
import com.example.workouttracker.databinding.FragmentUserDetailsBinding
import com.example.workouttracker.jogLog.MapsActivity
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlin.system.exitProcess

class UserDetailsFragment : Fragment() {

    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var binding: FragmentUserDetailsBinding
    var sharedPreference:SharedPreference? = null
//        this.activity?.getSharedPreferences("secret", Context.MODE_PRIVATE)
//        this.activity?.getPreferences(Context.MODE_PRIVATE)
//        this.requireActivity().getPreferences(Context.MODE_PRIVATE)
//        .getSharedPreferences("pref", Context.MODE_PRIVATE);
//        this.activity?.let { SharedPreference(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_details, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UserRepository(dao)
        val factory = UserDetailsViewModelFactory(repository, application)

        userDetailsViewModel =
            ViewModelProvider(this, factory).get(UserDetailsViewModel::class.java)

        binding.userDetailsViewModel = userDetailsViewModel
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
                Toast.makeText(activity, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            }
        })

        userDetailsViewModel.navigateTo.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val action = UserDetailsFragmentDirections.actionUserDetailsFragmentToLoginFragment()
                NavHostFragment.findNavController(this).navigate(action)
                userDetailsViewModel.doneNavigating()
            }
        })

        userDetailsViewModel.switchActivity.observe(viewLifecycleOwner) { switched ->
            if (switched == true) {
                val intent = Intent(activity, MapsActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = this.activity?.let { SharedPreference(it) }

        val firstName = sharedPreference?.getPreferenceString("firstName")

        if (firstName != null) {
            textView.text = "Hello $firstName!"
        }

    }
}
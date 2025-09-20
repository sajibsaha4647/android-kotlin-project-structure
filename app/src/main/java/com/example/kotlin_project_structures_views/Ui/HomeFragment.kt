package com.example.kotlin_project_structures_views.Ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.kotlin_project_structures_views.R
import com.example.kotlin_project_structures_views.databinding.FragmentHomeBinding


class Home : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = requireActivity().window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.blue)
    }


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val username = arguments?.getString("username")
        val age = arguments?.getInt("age")

        val myinfo: MutableMap<String, Any> = mutableMapOf()

        // Store key-value pairs
        if (username != null) {
            myinfo["username"] = username
        }
        if (age != null) {
            myinfo["age"] = age
        }
        // Print map
        println("here is key value $myinfo")  // Example output: {username=sajib, age=12}

        binding.goAboutId.setOnClickListener {
            println("press on go about id")
            findNavController().navigate(R.id.aboutFragment)
        }

        binding.goAccont.setOnClickListener {
            println("press on go account id")
            findNavController().navigate(R.id.accountFragment);
         }


    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null ;
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }





}
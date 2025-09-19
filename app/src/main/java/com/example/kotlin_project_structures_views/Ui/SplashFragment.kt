package com.example.kotlin_project_structures_views.Ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlin_project_structures_views.R



class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_splash, container, false)
        Handler(Looper.getMainLooper()).postDelayed({

            val bundle = Bundle().apply {
                putString("username", "Sajib")
                putInt("age", 25)
            }
//            findNavController().navigate(R.id.secondFragment, bundle)

            findNavController().navigate(R.id.home2, bundle)
        },3000)

        return view ;

    }

}
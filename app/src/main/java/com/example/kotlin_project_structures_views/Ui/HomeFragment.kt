package com.example.kotlin_project_structures_views.Ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.kotlin_project_structures_views.R


class Home : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = requireActivity().window

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.blue)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =   inflater.inflate(R.layout.fragment_home, container, false)
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




//        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

        return view ;
    }



//    override fun onDestroy() {
//        super.onDestroy()
//
//        val window = requireActivity().window
//        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
//    }





}
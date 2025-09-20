package com.example.kotlin_project_structures_views.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.kotlin_project_structures_views.R
import com.example.kotlin_project_structures_views.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private var _binding : FragmentAboutBinding? =null ;
    private val binding get() = _binding ;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAboutBinding.inflate(inflater,container,false)

        val toolbar = _binding?.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        // Enable back/up button
        val navController = findNavController()
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            navController
        )

        return _binding?.root ;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}
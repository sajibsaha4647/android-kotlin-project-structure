package com.example.kotlin_project_structures_views.Ui.Fragments
import com.example.kotlin_project_structure_new.R
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.kotlin_project_structure_new.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {


    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!


    private lateinit var tab1: TextView
    private lateinit var tab2: TextView
    private lateinit var content1: View
    private lateinit var content2: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        val toolbar = _binding?.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        // Enable back/up button
        val navController = findNavController()
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            navController
        )


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("Account is called")

        fun Int.dpToPx(): Int {
            return (this * resources.displayMetrics.density).toInt()
        }

        val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)

        val originalDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.menu)
        originalDrawable?.let { drawable ->
            // Convert dp to px
            val size = 20.dpToPx()

            // Wrap drawable in BitmapDrawable to set custom size
            val bitmap = (drawable as BitmapDrawable).bitmap
            val resizedDrawable =
                BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, size, size, true))

            // Set navigation icon
            binding?.toolbar?.navigationIcon = resizedDrawable

            // Click listener
            binding?.toolbar?.setNavigationOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

        }

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        // Enable back/up button with Navigation Component
        val navController = findNavController()
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            navController
        )


        tab1 = binding.tab1
        tab2 = binding.tab2
        content1 = binding.content1
        content2 = binding.content2

        // Set default selected tab
        selectTab(1)

        // Click listeners
        tab1.setOnClickListener { selectTab(1) }
        tab2.setOnClickListener { selectTab(2) }



    }


    private fun selectTab(tabNumber: Int) {
        when(tabNumber) {
            1 -> {
                content1.visibility = View.VISIBLE
                content2.visibility = View.GONE
                tab1.setBackgroundColor(Color.LTGRAY)   // Highlight selected
                tab2.setBackgroundColor(Color.TRANSPARENT)
            }
            2 -> {
                content1.visibility = View.GONE
                content2.visibility = View.VISIBLE
                tab1.setBackgroundColor(Color.TRANSPARENT)
                tab2.setBackgroundColor(Color.LTGRAY)   // Highlight selected
            }
        }
    }

}
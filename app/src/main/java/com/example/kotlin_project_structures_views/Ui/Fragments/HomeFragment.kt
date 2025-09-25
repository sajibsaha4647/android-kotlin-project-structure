package com.example.kotlin_project_structures_views.Ui.Fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_project_structure_new.databinding.FragmentHomeBinding
import com.example.kotlin_project_structures_views.ViewModel.HomeViewModel
import com.example.kotlin_project_structure_new.R
import com.example.kotlin_project_structure_new.Ui.main.Phone_call
import com.example.kotlin_project_structures_views.Adapdars.HomeAdapter
import com.example.kotlin_project_structures_views.Model.HomeResponseModel

class Home : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = requireActivity().window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.blue)

        // ✅ Register listener early with fragment lifecycle
        parentFragmentManager.setFragmentResultListener(
            "about_request",
            this  // use fragment lifecycle, not viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getString("about_result")
            println("checking result here back result $result")
            Toast.makeText(requireContext(), "Result: $result", Toast.LENGTH_SHORT).show()
        }

    }

    private lateinit var homeAdapter: HomeAdapter


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val PREFS_NAME = "my_prefs"
    private val KEY_USERNAME = "username"

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("HomeFragment is called")

        //here is sharepreference
        var sharedPref = requireContext().getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPref.edit().putString(KEY_USERNAME, "sajib");
        editor.apply()

        var usernamea = sharedPref.getString(KEY_USERNAME, "default_value")
        println("checking username $usernamea")



        val dialog = DialogFragment()
        dialog.show(requireActivity().supportFragmentManager, "my_dialog")


//        parentFragmentManager.setFragmentResultListener(
//            "about_request",  // request key
//            viewLifecycleOwner
//        ) { _, bundle ->
//            val result = bundle.getString("about_result") // bundle key
//            println("checking result here back result $result")
//            Toast.makeText(requireContext(), "Result: $result", Toast.LENGTH_SHORT).show()
//        }

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
            binding.toolbar.navigationIcon = resizedDrawable

            // Click listener
            binding.toolbar.setNavigationOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

        }

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

//        binding.goAboutId.setOnClickListener {
//            println("press on go about id")
//            findNavController().navigate(R.id.aboutFragment)
//        }
//
//        binding.goAccont.setOnClickListener {
//            println("press on go account id")
//            findNavController().navigate(R.id.accountFragment);
//         }

        binding.goPhoneCall.setOnClickListener {
            println("press on go phone call id")
            val intent = Intent(requireContext(), Phone_call::class.java);
            startActivity(intent);

            // Optional: finish current activity so user can't come back here
//            requireActivity().finish();
        }
        binding.googleWebView.setOnClickListener {
            findNavController().navigate(R.id.webViewFragment)
        }
        checkPermission()

        binding.addButtonId.setOnClickListener {
//            viewModel.addItem(HomeResponseModel)
        }


        viewModel.items.observe(viewLifecycleOwner) { items ->
            homeAdapter = HomeAdapter(items, object : HomeAdapter.OnTaskActionListener {
                override fun onEditClick(position: Int, home: HomeResponseModel) {
                   viewModel.removeItem(home);
                }

                override fun onDeleteClick(position: Int, home: HomeResponseModel) {
                    viewModel.updateItem(position,home.name);
                }
            })

            binding.recyclerViewHomeId.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewHomeId.adapter = homeAdapter
        }

        binding.addButtonId.setOnClickListener {
            val newItem = HomeResponseModel(
                id = viewModel.items.value?.size?.plus(1) ?: 1,
                name = "New Item ${viewModel.items.value?.size?.plus(1)}"
            )
            viewModel.addItem(newItem)
        }


        viewModel.items.observe(viewLifecycleOwner) { items ->
            // Update adapter’s data
            homeAdapter.updateData(items)
        }











    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // ✅ Permission granted → do your work
                Toast.makeText(requireContext(), "Camera granted", Toast.LENGTH_SHORT).show()
            } else {
                // ❌ Permission denied
                Toast.makeText(requireContext(), "Camera denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // ✅ Already granted
                Toast.makeText(requireContext(), "Already granted", Toast.LENGTH_SHORT).show()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // ℹ️ Show why you need it (dialog/snackbar)
                Toast.makeText(requireContext(), "Camera is required!", Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }

            else -> {
                // 🚀 Ask directly
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }


    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d("Permission", "${it.key} = ${it.value}")
            }
        }

    fun askMultiple() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null ;
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }





}
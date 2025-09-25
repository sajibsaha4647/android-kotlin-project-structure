package com.example.kotlin_project_structures_views.Ui.Fragments
import com.example.kotlin_project_structure_new.R
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_project_structure_new.databinding.FragmentAboutBinding
import com.example.kotlin_project_structures_views.Adapdars.PostAdapter
import com.example.kotlin_project_structures_views.ViewModel.HomeViewModel
import com.example.kotlin_project_structures_views.ViewModel.PostViewModel
import kotlinx.coroutines.launch


class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!  // Non-nullable getter

    private val viewModel: PostViewModel by viewModels()

    private var adapter: PostAdapter? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        val navController = findNavController()
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            navController
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

        val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
        val originalDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.menu)
        originalDrawable?.let { drawable ->
            val size = 20.dpToPx()
            val bitmap = (drawable as BitmapDrawable).bitmap
            val resizedDrawable =
                BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, size, size, true))
            binding.toolbar.navigationIcon = resizedDrawable
            binding.toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            sendResultAndGoBack()
        }

        binding.toolbar.setNavigationOnClickListener {
            sendResultAndGoBack()
        }

        setupRecyclerView();
        setupObservers();

        viewModel.fetchPosts(requireActivity());


//            viewModel.posts.observe(viewLifecycleOwner) { posts ->
//
//                 adapter = PostAdapter(posts)
//                binding.recyclerViewHomePostId.layoutManager =
//                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//                binding.recyclerViewHomePostId.adapter = adapter
//                if (posts != null) {
//                    adapter?.updateData(posts)
//                }
//            }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.posts.collect { posts ->
//                    postAdapter.updateData(posts)
//                }
//            }
//        }


//        viewModel.error.observe(viewLifecycleOwner) { error ->
////                binding.errorTextView.text = error
//            }
//
//            viewModel.loadingPosts.observe(viewLifecycleOwner) { isLoading ->
//                binding.ProgressBarId.visibility = if (isLoading) View.VISIBLE else View.GONE
//            }






        }


    private fun setupRecyclerView() {
        adapter = PostAdapter(emptyList())
        binding.recyclerViewHomePostId.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHomePostId.adapter = adapter
    }

    private fun setupObservers() {
        // Collect posts from Flow
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.posts.collect { posts ->
                    adapter?.updateData(posts)
                }
            }
        }

        // Observe errors if you want
        viewModel.error.observe(viewLifecycleOwner) { error ->
            // handle error, e.g., show Toast or TextView
        }

        // Observe loading
        viewModel.loadingPosts.observe(viewLifecycleOwner) { isLoading ->
            binding.ProgressBarId.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }



    private fun sendResultAndGoBack() {
        val result = Bundle().apply { putString("about_result", "Hello from AboutFragment") }
        parentFragmentManager.setFragmentResult("about_request", result)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

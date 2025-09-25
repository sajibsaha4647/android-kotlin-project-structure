package com.example.kotlin_project_structures_views.Ui.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.example.kotlin_project_structure_new.databinding.FragmentWebViewBinding
import com.example.kotlin_project_structure_new.R

class WebViewFragment : Fragment() {

    private var _binding : FragmentWebViewBinding? = null ;
    private  val binding get() = _binding;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebViewBinding.inflate(inflater,container,false);
        return _binding?.root;
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable JavaScript
        binding?.webview?.settings?.javaScriptEnabled = true

        // Optional: enable zoom controls
        binding?.webview?.settings?.setSupportZoom(true)
        binding?.webview?.settings?.builtInZoomControls = true
        binding?.webview?.settings?.displayZoomControls = false
        binding?.webview?.loadUrl("https://www.google.com")

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding?.webview?.canGoBack() == true) {
                binding?.webview?.goBack()
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }



    // Properly handle WebView lifecycle
    override fun onPause() {
        super.onPause()


        binding?.webview?.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding?.webview?.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.webview?.destroy()
        _binding = null
    }



}
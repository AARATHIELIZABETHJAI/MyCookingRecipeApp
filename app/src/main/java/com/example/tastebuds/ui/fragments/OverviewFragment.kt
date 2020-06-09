package com.example.tastebuds.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.tastebuds.databinding.FragmentOverviewBinding
import com.example.tastebuds.viewmodel.AppViewModel
import com.example.tastebuds.viewmodel.AppViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {
    private lateinit var binding:FragmentOverviewBinding
    private  lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentOverviewBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            AppViewModelFactory(requireActivity().application)
        ).get(
            AppViewModel::class.java)
        binding.viewModel = viewModel
//        var test = TextView(context)
//        test.text = "CHECKKKKKKKKKKK"
//        overViewLayout.addView(test)
//        viewModel.recipeDetail.observe(viewLifecycleOwner, Observer {  })
    }
}

package com.example.tastebuds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tastebuds.databinding.FragmentOverviewBinding

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
        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        binding.viewModel = viewModel
//        viewModel.recipeDetail.observe(viewLifecycleOwner, Observer {  })
    }

}

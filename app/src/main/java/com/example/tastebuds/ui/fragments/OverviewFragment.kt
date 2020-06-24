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
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment(),KodeinAware {
    private lateinit var binding:FragmentOverviewBinding
    private  lateinit var viewModel: AppViewModel
    override val kodein: Kodein by kodein()
    private val appViewModelFactory: AppViewModelFactory by instance()


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
        viewModel = ViewModelProvider(requireActivity(),appViewModelFactory
        ).get(
            AppViewModel::class.java)
        binding.viewModel = viewModel
//        var test = TextView(context)
//        test.text = "CHECKKKKKKKKKKK"
//        overViewLayout.addView(test)
//        viewModel.recipeDetail.observe(viewLifecycleOwner, Observer {  })
    }
}

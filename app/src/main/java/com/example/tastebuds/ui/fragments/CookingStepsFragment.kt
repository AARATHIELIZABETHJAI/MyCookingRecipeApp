package com.example.tastebuds.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.tastebuds.databinding.FragmentCookingStepsBinding
import com.example.tastebuds.viewmodel.AppViewModel
import com.example.tastebuds.viewmodel.AppViewModelFactory
import kotlinx.android.synthetic.main.fragment_cooking_steps.*

/**
 * A simple [Fragment] subclass.
 */
class CookingStepsFragment : Fragment() {

    private lateinit var binding: FragmentCookingStepsBinding
    private lateinit var viewModel: AppViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCookingStepsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            AppViewModelFactory(requireActivity().application)
        ).get(AppViewModel::class.java)
        binding.viewModel = viewModel
        var cookingSteps = viewModel.getCookingSteps()
        for (i in 0..(cookingSteps.size - 1)) {
            var textView = TextView(context)
            var layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.layoutParams = layoutParams
            textView.text = "${i + 1}) ${cookingSteps[i]}"
            textView.setPadding(15, 10, 3, 0)
            textView.setTextSize(25F)
            textView.setTextColor(Color.parseColor("#000000"))
            cookingStepsLayout.addView(textView)
        }
    }
}

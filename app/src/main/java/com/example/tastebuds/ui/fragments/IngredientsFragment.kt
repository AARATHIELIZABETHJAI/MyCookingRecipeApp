package com.example.tastebuds.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tastebuds.R
import com.example.tastebuds.databinding.FragmentIngredientsBinding
import com.example.tastebuds.ui.adapters.ShoppingList
import com.example.tastebuds.viewmodel.AppViewModel
import com.example.tastebuds.viewmodel.AppViewModelFactory
import kotlinx.android.synthetic.main.fragment_ingredients.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


/**
 * A simple [Fragment] subclass.
 */
class IngredientsFragment : Fragment(),KodeinAware {
    private lateinit var binding: FragmentIngredientsBinding
    private lateinit var viewModel: AppViewModel
    override val kodein: Kodein by kodein()
    private val appViewModelFactory: AppViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),appViewModelFactory).get(AppViewModel::class.java)
        binding.viewModel = viewModel
        val extendedIngredients = viewModel.recipeDetail.value?.extendedIngredients
        if(extendedIngredients!=null)
        {
            for (i in 0 until extendedIngredients.size)
            {
                val checkBox = CheckBox(context)
                checkBox.text = extendedIngredients[i].original
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                checkBox.textSize = 15F
                params.leftMargin  = 40
                checkBox.layoutParams = params
                ingredientsLayout.addView(checkBox)
                val shoppingList = ShoppingList(extendedIngredients[i].name)
                checkBox.setOnCheckedChangeListener(){buttonView, isChecked ->if (isChecked)
                {

                    viewModel.insertToShoppingList(shoppingList)
                }
                else
                {
                    viewModel.deleteFromShoppingList(shoppingList)
                }
                }
            }
        }
    }
}

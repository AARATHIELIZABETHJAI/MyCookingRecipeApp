package com.example.tastebuds

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

//https://api.spoonacular.com/recipes/search?query=yogurt&apiKey=beb23c70f0264a09b60b9f0aa44b1ea5
const val NUMBER_OF_RESULTS = 10;
const val APIKEY: String = "beb23c70f0264a09b60b9f0aa44b1ea5"

class AppViewModel : ViewModel() {
    /*brackets of Viewmodel means calling constructor of view model which is necessary*/
    lateinit var liveResponse: LiveData<ArrayList<Result>>
    private var _recipesList = MutableLiveData<ArrayList<Result>>()
    val recipeList: LiveData<ArrayList<Result>>
        get() = _recipesList

    private var _recipeDetail = MutableLiveData<RecipeDetail>()
    val recipeDetail: LiveData<RecipeDetail>
        get() = _recipeDetail

    var prevQuery: String? = null
    fun searchRecipe(recipeName: String) {
        if((prevQuery==null) || (recipeName !== prevQuery) ){
            _recipesList = UserRepository().searchRecipe(recipeName, APIKEY, NUMBER_OF_RESULTS)
            prevQuery = recipeName
        }
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _navigateToDetail = MutableLiveData<String>()
    val navigateToDetail: LiveData<String>
        get() = _navigateToDetail

    fun onRecipeItemtClicked(recipeId: Int) {
        _navigateToDetail.value = "Clicked"
        searchRecipebyId(recipeId)
    }

    fun onRecipeDetailNavigated() {
        if (_navigateToDetail.value != null) {
            _navigateToDetail.value = null
        }
    }

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)


    fun searchRecipebyId(recipeId: Int) {
        coroutineScope.launch {
            var serviceResponse = UserRepository().searchRecipebyId(recipeId, APIKEY)
            withContext(Dispatchers.Main){
                when (serviceResponse) {
                    is ServiceResponse.Success -> {
                        _recipeDetail.value = serviceResponse.data
                    }
                    is ServiceResponse.Error -> {
                        errorMessage.postValue(serviceResponse.exception)
                    }
                }
            }
        }
    }
}


//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }

package com.example.tastebuds.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tastebuds.data.network.responses.Result
import com.example.tastebuds.data.network.responses.ServiceResponse
import com.example.tastebuds.data.repository.UserRepository
import com.example.tastebuds.persistence.AppDataBase
import com.example.tastebuds.persistence.models.RecipeDetail
import com.example.tastebuds.ui.adapters.ShoppingList
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

//https://api.spoonacular.com/recipes/search?query=yogurt&apiKey=beb23c70f0264a09b60b9f0aa44b1ea5
const val NUMBER_OF_RESULTS = 10
const val APIKEY: String = "beb23c70f0264a09b60b9f0aa44b1ea5"

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allfavouriteRecipes: LiveData<List<RecipeDetail>>
    val shoppingListItems : LiveData<List<ShoppingList>>

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private var _errorloading = MutableLiveData<Boolean>()
    val errorloading: LiveData<Boolean>
        get() = _errorloading

    init {
        val favouriteRecipesDao = AppDataBase.getInstance(
            application
        ).getFavouriteRecipesDao()
        val shoppingListDao = AppDataBase.getInstance(
            application
        ).getShoppingListDao()
        repository = UserRepository(
            favouriteRecipesDao,
            shoppingListDao
        )
        allfavouriteRecipes = repository.allFavouriteRecipes
        shoppingListItems = repository.shoppingListItems
        _loading.value = false
        _errorloading.value = false
    }

    private var _recipesList = MutableLiveData<ArrayList<Result>>()
    val recipeList: LiveData<ArrayList<Result>>
        get() = _recipesList

    private var _responseRecieved = MutableLiveData<Boolean>()
    val responseRecieved: LiveData<Boolean>
        get() = _responseRecieved

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    var prevQuery: String? = null

    fun searchRecipe(recipeName: String) {
        _loading.value = true
        if ((prevQuery == null) || (recipeName !== prevQuery)) {
            coroutineScope.launch {
                val serviceResponse =
                    repository.searchRecipe(recipeName,
                        APIKEY,
                        NUMBER_OF_RESULTS
                    )
                withContext(Dispatchers.Main) {
                    _responseRecieved.value = true
                    when (serviceResponse) {
                        is ServiceResponse.Success -> {
                            if (serviceResponse.data.totalResults > 0) {
                                _recipesList.value =
                                    serviceResponse.data.results as ArrayList<Result>
                                _loading.value = false
                            } else {
                                errorMessage.value = "No Results !!"
                                _errorloading.value = true
                                _loading.value = false
                            }
                        }
                        is ServiceResponse.Error -> {
                            _errorloading.value = true
                            _loading.value = false
                            errorMessage.value = serviceResponse.exception
                        }
                    }
                    _navigateToList.value = true
                    _responseRecieved.value = false
                }
            }
            prevQuery = recipeName
        }
    }

    private var _recipeDetail = MutableLiveData<RecipeDetail>()
    val recipeDetail: LiveData<RecipeDetail>
        get() = _recipeDetail

    private val _navigateToDetail = MutableLiveData<Boolean>()
    val navigateToDetail: LiveData<Boolean>
        get() = _navigateToDetail

    fun onRecipeItemtClicked(recipeId: Int) {
        _navigateToDetail.value = true
        searchRecipebyId(recipeId)
    }

    private val _navigateToList = MutableLiveData<Boolean>()
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    fun onCategoryItemtClicked(categoryName: String) {
        searchRecipe(categoryName)
        _navigateToList.value = true
    }

    fun onCuisineItemtClicked(cuisineName: String) {
        searchRecipe(cuisineName)
        _navigateToList.value = true
    }

    fun onDailyRecipeItemClicked() {
        _recipeDetail = _dailyRecipe
        _navigateToDetail.value = true
    }

    fun onRecipeListNavigated() {
        if (_navigateToList.value!!) {
            _navigateToList.value = false
        }
    }

    fun onRecipeDetailNavigated() {
        if (_navigateToDetail.value!!) {
            _navigateToDetail.value = false
        }
    }

    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)


    fun searchRecipebyId(recipeId: Int) {
        _loading.value = true
        coroutineScope.launch {
            val serviceResponse = repository.searchRecipebyId(recipeId, true,
                APIKEY
            )
            withContext(Dispatchers.Main) {
                when (serviceResponse) {
                    is ServiceResponse.Success -> {
                        _loading.value = false
                        _recipeDetail.value = serviceResponse.data
                        println("OUTPUT ${_recipeDetail.value}")
                    }
                    is ServiceResponse.Error -> {
                        _loading.value = false
                        _errorloading.value = true
                        errorMessage.value = serviceResponse.exception
                    }
                }
            }
        }
    }

    fun getCookingSteps() :ArrayList<String> {
        val cookingSteps = ArrayList<String>()
        val analyzedInstructions =
            recipeDetail.value?.analyzedInstructions?.get(0) as LinkedTreeMap<String, String>
        if (analyzedInstructions.containsKey("steps")) {
            val stepsArrayList =
                analyzedInstructions.getValue("steps") as ArrayList<LinkedTreeMap<String, String>>
            for (i in 0..(stepsArrayList.size - 1)) {
                if (stepsArrayList[i].contains("step")) {
                    val cookingStep = stepsArrayList[i].getValue("step")
                    cookingSteps.add(cookingStep)
                }
            }
        }
        return cookingSteps
    }

    //Daily Recipe
    private var _dailyRecipe = MutableLiveData<RecipeDetail>()
    val dailyRecipe: LiveData<RecipeDetail>
        get() = _dailyRecipe

    private var dailyRecipe_received: Boolean = false
    fun searchDailyRecipe() {
        if (dailyRecipe_received == false) {
            coroutineScope.launch {
                val serviceResponse = repository.searchDailyRecipe(APIKEY, 1)
                withContext(Dispatchers.Main) {
                    when (serviceResponse) {
                        is ServiceResponse.Success -> {
                            _dailyRecipe.value = serviceResponse.data.recipes[0]
                            dailyRecipe_received = true
                        }
                        is ServiceResponse.Error -> {
                            errorMessage.value = serviceResponse.exception
                        }
                    }
                }
            }
        }
    }

    // Favourite
    fun insertFavouriteRecipe(recipeDetail: RecipeDetail) {
        coroutineScope.launch {
            val rowId = repository.insertFavouriteRecipe(recipeDetail)
        }
    }

    fun deleteFavouriteRecipe(recipeDetail: RecipeDetail) {
        coroutineScope.launch {
            val rowsDeleted = repository.deleteFavouriteRecipe(recipeDetail)
            println("hi")
        }
    }

    fun deleteAllFavouriteRecipes() {
        coroutineScope.launch {
            repository.deleteAllFavouriteRecipes()
        }
    }

    fun getFavouriteRecipe(recipeId: Int) {
        coroutineScope.launch {
            val favouriteRecipe = repository.getFavouriteRecipe(recipeId)
            withContext(Dispatchers.Main){
                if (favouriteRecipe != null) {
                    _loading.value = false
                    _recipeDetail.value = favouriteRecipe
                }
                else
                {
//                    _loading.value = false
                    _errorloading.value = true
                }
            }
        }
    }


    fun onFavouriteItemClicked(recipeId: Int) {
        _recipeDetail.value = null
        _navigateToDetail.value = true
        getFavouriteRecipe(recipeId)
    }


    fun insertToShoppingList(ingredient : ShoppingList)
    {
        coroutineScope.launch {
            val id = repository.insertToShoppingList(ingredient)
            println("ROW_ ${id}")
        }
    }


    fun deleteFromShoppingList(ingredient: ShoppingList)
    {
        coroutineScope.launch {
            val rowsDeleted = repository.deleteFromShoppingList(ingredient)
            println("ROW $rowsDeleted")
        }
    }

}


//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }

package com.github.ebrahimi16153.mvifoodapp.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.mvifoodapp.data.repository.HomeRepository
import com.github.ebrahimi16153.mvifoodapp.util.connectivity.ConnectionStatus
import com.github.ebrahimi16153.mvifoodapp.util.connectivity.ConnectivityImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val connectivity : ConnectivityImpl) : ViewModel() {

    val intentChannel = Channel<HomeIntent>()
    private val _state = MutableStateFlow<HomeState>(HomeState.Empty)
    val state: StateFlow<HomeState> get() = _state


    init {
        handelState()
        connectionStatus()
    }


    private fun handelState() = viewModelScope.launch {
        intentChannel.consumeAsFlow().collect { itHomeIntent ->

            when (itHomeIntent) {
                is HomeIntent.Category -> loadCategory()
                is HomeIntent.RandomMeal -> loadRandomMeal()
                is HomeIntent.FoodLetters -> loadFoodByLetter(itHomeIntent)
                is HomeIntent.FoodsByCategory -> loadFoodsByCategory(itHomeIntent)
                is HomeIntent.FoodsBySearch -> loadFoodBySearch(itHomeIntent)
            }
        }
    }


    private suspend fun loadRandomMeal() {

        _state.value = HomeState.Loading

        try {
            val response = homeRepository.getRandomMeal()
            if (response.isSuccessful) {
                when (response.code()) {

                    in 200..299 -> {
                           if (!response.body()?.meals.isNullOrEmpty())
                        _state.value = HomeState.RandomMeal(response.body()?.meals!![0])
                        else
                            _state.value = HomeState.Empty
                    }

                    in 400..499 -> {
                        _state.value = HomeState.Error(message = "Error code 400")
                    }

                    in 500..599 -> {
                        _state.value = HomeState.Error(message = "Error code 500")
                    }
                }
            } else _state.value = HomeState.Error(message = "Response isn't Successful")

        } catch (e: Exception) {
            _state.value = HomeState.Error(message = e.message.toString())
        }
    }


    private suspend fun loadCategory() {

        _state.value = HomeState.Loading

        try {
            val response = homeRepository.getCategory()
            if (response.isSuccessful) {
                when (response.code()) {

                    in 200..299 -> {
                        _state.value = HomeState.CategoryList(response.body()!!.categories)
                    }

                    in 400..499 -> {
                        _state.value = HomeState.Error(message = "Error code 400")
                    }

                    in 500..599 -> {
                        _state.value = HomeState.Error(message = "Error code 500")
                    }
                }
            } else _state.value = HomeState.Error(message = "Response isn't Successful")

        } catch (e: Exception) {
            _state.value = HomeState.Error(message = e.message.toString())
        }
    }


    private suspend fun loadFoodByLetter(intent: HomeIntent.FoodLetters) {

        _state.value = HomeState.Loading
        try {
            val response = homeRepository.getFoodsByFirstLetter(intent.letter)
            if (response.isSuccessful) {
                when (response.code()) {
                    in 200..299 -> {
                        if (!response.body()?.meals.isNullOrEmpty())
                        _state.value = HomeState.Foods(response.body()?.meals!!)
                        else _state.value = HomeState.Empty
                    }

                    in 400..499 -> {
                        _state.value = HomeState.Error(message = "Error code 400")
                    }

                    in 500..599 -> {
                        _state.value = HomeState.Error(message = "Error code 500")
                    }
                }
            } else _state.value = HomeState.Error("Response isn't Successful")


        } catch (e: Exception) {
            _state.value = HomeState.Error(message = e.message.toString())
        }

    }


    private suspend fun  loadFoodsByCategory(intent: HomeIntent.FoodsByCategory){
        try {
            val response = homeRepository.getFoodsByCategory(intent.category)
            if (response.isSuccessful) {
                when (response.code()) {
                    in 200..299 -> {
                        if (!response.body()?.meals.isNullOrEmpty())
                        _state.value = HomeState.Foods(response.body()?.meals!!)
                        else _state.value = HomeState.Empty
                    }

                    in 400..499 -> {
                        _state.value = HomeState.Error(message = "Error code 400")
                    }

                    in 500..599 -> {
                        _state.value = HomeState.Error(message = "Error code 500")
                    }
                }
            } else _state.value = HomeState.Error("Response isn't Successful")


        } catch (e: Exception) {
            _state.value = HomeState.Error(message = e.message.toString())
        }
    }


    private suspend fun loadFoodBySearch(intent: HomeIntent.FoodsBySearch){

        try {
            val response = homeRepository.getFoodsBySearch(intent.searchQuery)
            if (response.isSuccessful){
                when(response.code()){
                    in 200..299 -> {
                        if (!response.body()?.meals.isNullOrEmpty())
                        _state.value = HomeState.Foods(response.body()?.meals!!)
                        else _state.value = HomeState.Empty
                    }

                    in 400..499 -> {
                        _state.value = HomeState.Error(message = "Error code 400")
                    }

                    in 500..599 -> {
                        _state.value = HomeState.Error(message = "Error code 500")
                    }
                }
            } else _state.value = HomeState.Error("Response isn't Successful")


        }catch (e: Exception){

            _state.value = HomeState.Error(e.message.toString())
        }
    }

    private fun connectionStatus() = viewModelScope.launch{

        connectivity.observe().collectLatest {
            when(it){
                ConnectionStatus.AVAILABLE -> {
                    intentChannel.send(HomeIntent.FoodLetters("A"))
                }
                ConnectionStatus.UNAVAILABLE -> {
                    _state.value = HomeState.Error(message = "No Internet Connection")
                }
                ConnectionStatus.LOSTING -> {
                    _state.value = HomeState.Error(message = "No Internet Connection")
                }
                ConnectionStatus.LOST -> {
                    _state.value = HomeState.Error(message = "No Internet Connection")
                }
            }
        }
    }


}
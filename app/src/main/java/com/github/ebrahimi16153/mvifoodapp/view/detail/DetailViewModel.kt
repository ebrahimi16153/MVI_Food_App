package com.github.ebrahimi16153.mvifoodapp.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList
import com.github.ebrahimi16153.mvifoodapp.data.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository) :
    ViewModel() {


    val intentChannel = Channel<DetailIntent>()
    private var _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state: StateFlow<DetailState> get() = _state


    init {
        handelState()
    }

    private fun handelState() = viewModelScope.launch {
        intentChannel.consumeAsFlow().collect {intent ->
            when (intent) {
                is DetailIntent.DetailMeal -> loadDetailMeal(intent.id)
                is DetailIntent.ISExist -> isExist(intent.id)
                is DetailIntent.Add -> addMeal(intent.meal)
                is DetailIntent.Remove -> removeMeal(intent.meal)
            }
        }
    }

    private fun loadDetailMeal(id: String) = viewModelScope.launch {
        try {
            val response = detailRepository.getDetail(id.toInt())
            if (response.isSuccessful) {
                when (response.code()) {
                    in 200..299 -> {
                        _state.value = DetailState.ShowDetail(response.body()?.meals!![0])
                    }

                    in 400..499 -> {
                        _state.value = DetailState.Error(message = "Error code 400")
                    }

                    in 500..599 -> {
                        _state.value = DetailState.Error(message = "Error code 500")
                    }
                }
            } else _state.value = DetailState.Error("Response isn't Successful")


        } catch (e: Exception) {
            _state.value = DetailState.Error(e.message.toString())
        }
    }


    private fun isExist(id: String) = viewModelScope.launch {
        detailRepository.isExist(id).collect {
            _state.value = DetailState.IsExist(it)
        }
    }


    private fun addMeal(meal: FoodList.Meal) = viewModelScope.launch {

        _state.value =
            try {

                DetailState.Add(detailRepository.addFood(meal))
            } catch (e: Exception) {
                DetailState.Error(e.message.toString())
            }
    }

    private fun removeMeal(meal: FoodList.Meal) = viewModelScope.launch {
        _state.value =
            try {

                DetailState.Remove(detailRepository.removeFood(meal))
            } catch (e: Exception) {
                DetailState.Error(e.message.toString())
            }
    }

}
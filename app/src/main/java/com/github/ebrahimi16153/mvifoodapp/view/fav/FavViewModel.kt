package com.github.ebrahimi16153.mvifoodapp.view.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ebrahimi16153.mvifoodapp.data.repository.FavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavViewModel @Inject constructor(private val favRepository: FavRepository) : ViewModel() {

    val intentChannel = Channel<FavIntent>()
    private val _state = MutableStateFlow<FavState>(FavState.Empty)
    val state get() = _state

    init {
        handelIntent()
    }

    private fun handelIntent() = viewModelScope.launch {

        intentChannel.consumeAsFlow().collect{itFavIntent ->
            when(itFavIntent){
                FavIntent.FavoriteList -> showFavList()
            }
        }
    }


    fun showFavList() = viewModelScope.launch {
        favRepository.getListOfFav().collect{ favList->
            if (favList.isNotEmpty()){
                _state.emit(FavState.FavList(list = favList))
            }else{
                _state.emit(FavState.Empty)
            }
        }
    }


}
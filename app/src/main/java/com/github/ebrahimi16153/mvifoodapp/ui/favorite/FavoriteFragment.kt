package com.github.ebrahimi16153.mvifoodapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ebrahimi16153.mvifoodapp.data.adapter.FoodAdapter
import com.github.ebrahimi16153.mvifoodapp.databinding.FragmentFavoriteBinding
import com.github.ebrahimi16153.mvifoodapp.view.fav.FavIntent
import com.github.ebrahimi16153.mvifoodapp.view.fav.FavState
import com.github.ebrahimi16153.mvifoodapp.view.fav.FavViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.ebrahimi16153.mvifoodapp.R
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    //binding
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    //viewModel
    private val viewModel: FavViewModel by viewModels()
    //adapter
    @Inject
    lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.intentChannel.send(FavIntent.FavoriteList)
        }

        binding.apply {
            lifecycleScope.launch {
                viewModel.state.collect { itFavState ->
                    when (itFavState) {
                        is FavState.Empty -> showEmpty()
                        is FavState.FavList -> showFavList(itFavState.list as MutableList<FoodList.Meal>)
                    }
                }
            }
        }
    }

    private fun showEmpty() {
        binding.apply {
            mainLay.isVisible = false
            emptyList.disconnectedIcon.setImageResource(R.drawable.empty)
            emptyList.disconnectedText.text = getString(R.string.emptyFav)
            emptyLay.isVisible = true
        }
    }

    private fun showFavList(list: MutableList<FoodList.Meal>){
        binding.apply {
            mainLay.isVisible = true
            emptyLay.isVisible = false
            foodAdapter.setData(list)
            favList.adapter = foodAdapter
            favList.layoutManager = LinearLayoutManager(requireContext())
            foodAdapter.seOnItemClickListener {
                val direction = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it.idMeal.toInt())
                findNavController().navigate(direction)
            }

        }
    }
}
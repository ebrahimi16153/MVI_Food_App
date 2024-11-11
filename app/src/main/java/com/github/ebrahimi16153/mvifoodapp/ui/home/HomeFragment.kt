package com.github.ebrahimi16153.mvifoodapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.github.ebrahimi16153.mvifoodapp.data.adapter.CategoryAdapter
import com.github.ebrahimi16153.mvifoodapp.data.adapter.FoodAdapter
import com.github.ebrahimi16153.mvifoodapp.databinding.FragmentHomeBinding
import com.github.ebrahimi16153.mvifoodapp.util.setUpSpinner
import com.github.ebrahimi16153.mvifoodapp.view.home.HomeIntent
import com.github.ebrahimi16153.mvifoodapp.view.home.HomeState
import com.github.ebrahimi16153.mvifoodapp.view.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.ebrahimi16153.mvifoodapp.R

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //viewmodel
    val viewModel: HomeViewModel by viewModels()

    //adapter
    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    @Inject
    lateinit var foodAdapter: FoodAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // call intent
        val state = viewModel.state
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.intentChannel.send(HomeIntent.RandomMeal)
            viewModel.intentChannel.send(HomeIntent.Category)
        }

        //spinner
        val spinnerLetter = listOf('A'..'Z').flatten().toMutableList()
        binding.filterSpinner.setUpSpinner(list = spinnerLetter, callback = {
            lifecycleScope.launch {
                viewModel.intentChannel.send(HomeIntent.FoodLetters(it))
            }
        })

        //search
        binding.searchEdt.addTextChangedListener { itText ->
            if (!itText.isNullOrEmpty()) {
                lifecycleScope.launch {
                    viewModel.intentChannel.send(HomeIntent.FoodsBySearch(itText.toString()))
                }
            }
        }

        //state
        lifecycleScope.launch {
            state.collect { itState ->
                //state
                when (itState) {
                    is HomeState.CategoryList -> categoryList(itState)
                    is HomeState.Error -> homeError(itState)
                    is HomeState.Empty -> emptyList()
                    is HomeState.Loading -> homeLoading()
                    is HomeState.RandomMeal -> randomMeal(itState)
                    is HomeState.Foods -> foodList(itState)
                }
            }
        }
    }

    private fun randomMeal(state: HomeState.RandomMeal) {

        binding.mainContent.isVisible = true
        binding.foodsLoading.isVisible = false
        binding.headerImg.load(data = state.meal.strMealThumb)
        Log.e("Meal", state.meal.toString())

    }

    private fun categoryList(state: HomeState.CategoryList) {

        binding.mainContent.isVisible = true
        binding.foodsLoading.isVisible = false
        categoryAdapter.setData(state.list)
        Log.e("Category", state.list.toString())
        binding.categoryRecycler.adapter = categoryAdapter
        binding.categoryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter.seOnItemClickListener {
            lifecycleScope.launch {
                viewModel.intentChannel.send(HomeIntent.FoodsByCategory(category = it.strCategory))
            }
        }
    }

    private fun homeLoading() {
        binding.mainContent.isVisible = false
        binding.foodsLoading.isVisible = true
    }

    private fun homeError(state: HomeState.Error) {
        binding.mainContent.isVisible = true
        binding.foodsLoading.isVisible = false
        binding.foodsRecycler.isVisible = false
        binding.disconnected.disconnectedIcon.setImageResource(R.drawable.disconnect)
        binding.disconnected.disconnectedText.text = state.message
        binding.disconnectedLay.isVisible = true
    }

    private fun foodList(state: HomeState.Foods) {
        binding.mainContent.isVisible = true
        binding.foodsLoading.isVisible = false
        binding.foodsRecycler.isVisible = true
        binding.disconnectedLay.isVisible = false
        foodAdapter.setData(state.list)
        binding.foodsRecycler.adapter = foodAdapter
        binding.foodsRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // onFood item Click
        foodAdapter.seOnItemClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.idMeal.toInt())
            findNavController().navigate(direction)
        }
    }

    private fun emptyList() {
        binding.apply {
            binding.mainContent.isVisible = true
            binding.foodsLoading.isVisible = false
            foodsRecycler.isVisible = false
            disconnected.disconnectedIcon.setImageResource(R.drawable.empty)
            disconnected.disconnectedText.text = "Nothing Found!"
            disconnectedLay.isVisible = true
        }
    }

}
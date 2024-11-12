package com.github.ebrahimi16153.mvifoodapp.ui.detail

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.github.ebrahimi16153.mvifoodapp.R
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList
import com.github.ebrahimi16153.mvifoodapp.data.repository.DetailRepository
import com.github.ebrahimi16153.mvifoodapp.databinding.FragmentDetailBinding
import com.github.ebrahimi16153.mvifoodapp.view.detail.DetailIntent
import com.github.ebrahimi16153.mvifoodapp.view.detail.DetailState
import com.github.ebrahimi16153.mvifoodapp.view.detail.DetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    //binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //binding
    private val args: DetailFragmentArgs by navArgs()
    private var foodID: Int = 0

    //viewModel
    private val viewModel: DetailViewModel by viewModels()

    // meal
    private lateinit var meal: FoodList.Meal

    @Inject
    lateinit var detailRepository: DetailRepository

    private var isExist = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodID = args.foodId
        binding.apply {

            //backBtn
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }



            lifecycleScope.launch {
                viewModel.intentChannel.send(DetailIntent.DetailMeal(foodID.toString()))
                viewModel.intentChannel.send(DetailIntent.ISExist(foodID.toString()))
            }




            lifecycleScope.launch {
                //state
                viewModel.state.collect { itDetailState ->
                    when (itDetailState) {
                        is DetailState.Error -> error(itDetailState)
                        is DetailState.Loading -> loading()
                        is DetailState.ShowDetail -> loadDetail(itDetailState)
                        is DetailState.Add -> {
                            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                        }

                        is DetailState.IsExist -> {
                            isExist = itDetailState.exist
                        }

                        is DetailState.Remove -> {
                            Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
            //favBtn
            favorite.setOnClickListener {
                lifecycleScope.launch {
                    if (isExist) {
                        viewModel.intentChannel.send(DetailIntent.Remove(meal))
                    } else {
                        viewModel.intentChannel.send(DetailIntent.Add(meal))
                    }
                }
            }
        }
    }

    fun error(detailState: DetailState.Error) {
        val message = detailState.message
        binding.apply {
            mainLay.isVisible = false
            detailLoading.isVisible = true
            error.disconnectedText.text = message
            error.disconnectedIcon.setImageResource(R.drawable.disconnect)
            errorLay.isVisible = true
        }

    }


    fun loadDetail(detailState: DetailState.ShowDetail) {

        binding.apply {

            // show main lay if loading is false
            mainLay.isVisible = true
            detailLoading.isVisible = false
            //foodDetail
            meal = detailState.detail
            //top banner
            coverImg.load(data = meal.strMealThumb) { crossfade(true) }
            //category
            categoryDetail.text = meal.strCategory
            //area
            areaDetail.text = meal.strArea
            //youTube
            if (!meal.strYoutube.isNullOrEmpty()) youtube.isVisible =
                true else youtube.isVisible =
                false
            //source
            if (!meal.strSource.isNullOrEmpty()) sourceDetail.isVisible =
                true else sourceDetail.isVisible = false
            sourceDetail.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.strSource))
                startActivity(intent)
            }
            //mealTitle
            foodTitleTxt.text = meal.strMeal
            //instructions
            foodDescTxt.text = meal.strInstructions
            //ingredients
            ingredientsTxt.text = meal.toIngredient()
            //measure
            measureTxt.text = meal.toMeasure()
            //youtube
            youtubePlayer.apply {
                viewLifecycleOwner.lifecycle.addObserver(this)
                val youtubeKey = meal.youtubeKey()
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        youtubeKey?.let {
                            player.cueVideo(it, 0f)
                        }
                    }
                })
            }
        }
    }


    fun loading() {
        binding.apply {
            mainLay.isVisible = false
            detailLoading.isVisible = true
        }
    }
}
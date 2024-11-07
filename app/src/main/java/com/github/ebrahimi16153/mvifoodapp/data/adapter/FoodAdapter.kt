package com.github.ebrahimi16153.mvifoodapp.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList
import com.github.ebrahimi16153.mvifoodapp.databinding.ItemFoodsBinding
import javax.inject.Inject

class FoodAdapter @Inject constructor() : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private lateinit var binding: ItemFoodsBinding
    private var listOfFoods: List<FoodList.Meal> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(listOfFoods[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = listOfFoods.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun bindViews(item: FoodList.Meal) {
            binding.apply {
                itemFoodsImg.load(data = item.strMealThumb) {
                    crossfade(true)
                }
                itemFoodsTitle.text = item.strMeal
                if (!item.strArea.isNullOrEmpty()) binding.itemFoodsArea.text =
                    item.strArea else binding.itemFoodsArea.visibility = View.GONE
                if (!item.strCategory.isNullOrEmpty()) binding.itemFoodsCategory.text =
                    item.strCategory else binding.itemFoodsCategory.visibility = View.GONE
                if (!item.strSource.isNullOrEmpty()) binding.itemFoodsCount.visibility =
                    View.VISIBLE else binding.itemFoodsCount.visibility = View.GONE
            }

            //onItemClickListener
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }

    }


// a differ class and function for dynamic adapter -> when data of adapter can change many times
// whe must handel oldItems and new items, in fact we handel how adapter update new items

    fun setData(data: List<FoodList.Meal>) {

        val movieListDiffer = MovieListDiffer(oldItems = listOfFoods, newItems = data)
        val diffUtil = DiffUtil.calculateDiff(movieListDiffer)
        listOfFoods = data
        diffUtil.dispatchUpdatesTo(this)

    }

    class MovieListDiffer(
        private val oldItems: List<FoodList.Meal>,
        private val newItems: List<FoodList.Meal>
    ) : DiffUtil.Callback() {


        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] === newItems[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] === newItems[newItemPosition]

    }

    // onClickListener
    private var onItemClickListener: ((FoodList.Meal) -> Unit)? = null

    fun seOnItemClickListener(listener: (FoodList.Meal) -> Unit) {
        onItemClickListener = listener
    }


}
package com.github.ebrahimi16153.mvifoodapp.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.ebrahimi16153.mvifoodapp.data.model.Categories
import com.github.ebrahimi16153.mvifoodapp.R
import com.github.ebrahimi16153.mvifoodapp.databinding.ItemCategoriesBinding
import javax.inject.Inject

class CategoryAdapter @Inject constructor() : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var binding: ItemCategoriesBinding
    private var listOfCategory: List<Categories.Category> = emptyList()
    private var selectedItem = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(listOfCategory[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = listOfCategory.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bindViews(item: Categories.Category) {
            binding.itemCategoriesTxt.text = item.strCategory
            binding.itemCategoriesImg.load(data = item.strCategoryThumb)

            //onItemClickListener
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    selectedItem = adapterPosition
                    notifyDataSetChanged()
                    it(item)
                }
            }

            // selected
            if (adapterPosition == selectedItem) {
                binding.root.setBackgroundResource(R.drawable.bg_selected_rounded)
            } else {
                binding.root.setBackgroundResource(R.drawable.bg_rounded_white)
            }
        }
    }


// a differ class and function for dynamic adapter -> when data of adapter can change many times
// whe must handel oldItems and new items, in fact we handel how adapter update new items

    fun setData(data: List<Categories.Category>) {

        val movieListDiffer = MovieListDiffer(oldItems = listOfCategory, newItems = data)
        val diffUtil = DiffUtil.calculateDiff(movieListDiffer)
        listOfCategory = data
        diffUtil.dispatchUpdatesTo(this)

    }


    class MovieListDiffer(
        private val oldItems: List<Categories.Category>,
        private val newItems: List<Categories.Category>
    ) :
        DiffUtil.Callback() {


        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] === newItems[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] === newItems[newItemPosition]
    }

    // onClickListener
    private var onItemClickListener: ((Categories.Category) -> Unit)? = null

    fun seOnItemClickListener(listener: (Categories.Category) -> Unit) {
        onItemClickListener = listener
    }


}
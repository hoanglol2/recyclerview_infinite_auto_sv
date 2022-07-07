package com.example.rv_auto_scrollview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rv_auto_scrollview.databinding.ItemFeatureTileBinding

class FeaturesAdapter: RecyclerView.Adapter<FeaturesAdapter.FeaturesVH>() {
    private val mDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Feature>() {
        override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem.id == newItem.id
        }
    })

    fun setList(list: List<Feature>) {
        mDiffer.submitList(list)
    }

    fun getList(): MutableList<Feature> = mDiffer.currentList

    inner class FeaturesVH(val viewBinding: ItemFeatureTileBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data: Feature) {
            viewBinding.apply {
                root.text = data.text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturesVH {
        return FeaturesVH(
            ItemFeatureTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FeaturesVH, position: Int) {
        holder.bind(mDiffer.currentList[position])
    }

    override fun getItemCount() = mDiffer.currentList.size
}
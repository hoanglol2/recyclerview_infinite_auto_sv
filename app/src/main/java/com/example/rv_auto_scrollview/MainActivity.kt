package com.example.rv_auto_scrollview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rv_auto_scrollview.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mAdapter by lazy { FeaturesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initData()
        initRecyclerView()

        lifecycleScope.launch {
            autoScrollFeaturesList()
        }
    }

    private suspend fun autoScrollFeaturesList() {
        mBinding.apply {
            if (rvFeature.canScrollHorizontally(DIRECTION_RIGHT)) {
                rvFeature.smoothScrollBy(SCROLL_DX, 0)
            } else {
                val firstPosition =
                    (rvFeature.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (firstPosition != RecyclerView.NO_POSITION) {
                    val currentList = mAdapter.getList()
                    val secondPart = currentList.subList(0, firstPosition)
                    val firstPart = currentList.subList(firstPosition, currentList.size)
                    mAdapter.setList(firstPart + secondPart)
                }
            }
            delay(DELAY_BETWEEN_SCROLL_MS)
            autoScrollFeaturesList()
        }
    }

    private fun initRecyclerView() {
        mBinding.rvFeature.adapter = mAdapter
    }

    private fun initData() {
        val list = arrayListOf<Feature>()
        for (i in 0..5) {
            list.add(Feature("Google Feates esdas $i"))
        }
        mAdapter.setList(list)
    }

    companion object {
        private const val DELAY_BETWEEN_SCROLL_MS = 35L
        private const val SCROLL_DX = 50
        private const val DIRECTION_RIGHT = 1
    }
}
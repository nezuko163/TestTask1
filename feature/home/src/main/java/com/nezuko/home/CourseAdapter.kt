package com.nezuko.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nezuko.domain.model.Course
import com.nezuko.ui.databinding.CourseCardBinding

class CourseAdapter constructor(
    private val onItemClick: (Long) -> Unit,
    private val onLikeClick: (Course) -> Unit
) : PagingDataAdapter<Course, CourseAdapter.CourseViewHolder>(DIFF_CALLBACK) {
    private var count = 0
    private val TAG = "CourseAdapter"

    inner class CourseViewHolder(
        private val binding: CourseCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            Glide
                .with(itemView.context)
                .load(course.cover.apply {
                    Log.i(TAG, "bind: $this")
                })
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image)

            binding.tvBlurStar.text = course.score.toString()
            binding.tvBlurDate.text = course.dateCreated.toString()
            binding.tvTitle.text = course.title
            binding.tvAbout.text = course.description
            binding.tvPrice.text = course.priceDisplay
            binding.blurView
                .setupWith(binding.blurView)
                .setBlurRadius(20f)
                .setBlurEnabled(true)

            binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
            binding.blurView.clipToOutline = true

            binding.blurView2
                .setupWith(binding.root)
                .setBlurRadius(20f)
                .setBlurEnabled(true)


            binding.blurView2.outlineProvider = ViewOutlineProvider.BACKGROUND
            binding.blurView2.clipToOutline = true

            binding.blurView3
                .setupWith(binding.root)
                .setBlurRadius(20f)
                .setBlurEnabled(true)

            binding.blurView3.outlineProvider = ViewOutlineProvider.BACKGROUND
            binding.blurView3.clipToOutline = true
            binding.root.setOnClickListener {
                onItemClick(course.id)
            }

            binding.imgIsFavourite.setImageResource(
                if (course.isFavourite) {
                    com.nezuko.ui.R.drawable.like
                } else {
                    com.nezuko.ui.R.drawable.not_like
                }
            )

            binding.imgIsFavourite.setOnClickListener {
                Log.i(TAG, "bind: course - ${course.hashCode()}")
                course.isFavourite = !course.isFavourite
                binding.imgIsFavourite.setImageResource(
                    if (course.isFavourite) {
                        com.nezuko.ui.R.drawable.like
                    } else {
                        com.nezuko.ui.R.drawable.not_like
                    }
                )
                onLikeClick(course)
            }
        }
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        if (course != null) {
            holder.bind(course)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = CourseCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.nezuko.account

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nezuko.domain.model.Course
import com.nezuko.ui.databinding.CourseCardBinding

class AccountAdapter constructor(
    private val onItemClick: (Long) -> Unit,
    private val onLikeClick: (Course) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {
    private var courses: List<Course> = emptyList()
    private val TAG = "FavouritesAdapter"

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Course>) {
        courses = list
        notifyDataSetChanged()
    }

    inner class AccountViewHolder(
        private val binding: CourseCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            Log.i(TAG, "bind: title - ${course.title}")
            Log.i(TAG, "bind: isFav - ${course.isFavourite}")
            Glide
                .with(itemView.context)
                .load(course.cover)
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
                onLikeClick(course)
                binding.imgIsFavourite.setImageResource(
                    if (course.isFavourite) {
                        com.nezuko.ui.R.drawable.like
                    } else {
                        com.nezuko.ui.R.drawable.not_like
                    }
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = CourseCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val course = courses.get(position)
        if (course != null) {
            holder.bind(course)
        }
    }
}
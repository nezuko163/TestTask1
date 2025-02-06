package com.nezuko.coursecard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nezuko.coursecard.databinding.FragmentCourseCardBinding
import com.nezuko.domain.model.CoursePlatform
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseCardFragment : Fragment() {
    private val TAG = "CourseCardFragment"
    private var _binding: FragmentCourseCardBinding? = null
    private val binding get() = _binding!!

    private val vm: CourseCardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }


        val courseId = arguments?.getLong("courseId") ?: 0L
        if (courseId == 0L) {
            Log.e(TAG, "onViewCreated: courseId = 0")
        }
        val course = vm.cache[CoursePlatform.STEPIK]?.get(courseId)
        if (course == null) {
            Log.e(TAG, "onViewCreated: course = null")
            return
        }

        binding.btnFavourite.setOnClickListener {
            Log.i(TAG, "btnFavourite: click")
            course.isFavourite = !course.isFavourite
            vm.likeCourse(course)
            binding.btnFavourite.setImageResource(
                if (course.isFavourite) {
                    R.drawable.favourite_green_outline
                } else {
                    R.drawable.favourite1
                }
            )
        }

        binding.btnFavourite.setImageResource(
            if (course.isFavourite) {
                R.drawable.favourite_green_outline
            } else {
                R.drawable.favourite1
            }
        )


        Glide
            .with(requireContext())
            .load(course.cover)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageCourse)

        Glide
            .with(requireContext())
            .load(course.cover)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
            .into(binding.imgAuthor)

        binding.tvBlurStar.text = course.score.toString()
        binding.tvBlurDate.text = course.dateCreated.toString()
        binding.tvTitle.text = course.title
        binding.tvAuthor.text = course.author
        binding.tvDescription.text = course.description

        binding.btnContinue.setOnClickListener {
            // переход
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(course.urlCourse))
            startActivity(intent)
        }
    }
}
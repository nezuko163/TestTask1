package com.nezuko.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.nezuko.domain.model.Course
import com.nezuko.domain.repository.NavigationRepository
import com.nezuko.main.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CourseAdapter

    private val vm: HomeViewModel by activityViewModels()

    @Inject
    lateinit var navigationRepository: NavigationRepository

    private val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())
    private val delay: Long = 1000
    private var runnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated: $savedInstanceState")

        binding.et.doOnTextChanged { text, _, _, _ ->
            runnable?.let { handler.removeCallbacks(it) }

            runnable = Runnable {
                Log.i(TAG, "onTextChanged: $text")

                if (vm.query != text?.toString()) {
                    lifecycleScope.launch {
                        adapter.submitData(PagingData.empty())
                    }
                }
                if (text?.isNotEmpty() == true) {
                    vm.setQuery(text.toString())
                }

            }

            runnable?.let { handler.postDelayed(it, delay) }
        }

        adapter = CourseAdapter(
            onItemClick = { id ->
                navigationRepository.navigateFromHomeToCourse(id)
            },
            onLikeClick = { course: Course ->
                vm.like(course)
            }
        )
        adapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.errorTextView.isVisible = loadState.source.refresh is LoadState.Error
        }
        binding.rcView.layoutManager = LinearLayoutManager(context)
        binding.rcView.adapter = adapter.withLoadStateFooter(
            CourseLoadStateAdapter { adapter.retry() }
        )

        lifecycleScope.launch {
            vm.courses.collect { data ->
                adapter.submitData(lifecycle, data)
            }
        }
    }
}
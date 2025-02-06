package com.nezuko.favourites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nezuko.domain.repository.NavigationRepository
import com.nezuko.favourites.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val TAG = "FavouritesFragment"
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavouritesAdapter

    private val vm: FavouritesViewModel by activityViewModels()

    @Inject
    lateinit var navigationRepository: NavigationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavouritesAdapter(
            onItemClick = {
                navigationRepository.navigateFromFavouriteToCourse(it)
            },
            onLikeClick = { course ->
                vm.like(course)
            }
        )

        binding.rcView.layoutManager = LinearLayoutManager(context)
        binding.rcView.adapter = adapter

        lifecycleScope.launch {
            vm.likedCourses.collect {
                Log.i(TAG, "onViewCreated: likedCourses - $it")
                adapter.setList(it.values.toList())
            }
        }
    }
}
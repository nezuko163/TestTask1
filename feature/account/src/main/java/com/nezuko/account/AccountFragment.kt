package com.nezuko.account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nezuko.account.databinding.FragmentAccountBinding
import com.nezuko.domain.repository.NavigationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val vm: AccountViewModel by activityViewModels()

    @Inject
    lateinit var navigationRepository: NavigationRepository

    private lateinit var adapter: AccountAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLeaveAccount.setOnClickListener {
            vm.signOut()
        }

        adapter = AccountAdapter(
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
                adapter.setList(it.values.toList())
            }
        }
    }
}
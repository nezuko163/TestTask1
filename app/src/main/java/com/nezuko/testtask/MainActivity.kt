package com.nezuko.testtask

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nezuko.account.AccountFragmentDirections
import com.nezuko.domain.model.ResultModel
import com.nezuko.domain.repository.NavigationRepository
import com.nezuko.favourites.FavouritesFragmentDirections
import com.nezuko.home.HomeFragmentDirections
import com.nezuko.testtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigationRepository: NavigationRepository

    private val TAG = "MainMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        vm.onCreate()
        vm.getCurrentUser()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vm.loadTokens()


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) ?: return
        navController = navHostFragment.findNavController()
        binding.bottomNavigation.setupWithNavController(navController)

        navigationRepository.apply {
            setUpNavigateFromHomeToCourse { id ->
                navController.navigate(HomeFragmentDirections.fromHomeToCourse(id))
            }
            setUpNavigateFromAccountToCourse { id ->
                navController.navigate(AccountFragmentDirections.fromAccountToCourse(id))
            }
            setUpNavigateFromFavouriteToCourse { id ->
                navController.navigate(FavouritesFragmentDirections.fromFavouriteToCourse(id))
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.user.collect { user ->
                    navController.setGraph(
                        if (user.status == ResultModel.Status.SUCCESS && !user.data.isNullOrEmpty()) {
                            binding.bottomNavigation.visibility = View.VISIBLE
                            R.navigation.app_nav
                        } else {
                            binding.bottomNavigation.visibility = View.GONE
                            com.nezuko.auth.R.navigation.navigation
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.onDestroy()
    }
}
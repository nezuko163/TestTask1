package com.nezuko.auth


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nezuko.auth.databinding.RegistrationLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: RegistrationLayoutBinding? = null
    private val binding get() = _binding!!

    private val vm: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RegistrationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.tvLogin.setOnClickListener {
            navController.navigate(R.id.from_reg_to_login)
        }

        binding.btnRegister.setOnClickListener {
            Log.i("Registration", "onViewCreated: ${binding.etEmail.text.toString()}")
            try {
                vm.createUser(binding.etEmail.text.toString(), binding.etPassword.text.toString(), requireContext())
            } catch (_: Exception) {}
        }
    }
}
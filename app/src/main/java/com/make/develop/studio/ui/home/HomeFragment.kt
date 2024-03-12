package com.make.develop.studio.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.make.develop.studio.databinding.FragmentHomeBinding
import com.make.develop.studio.utils.Constants

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setUserData(Constants.currentUser!!)

        setupListeners()

        setupObservers()
    }

    private fun setupListeners() {
        binding.btnAddBill.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToBillsFragment())
        }

        binding.btnModelPayments.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToModelsPaymentFragment())
        }
    }

    private fun setupObservers() {
        viewModel.userModel.observe(viewLifecycleOwner) {user->
            binding.txtBalanceNumber.text = Constants.formatPriceInPesos(user.balance?.toDouble()?:"0".toDouble())
            binding.txtUser.text = "Bienvenido ${user.name}"
        }
    }
}
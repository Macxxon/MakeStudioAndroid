package com.make.develop.studio.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.make.develop.studio.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnAddBill.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToBillsFragment())
        }

        binding.btnModelPayments.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToModelsPaymentFragment())
        }
    }
}
package com.make.develop.studio.ui.bills

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.make.develop.studio.databinding.FragmentBillsBinding
import com.make.develop.studio.models.BillsModel
import com.make.develop.studio.utils.Constants

class BillsFragment: Fragment() {

    private lateinit var binding: FragmentBillsBinding
    private val viewModel: BillsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBillsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()

        setupObservers()
    }

    private fun setupListeners(){

        val users = arrayOf("Venus", "Kevin", "Jose", "Maicol")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, users)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerChooseUserBill.adapter = adapter

        binding.btnSaveBill.setOnClickListener {
            viewModel.postNewBill(createRequest())
        }
    }

    private fun setupObservers(){
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Gasto registrado!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun createRequest():BillsModel{
        val estimatedServerTimeInMs =
            System.currentTimeMillis() + 5000
        return BillsModel(
            concept = binding.edtReference.text.toString(),
            total_payment = binding.edtValue.text.toString().toInt(),
            payment_user = binding.spinnerChooseUserBill.selectedItem.toString(),
            paid = true,
            payment_date = estimatedServerTimeInMs,
            user_paid = Constants.currentUser?.uid
        )
    }
}
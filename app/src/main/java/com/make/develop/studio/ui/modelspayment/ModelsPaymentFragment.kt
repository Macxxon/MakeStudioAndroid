package com.make.develop.studio.ui.modelspayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.make.develop.studio.R
import com.make.develop.studio.databinding.FragmentModelsPaymentBinding
import com.make.develop.studio.databinding.ItemModelPaymentBinding
import com.make.develop.studio.models.ModelsInfoModel
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

class ModelsPaymentFragment: Fragment() {

    private lateinit var binding: FragmentModelsPaymentBinding
    private val viewModel: ModelsPaymentViewModel by viewModels()

    private val modelsAdapter = adapterOf<ModelsInfoModel> {
        register(layoutResource = R.layout.item_model_payment,
            viewHolder = ::ModelsViewHolder,
            onBindViewHolder = { vh, pos, item ->
                vh.bind(item, pos)//vh.binding.item = item
            })
    }

    inner class ModelsViewHolder(view: View) : RecyclerViewHolder<ModelsInfoModel>(view) {
        val binding = ItemModelPaymentBinding.bind(view)
        fun bind(item: ModelsInfoModel, pos: Int) {
            if(item.status == 1) binding.btnPaid.isEnabled = false

            binding.txtName.text = "Nombre: ${item.name}"
            binding.txtNickname.text = "Nickname: ${item.nickname}"
            binding.txtPayment.text = "Pago: ${item.payment}"
            binding.btnPaid.setOnClickListener {
                viewModel.updateModelPayment(item,pos)
                binding.btnPaid.isEnabled = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModelsPaymentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getModelsPayment()

        setupListeners()

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.modelsPayment.observe(viewLifecycleOwner) {modelsPayment->
            if(modelsPayment.status == 1) binding.btnSaveBillTotal.isEnabled = false
            binding.txtViewRangeDate.text = "Rango: ${modelsPayment.rangeDate}"
            binding.txtTotalPayment.text = "Total: ${modelsPayment.totalPayment}"
            modelsAdapter.submitList(modelsPayment.Models)
        }
        
        viewModel.isSuccessModelPayment.observe(viewLifecycleOwner) {isSuccess->
            if(isSuccess){
                Toast.makeText(requireContext(), "Tu pago ha sido registrado!", Toast.LENGTH_SHORT).show()
                viewModel.setSuccessModelPayment(false)
                viewModel.getModelsPayment()
            }
        }

        viewModel.isSuccessFinal.observe(viewLifecycleOwner) {isSuccess->
            if(isSuccess){
                Toast.makeText(requireContext(), "Cuenta semanal cerrada!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupListeners() {
        binding.rvModelsPayment.adapter = modelsAdapter

        binding.btnSaveBillTotal.setOnClickListener {
            if(modelsAdapter.currentList.all { it.status == 1 }){
                viewModel.updateStatusModelsPayment()
            }else{
                Toast.makeText(requireContext(), "Aun hay modelos sin pagar", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
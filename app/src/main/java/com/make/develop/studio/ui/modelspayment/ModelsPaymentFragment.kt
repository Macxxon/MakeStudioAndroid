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
import com.make.develop.studio.utils.Constants
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
        val bindingItem = ItemModelPaymentBinding.bind(view)
        fun bind(item: ModelsInfoModel, pos: Int) {
            if(item.status == 1) bindingItem.btnPaid.isEnabled = false

            bindingItem.txtName.text = "Nombre: ${item.name}"
            bindingItem.txtNickname.text = "Nickname: ${item.nickname}"
            bindingItem.txtPayment.text = "Pago: ${ Constants.formatPriceInPesos(item.payment?.toDouble()?:0.0)}"
            bindingItem.btnPaid.setOnClickListener {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.updateModelPayment(item,pos)
                bindingItem.btnPaid.isEnabled = false
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

        binding.progressBar.visibility = View.VISIBLE

        viewModel.getModelsPayment()

        setupListeners()

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.modelsPayment.observe(viewLifecycleOwner) {modelsPayment->
            if(modelsPayment.status == 1) binding.btnSaveBillTotal.isEnabled = false
            binding.txtViewRangeDate.text = "Rango: ${modelsPayment.rangeDate}"
            binding.txtTotalPayment.text = "Total: ${Constants.formatPriceInPesos(modelsPayment.totalPayment?.toDouble()?:0.0)}"
            modelsAdapter.submitList(modelsPayment.Models)
            binding.progressBar.visibility = View.GONE
        }
        
        viewModel.isSuccessModelPayment.observe(viewLifecycleOwner) {isSuccess->
            if(isSuccess){
                Toast.makeText(requireContext(), "Tu pago ha sido registrado!", Toast.LENGTH_SHORT).show()
                viewModel.setSuccessModelPayment(false)
                viewModel.getModelsPayment()
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.isSuccessFinal.observe(viewLifecycleOwner) {isSuccess->
            if(isSuccess){
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Cuenta semanal cerrada!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupListeners() {
        binding.rvModelsPayment.adapter = modelsAdapter

        binding.btnSaveBillTotal.setOnClickListener {
            if(modelsAdapter.currentList.all { it.status == 1 }){
                binding.progressBar.visibility = View.VISIBLE
                viewModel.updateStatusModelsPayment()
            }else{
                Toast.makeText(requireContext(), "Aun hay modelos sin pagar", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
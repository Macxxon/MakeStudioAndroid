package com.make.develop.studio.ui.modelspayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
            binding.txtName.text = item.name
            binding.txtNickname.text = item.nickname
            binding.txtPayment.text = item.payment.toString()
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
            binding.textView2.text = modelsPayment.rangeDate
            modelsAdapter.submitList(modelsPayment.models)
        }
    }

    private fun setupListeners() {
        binding.rvModelsPayment.adapter = modelsAdapter
    }

}
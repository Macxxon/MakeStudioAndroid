package com.make.develop.studio.ui.listbills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.make.develop.studio.R
import com.make.develop.studio.databinding.FragmentListBillsBinding
import com.make.develop.studio.databinding.ItemBillBinding
import com.make.develop.studio.models.BillsModel
import com.make.develop.studio.utils.Constants
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

class ListBillsFragment : Fragment(){

    private lateinit var binding: FragmentListBillsBinding
    private val viewModel: ListBillsViewModel by viewModels()


    private val billsAdapter = adapterOf<BillsModel> {
        register(layoutResource = R.layout.item_bill,
            viewHolder = ::BillsViewHolder,
            onBindViewHolder = { vh, pos, item ->
                vh.bind(item, pos)
            })
    }

    inner class BillsViewHolder(view: View) : RecyclerViewHolder<BillsModel>(view) {
        val binding = ItemBillBinding.bind(view)
        fun bind(item: BillsModel, pos: Int) {
            binding.txtConcept.text = "Nombre: ${item.concept}"
            binding.txtPayment.text = "Nickname: ${Constants.formatPriceInPesos(item.total_payment?.toDouble()?:0.0)}"
            binding.txtPaymentUser.text = "Pago: ${item.payment_user}"
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBillsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBills()

        setupListeners()

        setupObservers()
    }

    private fun setupListeners() {
        binding.rvBills.adapter = billsAdapter
    }

    private fun setupObservers() {
        viewModel.bills.observe(viewLifecycleOwner) {bills->
            billsAdapter.submitList(bills)
        }
    }

}
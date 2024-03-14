package com.make.develop.studio.ui.modelspayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.make.develop.studio.models.ModelsInfoModel
import com.make.develop.studio.models.ModelsPaymentModel

class ModelsPaymentViewModel: ViewModel(){


    private var _modelsPayment = MutableLiveData<ModelsPaymentModel>()
    val modelsPayment: LiveData<ModelsPaymentModel> get() = _modelsPayment


    fun getModelsPayment(){
        _modelsPayment.value = ModelsPaymentModel(
            models = listOf(ModelsInfoModel("name", "nickname", 0, 0)),
            createDate = 0,
            rangeDate = "Mi rango",
            status = 0,
            totalPayment = 0
        )
    }
}
package com.make.develop.studio.ui.modelspayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.make.develop.studio.models.ModelsInfoModel
import com.make.develop.studio.models.ModelsPaymentModel
import com.make.develop.studio.utils.Constants

class ModelsPaymentViewModel: ViewModel(){


    private var _modelsPayment = MutableLiveData<ModelsPaymentModel>()
    val modelsPayment: LiveData<ModelsPaymentModel> get() = _modelsPayment


    fun getModelsPayment(){
        FirebaseDatabase.getInstance().getReference(Constants.MODELS_PAYMENT_REFERENCE)
            .limitToLast(100)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { child->
                        val modelsPayment = child.getValue(ModelsPaymentModel::class.java)
                        _modelsPayment.value = modelsPayment
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _modelsPayment.value = ModelsPaymentModel()
                }
            })
    }
}
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
import com.make.develop.studio.models.UserModel
import com.make.develop.studio.utils.Constants

class ModelsPaymentViewModel: ViewModel(){


    private var _modelsPayment = MutableLiveData<ModelsPaymentModel>()
    val modelsPayment: LiveData<ModelsPaymentModel> get() = _modelsPayment


    private val _isSuccessModelPayment : MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessModelPayment: MutableLiveData<Boolean> get() = _isSuccessModelPayment

    private val _isSuccessFinal : MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessFinal: MutableLiveData<Boolean> get() = _isSuccessFinal

    fun getModelsPayment(){
        FirebaseDatabase.getInstance().getReference(Constants.MODELS_PAYMENT_REFERENCE)
            .limitToLast(100)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { child->
                        val modelsPayment = child.getValue(ModelsPaymentModel::class.java)
                        modelsPayment!!.key = child.key
                        _modelsPayment.value = modelsPayment
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _modelsPayment.value = ModelsPaymentModel()
                }
            })
    }

    fun updateModelPayment(model: ModelsInfoModel, pos: Int){
        val updateData = HashMap<String,Any>()
        updateData["status"] = 1
        FirebaseDatabase.getInstance()
            .getReference(Constants.MODELS_PAYMENT_REFERENCE)
            .child(_modelsPayment.value?.key!!)
            .child(Constants.MODELS_REFERENCE)
            .child(pos.toString())
            .updateChildren(updateData)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    discountBalanceUser(model.payment)
                }
            }
    }

    private fun discountBalanceUser(payment:Int){
        val balanceFinal = Constants.currentUser!!.balance!! - payment
        FirebaseDatabase.getInstance()
            .getReference(Constants.USER_REFERENCE)
            .child(Constants.currentUser!!.uid!!)
            .child("balance")
            .setValue(balanceFinal)
            .addOnFailureListener { }
            .addOnCompleteListener {
                if(it.isSuccessful){
                    updateDataUser()
                }
            }
    }

    private fun updateDataUser(){
        FirebaseDatabase.getInstance()
            .getReference(Constants.USER_REFERENCE)
            .child(Constants.currentUser!!.uid!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    //later
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        val userModel = p0.getValue(UserModel::class.java)
                        Constants.currentUser = userModel

                        _isSuccessModelPayment.value = true
                    }
                }
            })
    }

    fun updateStatusModelsPayment(){
        FirebaseDatabase.getInstance()
            .getReference(Constants.MODELS_PAYMENT_REFERENCE)
            .child(_modelsPayment.value?.key!!)
            .child("status")
            .setValue(1)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    _isSuccessFinal.value = true
                }
            }
    }

    fun setSuccessModelPayment(isSuccess: Boolean){
        _isSuccessModelPayment.value = isSuccess
    }
}
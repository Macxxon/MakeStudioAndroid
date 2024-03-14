package com.make.develop.studio.ui.bills

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.make.develop.studio.models.BillsModel
import com.make.develop.studio.models.UserModel
import com.make.develop.studio.utils.Constants

class BillsViewModel: ViewModel(){

    private val _isSuccess : MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> get() = _isSuccess


    fun postNewBill(bill: BillsModel){
        FirebaseDatabase.getInstance()
            .getReference(Constants.BILLS_REFERENCE)
            .child(Constants.createBillNumber())
            .setValue(bill)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    discountBalanceUser(bill.total_payment!!)
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

                        _isSuccess.value = true
                    }
                }
            })
    }
}
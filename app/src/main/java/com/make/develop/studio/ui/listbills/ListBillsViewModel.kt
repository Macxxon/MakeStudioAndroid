package com.make.develop.studio.ui.listbills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.make.develop.studio.models.BillsModel
import com.make.develop.studio.utils.Constants

class ListBillsViewModel : ViewModel() {

    private var _bills = MutableLiveData<ArrayList<BillsModel>>()
    val bills: LiveData<ArrayList<BillsModel>> get() = _bills

    fun getBills() {
        FirebaseDatabase.getInstance().getReference(Constants.BILLS_REFERENCE)
            .orderByChild("user_paid")
            .equalTo(Constants.currentUser?.uid)
            .limitToLast(100)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val bills = ArrayList<BillsModel>()
                    snapshot.children.forEach { child ->
                        val bill = child.getValue(BillsModel::class.java)
                        bills.add(bill!!)
                    }
                    _bills.value = bills
                }

                override fun onCancelled(error: DatabaseError) {
                    _bills.value = arrayListOf()
                }
            })
    }
}
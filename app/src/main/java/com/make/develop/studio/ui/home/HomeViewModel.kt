package com.make.develop.studio.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.make.develop.studio.models.UserModel

class HomeViewModel: ViewModel(){


    private var _userModel = MutableLiveData<UserModel>()
    val userModel: LiveData<UserModel> get() = _userModel

    fun setUserData(user: UserModel){
        _userModel.value = user
    }
}
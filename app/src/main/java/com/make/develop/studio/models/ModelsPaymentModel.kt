package com.make.develop.studio.models

data class ModelsPaymentModel(
    var key:String?=null,
    var Models:List<ModelsInfoModel>?=null,
    var createDate: Long=0,
    var rangeDate: String?=null,
    var status: Int=0,
    var totalPayment: Int = 0,
)

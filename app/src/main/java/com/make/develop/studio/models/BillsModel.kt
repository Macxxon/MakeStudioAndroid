package com.make.develop.studio.models

data class BillsModel(
    var concept:String?=null,
    var total_payment:Int?= 0,
    var payment_user:String?=null,
    var paid:Boolean=false,
    var payment_date:Long?=null,
)
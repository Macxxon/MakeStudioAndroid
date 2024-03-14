package com.make.develop.studio.models

import com.squareup.moshi.Json

data class ModelsPaymentModel(
    @Json(name = "Models")
    var Models:List<ModelsInfoModel>?=null,
    @Json(name = "createDate")
    var createDate: Long=0,
    @Json(name = "rangeDate")
    var rangeDate: String?=null,
    @Json(name = "status")
    var status: Int=0,
    @Json(name = "totalPayment")
    var totalPayment: Int = 0,
)

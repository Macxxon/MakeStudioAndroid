package com.make.develop.studio.models

import com.squareup.moshi.Json

data class ModelsInfoModel (
    @Json(name = "name")
    var name:String?=null,
    @Json(name = "nickname")
    var nickname:String?=null,
    @Json(name = "payment")
    var payment: Int=0,
    @Json(name = "status")
    var status: Int=0
)
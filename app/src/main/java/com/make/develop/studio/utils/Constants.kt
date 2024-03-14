package com.make.develop.studio.utils

import com.make.develop.studio.models.UserModel
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat

object Constants {
    const val USER_REFERENCE = "Users"
    const val MODELS_PAYMENT_REFERENCE = "ModelsPayments"

    var authorizeToken: String?=null
    var currentUser: UserModel?=null

    fun formatPriceInPesos(price: Double): String {
        return if (price != 0.toDouble()) {
            val df = DecimalFormat("#,##0")
            df.roundingMode = RoundingMode.HALF_UP
            val finalPrice = StringBuilder(df.format(price)).toString()
            "$ ${finalPrice.replace(",", ".")}"
        } else {
            "$ 0"
        }
    }
}
package com.make.develop.studio.utils

import com.make.develop.studio.models.UserModel
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Random

object Constants {
    const val USER_REFERENCE = "Users"
    const val MODELS_PAYMENT_REFERENCE = "ModelsPayments"
    const val MODELS_REFERENCE = "Models"
    const val BILLS_REFERENCE = "Bills"

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

    fun createBillNumber(): String {
        return StringBuilder()
            .append(System.currentTimeMillis())
            .append(Math.abs(Random().nextInt()))
            .toString()
    }

}
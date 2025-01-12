package com.example.sharecircle

import java.text.NumberFormat
import java.util.Locale

fun Double.formatAsCurrency(locale: Locale = Locale("sl", "SI")): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(locale)
    return currencyFormat.format(this)
}

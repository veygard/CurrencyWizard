package com.veygard.currencywizard.util.extentions

import java.math.RoundingMode
import java.text.DecimalFormat

fun String.round(): String {
    val df  = DecimalFormat("#.##")
    df.roundingMode= RoundingMode.CEILING
    return df.format(this.toDouble()).replace(",", ".")
}
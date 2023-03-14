package com.haroof.common.extension

fun Float.roundDecimal(digit: Int) = "%.${digit}f".format(this)

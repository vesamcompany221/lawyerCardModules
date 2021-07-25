package com.vesam.barexamlibrary.utils.extention

fun toPersianNumber(text: String): String {
    val persianNumbers = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
    if (text.isEmpty()) return ""
    var out = "";
    val length = text.length
    for ( i in 0 until length) when (val c = text[i]) {
        in '0'..'9' -> {
            val number = Integer.parseInt(c.toString())
            out += persianNumbers[number]
        }
        '٫' -> out += '،'
        else -> out += c
    }
    return out
}
package com.ternakpro.app.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val indonesiaLocale = Locale("id", "ID")

fun formatRupiah(value: Double): String {
    val format = NumberFormat.getNumberInstance(indonesiaLocale)
    format.maximumFractionDigits = 0
    format.minimumFractionDigits = 0

    return "Rp ${format.format(value)}"
}

fun formatAngka(value: Double): String {
    val format = NumberFormat.getNumberInstance(indonesiaLocale)
    format.maximumFractionDigits = 2
    format.minimumFractionDigits = 0

    return format.format(value)
}

fun formatTanggal(tanggal: String): String {
    return try {
        val input = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        )

        val output = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        )

        val date = input.parse(tanggal)
        if (date != null) output.format(date) else tanggal
    } catch (_: Exception) {
        tanggal
    }
}

fun tanggalHariIni(): String {
    return SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    ).format(Date())
}

fun tanggalBulanIni(): String {
    return SimpleDateFormat(
        "yyyy-MM",
        Locale.getDefault()
    ).format(Date())
}

fun parseRupiah(value: String): Double {
    return try {
        value
            .replace("Rp", "", ignoreCase = true)
            .replace(".", "")
            .replace(",", ".")
            .trim()
            .toDoubleOrNull() ?: 0.0
    } catch (_: Exception) {
        0.0
    }
}

fun formatInteger(value: Int): String {
    return NumberFormat.getIntegerInstance(indonesiaLocale)
        .format(value)
}

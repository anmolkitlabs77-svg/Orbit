package com.example.jetpackcomposearchi.other

import android.util.Log
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object helper {

    fun formatDate(date: String): String{

        try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val outputFormatter = DateTimeFormatter.ofPattern("MMMM d", Locale.getDefault())

            val date = LocalDate.parse(date, inputFormatter)
            val formattedDate = date.format(outputFormatter)

            Log.d("asdadadada",formattedDate)
            return formattedDate
        }
        catch (e: Exception){
            e.printStackTrace()
            return  date ?: ""
        }

    }

    fun format2(date: String): String{
        try {
            return LocalDate.parse(date)
                .format(
                    DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault())
                )
        }
        catch (e: Exception){
            e.printStackTrace()
            return  ""
        }
    }

    fun currentDate(): String{
        try {

            val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val formattedDate = sdf.format(Date())
            return formattedDate
        }
        catch (e: Exception){
            return ""
        }
    }


    fun String.toFormattedVelocity(): String {
        val value = this.toDoubleOrNull() ?: return "--"

        val formatted = when {
            value >= 1_000_000_000_000 -> "${DecimalFormat("#.##").format(value / 1_000_000_000_000)}T"
            value >= 1_000_000_000 -> "${DecimalFormat("#.##").format(value / 1_000_000_000)}B"
            value >= 1_000_000 -> "${DecimalFormat("#.##").format(value / 1_000_000)}M"
            value >= 1_000 -> DecimalFormat("#,##0.00").format(value)
            else -> DecimalFormat("#,##0.00").format(value)
        }

        return "$formatted km/h"
    }


    fun String.formatKilometers(): String {
        val value = this.toDoubleOrNull() ?: return "--"

        return when {
            value >= 1_000_000 ->
                "${DecimalFormat("#.#").format(value / 1_000_000)}M"

            value >= 1_000 ->
                "${DecimalFormat("#.#").format(value / 1_000)}K"

            else ->
                NumberFormat.getNumberInstance(Locale.US).apply {
                    maximumFractionDigits = 2
                    minimumFractionDigits = 0
                }.format(value)
        }
    }

    fun Double.formatMeters(): String {
        return "${DecimalFormat("#,##0.##").format(this)} m"
    }

    fun String.formatMeters(): String {
        return this.toDoubleOrNull()?.formatMeters() ?: "--"
    }

}
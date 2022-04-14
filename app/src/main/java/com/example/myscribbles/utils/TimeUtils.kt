package com.example.myscribbles.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        fun formatDateMonth(date: Date): String {
            val simpleDateFormat = SimpleDateFormat("dd MMM")
            return simpleDateFormat.format(date)
        }

        fun formatYearDay(date: Date): String {
            val simpleDateFormat = SimpleDateFormat("yyyy EEE")
            return simpleDateFormat.format(date)
        }

        fun formatTime(date: Date): String {
            val simpleDateFormat = SimpleDateFormat("h:mm a")
            return simpleDateFormat.format(date)
        }
    }
}
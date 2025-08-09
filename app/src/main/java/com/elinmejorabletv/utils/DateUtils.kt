package com.elinmejorabletv.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateUtils {
    private val dateFormatFull = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    private val dateFormatDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val dateFormatTime = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateFormatDayMonth = SimpleDateFormat("dd MMM", Locale.getDefault())

    fun formatFullDate(date: Date): String {
        return dateFormatFull.format(date)
    }

    fun formatDate(date: Date): String {
        return dateFormatDate.format(date)
    }

    fun formatTime(date: Date): String {
        return dateFormatTime.format(date)
    }

    fun formatDayMonth(date: Date): String {
        return dateFormatDayMonth.format(date)
    }

    fun isToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        val calendar = Calendar.getInstance().apply { time = date }

        return today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
    }

    fun getTimeAgo(date: Date): String {
        val now = System.currentTimeMillis()
        val diff = now - date.time

        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "Hace un momento"
            diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)} minutos atrás"
            diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)} horas atrás"
            diff < TimeUnit.DAYS.toMillis(7) -> "${TimeUnit.MILLISECONDS.toDays(diff)} días atrás"
            else -> formatDate(date)
        }
    }
}
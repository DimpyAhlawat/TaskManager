package com.taskmanager.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    fun todayEpochMillis(): Long = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    fun format(epochMillis: Long): String = Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
    fun parseOrToday(value: String): Long = runCatching { LocalDate.parse(value, formatter) }.getOrDefault(LocalDate.now()).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

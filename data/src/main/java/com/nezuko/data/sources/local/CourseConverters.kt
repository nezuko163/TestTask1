package com.nezuko.data.sources.local

import androidx.room.TypeConverter
import com.nezuko.domain.model.CoursePlatform
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CourseConverters {

    // Конвертер для LocalDate
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun toLocalDate(date: String?): LocalDate? {
        return date?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }
    }

    // Конвертер для CoursePlatform (предположим, что это enum)
    @TypeConverter
    fun fromCoursePlatform(platform: CoursePlatform): Int {
        return platform.ordinal
    }

    @TypeConverter
    fun toCoursePlatform(platformId: Int): CoursePlatform {
        return CoursePlatform.entries[platformId]
    }
}

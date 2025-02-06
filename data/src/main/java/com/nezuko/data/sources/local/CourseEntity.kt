package com.nezuko.data.sources.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nezuko.domain.model.Course
import com.nezuko.domain.model.CoursePlatform
import java.time.LocalDate

@Entity(tableName = "courses")
@TypeConverters(CourseConverters::class) // Указываем, что будем использовать конвертеры
data class CourseEntity(
    @PrimaryKey
    @ColumnInfo(name = "course_id") // Имя столбца для id
    val id: Long,

    @ColumnInfo(name = "score") // Имя столбца для score
    val score: Double,

    @ColumnInfo(name = "title") // Имя столбца для title
    val title: String,

    @ColumnInfo(name = "platform") // Имя столбца для platform
    val platform: CoursePlatform,

    @ColumnInfo(name = "cover") // Имя столбца для cover
    val cover: String,

    @ColumnInfo(name = "price_display") // Имя столбца для priceDisplay
    val priceDisplay: String,

    @ColumnInfo(name = "description") // Имя столбца для description
    val description: String,

    @ColumnInfo(name = "is_favourite") // Имя столбца для isFavourite
    val isFavourite: Boolean,

    @ColumnInfo(name = "author_cover") // Имя столбца для authorCover
    val authorCover: String,

    @ColumnInfo(name = "author") // Имя столбца для author
    val author: String,

    @ColumnInfo(name = "url_course") // Имя столбца для urlCourse
    val urlCourse: String,

    @ColumnInfo(name = "date_created") // Имя столбца для dateCreated
    val dateCreated: LocalDate
) {
    fun toDTO(): Course {
        return Course(
            id = this.id,
            score = this.score,
            title = this.title,
            platform = this.platform,
            cover = this.cover,
            priceDisplay = this.priceDisplay,
            description = this.description,
            isFavourite = this.isFavourite,
            authorCover = this.authorCover,
            author = this.author,
            urlCourse = this.urlCourse,
            dateCreated = this.dateCreated
        )
    }
}

fun Course.toEntity(): CourseEntity {
    return CourseEntity(
        id = this.id,
        score = this.score,
        title = this.title,
        platform = this.platform,
        cover = this.cover,
        priceDisplay = this.priceDisplay,
        description = this.description,
        isFavourite = this.isFavourite,
        authorCover = this.authorCover,
        author = this.author,
        urlCourse = this.urlCourse,
        dateCreated = this.dateCreated
    )
}

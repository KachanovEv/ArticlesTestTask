package com.example.articleslistapp.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_table")
data class ArticlesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "date_created")
    var dateCreated: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "picture_path")
    var picturePath: String,
    @ColumnInfo(name = "picture_width")
    var pictureWidth: Int,
    @ColumnInfo(name = "picture_height")
    var pictureHeight: Int,
    @ColumnInfo(name = "category_id")
    var categoryId: Int,
    @ColumnInfo(name = "pages_count")
    var pagesCount: Int
)





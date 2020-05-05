package com.example.articleslistapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.articleslistapp.data.db.dao.ArticlesDao
import com.example.articleslistapp.data.db.model.ArticlesEntity

@Database(
    entities = [ArticlesEntity::class], version = 2
)

abstract class ArticlesListAppDataBase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}
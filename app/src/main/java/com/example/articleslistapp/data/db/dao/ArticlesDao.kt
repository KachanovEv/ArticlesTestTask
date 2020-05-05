package com.example.articleslistapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.articleslistapp.data.db.model.ArticlesEntity
import io.reactivex.Observable

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticlesData(articlesEntityList: ArrayList<ArticlesEntity>)

    @Query("SELECT * FROM articles_table WHERE category_id=:id")
    fun getAllArticlesData(id:Int): Observable<List<ArticlesEntity>>

}
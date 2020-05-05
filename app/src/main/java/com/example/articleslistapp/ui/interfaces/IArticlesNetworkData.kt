package com.example.articleslistapp.ui.interfaces

import com.example.articleslistapp.data.db.model.ArticlesEntity
import com.example.articleslistapp.data.network.model.ArticlesListNetModel

interface IArticlesNetworkData {

    fun onDataChange(
        articlesList: ArticlesListNetModel,
        category: Int
    )

    fun onPageCountChange(count: Int?)

    fun onFileUriChange(
        articlesList: ArrayList<ArticlesEntity>
    )

    fun onDataFromDbChange(
        articlesEntityList: List<ArticlesEntity>,
        categoryId: Int
    )
}
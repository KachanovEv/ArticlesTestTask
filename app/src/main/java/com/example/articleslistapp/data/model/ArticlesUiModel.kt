package com.example.articleslistapp.data.model


data class ArticlesUiModel(
    var dateCreated: Int,
    var title: String,
    var content: String,
    var pictureUrl: String,
    var pictureWidth: Int,
    var pictureHeight: Int,
    var category: Int
)
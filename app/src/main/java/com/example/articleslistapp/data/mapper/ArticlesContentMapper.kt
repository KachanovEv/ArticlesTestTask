package com.example.articleslistapp.data.mapper

import com.example.articleslistapp.data.db.model.ArticlesEntity
import com.example.articleslistapp.data.model.ArticlesUiModel
import com.example.articleslistapp.data.network.model.Article
import com.example.articleslistapp.data.network.model.ArticlesListNetModel

object ArticlesContentMapper {

    private var contentUiListData = ArrayList<ArticlesUiModel>()
    private var articlesList = ArrayList<ArticlesUiModel>()
    private var pagesCount = 0
    private var articleListEntity = ArrayList<ArticlesEntity>()

    fun setDataFromNetwork(
        result: ArticlesListNetModel,
        category: Int
    ): ArrayList<ArticlesUiModel> {
        for (item in result.articles!!) {
            contentUiListData.add(
                ArticlesUiModel(
                    item.dateCreated!!.toInt(),
                    item.title!!,
                    item.content!!,
                    item.picture!!,
                    item.pictureWidth!!.toInt(),
                    item.pictureHeight!!.toInt(),
                    category
                )
            )
        }
        return contentUiListData
    }

    fun setDataFromDB(articlesEntityList: List<ArticlesEntity>): ArrayList<ArticlesUiModel> {
        for (item in articlesEntityList) {
            articlesList.add(
                ArticlesUiModel(
                    item.dateCreated,
                    item.title,
                    item.content,
                    item.picturePath,
                    item.pictureWidth,
                    item.pictureHeight,
                    item.categoryId
                )
            )
        }

        return articlesList
    }

    fun getPagesCount(
        articlesEntityList: List<ArticlesEntity>,
        categoryId: Int
    ): Int {
        for (item in articlesEntityList) {
            if (item.categoryId == categoryId) {
                pagesCount = item.pagesCount
            }
        }
        return pagesCount
    }

    fun setLocalDataForDB(
        articles: Article,
        pathFile: String,
        pagesCount: Int?
    ): ArrayList<ArticlesEntity> {
        articleListEntity.add(
            ArticlesEntity(
                articles.id!!.toInt(),
                articles.dateCreated!!.toInt(),
                articles.title!!,
                articles.content!!,
                pathFile,
                articles.pictureWidth!!.toInt(),
                articles.pictureHeight!!.toInt(),
                articles.category!!.id!!.toInt(),
                pagesCount!!
            )
        )
        return articleListEntity
    }
}
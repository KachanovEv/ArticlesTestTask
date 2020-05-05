package com.example.articleslistapp.data.network

import com.example.articleslistapp.data.network.model.ArticlesListNetModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RestApi {

    @GET("applications/app/content/list/")
    fun getAllArticlesList(
        @Query("category") category: Int?,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?,
        @Query("order") order: String?
    ): Observable<ArticlesListNetModel>

    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String?): Observable<Response<ResponseBody>>

}
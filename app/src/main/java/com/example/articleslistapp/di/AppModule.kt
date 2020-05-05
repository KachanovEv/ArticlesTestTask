package com.example.articleslistapp.di

import androidx.room.Room
import com.example.articleslistapp.data.db.ArticlesListAppDataBase
import com.example.articleslistapp.data.network.ApiManager
import com.example.articleslistapp.ui.fragment.article.ArticlesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Instance  DB Room
    single {
        Room.databaseBuilder(get(), ArticlesListAppDataBase::class.java, "articles-database")
            .build()
    }

    // Retrofit
    single { ApiManager() }

    // view model
    viewModel {
        ArticlesViewModel(
            get()
        )
    }
}
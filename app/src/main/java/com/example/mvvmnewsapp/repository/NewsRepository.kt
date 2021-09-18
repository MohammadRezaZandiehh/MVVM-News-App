package com.example.mvvmnewsapp.ui.repository

import androidx.room.Query
import com.example.mvvmnewsapp.ui.api.RetrofitInstance
import com.example.mvvmnewsapp.ui.db.ArticleDatabase
import com.example.mvvmnewsapp.ui.model.Article

//dg tooye constructor az class e api chizi nagereftim chon onja joorie k age hamin api ro bZnim mitonim fara khanish konim .
class NewsRepository(
    val db: ArticleDatabase
) {
//API :
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)


    suspend fun searchNews (searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

//Database :
    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)
    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
    fun getSavedNews () = db.getArticleDao().getAllArticles()
}
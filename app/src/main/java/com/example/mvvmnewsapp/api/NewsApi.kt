package com.example.mvvmnewsapp.api

import com.example.mvvmnewsapp.model.NewsResponse
import com.example.mvvmnewsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines/{postGroupSlug}")
    suspend fun getBreakingNews(
        @Path("postGroupSlug")
        url: String?,
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
        ): Response<NewsResponse>


    @GET("v2/top-headlines")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}
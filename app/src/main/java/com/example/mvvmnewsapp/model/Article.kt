package com.example.mvvmnewsapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: String? = null,

    @SerializedName("author")
    var author: String,

    @SerializedName("content")
    var content: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("publishedAt")
    var publishedAt: String,

    @SerializedName("source")
    var source: Source,

    @SerializedName("title")
    var title: String,

    @SerializedName("url")
    var url: String,

    @SerializedName("urlToImage")
    var urlToImage: String
)
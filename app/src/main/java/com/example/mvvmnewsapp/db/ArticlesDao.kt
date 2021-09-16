package com.example.mvvmnewsapp.ui.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvmnewsapp.ui.model.Article


/*
If you set the onConflictStrategy of an insertion function for Room to OnConflictStrategy.REPLACE,
 when will an entry be replaced?
 answer:  If the new entry has the "SAME" primary key value as the old entry
*/

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long
    //upsert : mokhafafe update va insert !

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    /*
    har moghe masalan gooshi rotation dashte bashe k activity kharab mishe va az aval sakhte mishe ,
    ba LiveData data haye jadid ro migire va jaygozin mikone .
          */

    @Delete
    suspend fun deleteArticle(article: Article)
}
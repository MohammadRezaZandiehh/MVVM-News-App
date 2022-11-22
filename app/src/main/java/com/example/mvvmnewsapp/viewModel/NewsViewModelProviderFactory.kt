package com.example.mvvmnewsapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmnewsapp.db.ArticleDatabase
import com.example.mvvmnewsapp.repo.NewsRepository

class NewsViewModelProviderFactory(
    val repository: NewsRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}
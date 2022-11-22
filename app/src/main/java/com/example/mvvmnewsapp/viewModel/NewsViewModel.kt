package com.example.mvvmnewsapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.mvvmnewsapp.repo.NewsRepository

class NewsViewModel(
    val repository: NewsRepository
): ViewModel() {

}
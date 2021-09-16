package com.example.mvvmnewsapp.ui.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnewsapp.ui.model.NewsResponse
import com.example.mvvmnewsapp.ui.repository.NewsRepository
import com.example.mvvmnewsapp.ui.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }
    //block e bala daghighan jaei hast k vaghti ye object az in NewsViewModel emoon sakhte mishe , request ro b API mifreste.


    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
//        if (response.isSuccessful){
//            val success = Resource.Success(response.body())
//            breakingNews.postValue(success)
//        }else{
//            val error = Resource.Error(response.message().toString())
//            breakingNews.postValue(error)
//        }




    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
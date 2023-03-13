package com.example.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.adapter.NewsAdapter
import com.example.mvvmnewsapp.ui.NewsActivity
import com.example.mvvmnewsapp.utils.Constants
import com.example.mvvmnewsapp.utils.Resource
import com.example.mvvmnewsapp.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment, bundle
            )
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())

//finding if we are in the last pages or not:
                        val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
                        /** 2 reasons for +2 : firstly, we have integer division(taghsime adade sahih) and we always have round off()gerd shodan
                         * secondly the last page of aur response will always be empty and we don't really want that  */

                        isLastPage = viewModel.breakingNewsPage == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    // are we currently loading or not:
    var isLoading = false

    // are we at the end of the page already or not? :
    var isLastPage = false

    // are we currently scrolling or not? :
    var isScrolling = false

    //now we define scrollListener for our recyclerView:
    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            //if we are currently scrolling in the bottom line:
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) isScrolling =
                true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            //total ""visible"" item count:
            val visibleItemCount = layoutManager.childCount
            //total item count"
            val totalItemCount = layoutManager.itemCount


            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            //we know that the last item is visible now with bottom line:
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            /*we are not at the begin fo our recyclerView:
             if it was 0 -> we are at the beginning of recyclerView */
            val isNotAtTheBeginning = firstVisibleItemPosition >= 0
            //it will check if we have at least as many items in our recyclerView than query page size (20)
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE

            /**now we check if we should paginated or not*/
            val shouldPaginated =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtTheBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginated) {
                /** because of we wrote breakingNewsPage++ in viewModel it will go to next page automatically*/
                viewModel.getBreakingNews("us")
                isScrolling = false
            } else {
                //we reset the end padding of out rv if we don't have another pagination:
                rvBreakingNews.setPadding(0, 0, 0, 0)
            }
        }
    }
}


/**
 * with layoutManager && firstVisibleItemPosition && visibleItemCount && totalItemCount we check if we scroll until the bottom of our recyclerView or not*/


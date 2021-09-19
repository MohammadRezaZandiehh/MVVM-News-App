package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.ui.NewsActivity
import com.example.mvvmnewsapp.ui.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article?.url)
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)          // in article hamon object e article i hast k dar scope e bala az args gereftimesh , k dar vaghe hamon object i hast k dar breakingNewsFragment b vasile ye NavigationComponent pass esh dadim inja yani ArticleFragment (vaghti rooye itm haye recyclerView dar breakingFragment click mikonimmm , vared e ArticleFragment mishim .)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}
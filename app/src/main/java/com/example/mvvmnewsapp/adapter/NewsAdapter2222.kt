package com.example.mvvmnewsapp.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.model.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*


class NewsAdapter2222(val callbackForGetNewsWithId22: CallbackForGetNewsWithId22? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_PLACE_HOLDER = 0
        private const val VIEW_TYPE_MOVIE = 1
    }

    inner class ArticleViewHolder2222(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(article: Article) {
            Glide.with(itemView).load(article.urlToImage).into(itemView.ivArticleImage)
            itemView.tvSource.text = article.source?.name
            itemView.tvTitle.text = article.title
            itemView.tvDescription.text = article.description
            itemView.tvPublishedAt.text = article.publishedAt
            itemView.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    inner class PlaceHolderViewHolder(parent: View) : RecyclerView.ViewHolder(parent)

    private val articleLists = mutableListOf<Article>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_PLACE_HOLDER -> {
                return PlaceHolderViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_place_holder_rv,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_MOVIE -> {
                return ArticleViewHolder2222(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_article_preview,
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalStateException("viewType is not valid")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articles = articleLists[position]

        when (holder) {
            is PlaceHolderViewHolder -> {
                articles.url.let {
                    callbackForGetNewsWithId22?.getNewsWithCallback22(it)
                }
            }
            is ArticleViewHolder2222 -> holder.onBind(articles)
        }
    }

    override fun getItemCount(): Int {
        return articleLists.size
    }

    private fun remove(position: Int) {
        Handler(Looper.getMainLooper()).post {
            articleLists.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addPostGroupData(
        url: String,
        articleContent: List<Article>
    ) {
        articleLists.find {
            it.url == url
        }?.let { article ->
            val index = articleLists.indexOf(article)

            if (articleContent.isEmpty()) {
                remove(index)
            } else {
                articleLists.remove(article)
                articleLists.add(index, article)

                notifyItemChanged(index)
            }
        }
    }


    fun updateRV(lists: List<Article>) {
        articleLists.addAll(lists)
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}


interface CallbackForGetNewsWithId22 {
    fun getNewsWithCallback22(url: String?)
}